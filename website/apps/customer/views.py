from django.contrib.auth.mixins import PermissionRequiredMixin
from django.contrib.auth.views import PasswordChangeView
from django.shortcuts import render
from django.http import HttpResponse
from django.template import Context, loader
import os
# Create your views here.
from django.views.generic.edit import FormView
from django.views.generic import CreateView, UpdateView, ListView, TemplateView
from apps.main.views import IsLoggedInView, IsCustomer
from apps.main.views import TransactionDetailsView as MainTransactionDetails
from apps.customer.models import Customer, Request, CustomTransactionType
from views import Compilation
from django.urls import reverse_lazy
from django.views.generic import CreateView, UpdateView, ListView
from django.views.generic.detail import DetailView
from apps.customer.Forms import CustomerSettingsForm
from apps.main.Forms import UserPasswordChangeForm
from apps.customer.models import Customer, TOFEL, GRE, UniversityTrans, ForeignTrans, InternalTrans, UnknownTrans
from apps.customer import Forms
from apps.main.models import GenUser, Notification
from django.contrib import messages


class CustomerTemplateView(TemplateView):
    def get_context_data(self, **kwargs):
        context = super().get_context_data(**kwargs)
        context, customer = Compilation.get_customer_context_data(context, self.request.user.username)
        self.context = context
        return context


class CustomerFormView(FormView, IsLoggedInView, IsCustomer):
    def get_context_data(self, **kwargs):
        context = super().get_context_data(**kwargs)
        context, customer = Compilation.get_customer_context_data(context, self.request.user.username)
        context.update({'notifications': Notification.objects.filter(user__username=self.request.user.username, seen=False).order_by('-sent_date')})
        self.context = context
        return context


class CustomerCreateView(CreateView, IsLoggedInView, IsCustomer):
    def get_context_data(self, **kwargs):
        context = super().get_context_data(**kwargs)
        context, customer = Compilation.get_customer_context_data(context, self.request.user.username)
        context.update({'notifications': Notification.objects.filter(user__username=self.request.user.username, seen=False).order_by('-sent_date')})
        self.context = context
        return context


class CustomerWallet (CustomerFormView):
    template_name = "customer/wallet.html"

    def get_context_data(self, **kwargs):
        context = super().get_context_data(**kwargs)
        context['currency'] = self.kwargs['currency']
        return context


class CustomerDashboardView(IsLoggedInView, IsCustomer, CustomerTemplateView):
    template_name = "customer/dashboard.html"

    def get_context_data(self, **kwargs):
        context = super().get_context_data(**kwargs)
        context = Compilation.get_wallet_requests(context, self.request.user.username, -1)
        context['requests'] = context['requests'][:5]
        context['pending_requests'] = len(Request.objects.filter(source_user__username=self.request.user.username, status=2))
        context["transaction_types"] = CustomTransactionType.objects.all()
        return context


class TransactionCreationView(CustomerCreateView):
    template_name = "customer/render_form.html"

    def get_success_url(self):
        return reverse_lazy('customer:create', kwargs={'type': self.type})

    def get_form_kwargs(self):
        kwargs = super(CustomerCreateView, self).get_form_kwargs()
        kwargs['user'] = Customer.objects.get(username=self.request.user)
        # kwargs['dest'] = Transactions.currency_to_num(self.kwargs['currency'])
        return kwargs

    def dispatch(self, request, *args, **kwargs):
        self.type = self.kwargs['type']
        temp = super().dispatch(request, *args, **kwargs)
        return temp

    def get_form_class(self):
        return Forms.get_form_class(self.type)

    def form_valid(self, form):
        messages.add_message(
            self.request, messages.SUCCESS, 'تراکنش با موفقیت ثبت شد.')
        return super(TransactionCreationView, self).form_valid(form)


class ReverseChargeCreationView(TransactionCreationView):

    template_name = "customer/reverse_charge.html"

    class Meta:
        model = InternalTrans
        # todo incomplete


class TransactionDetailsView(MainTransactionDetails):
    template_name = "customer/transaction_details.html"
    mdoel = Request

    def get_context_data(self, **kwargs):
        temp = super().get_context_data(**kwargs)
        temp['type'] = temp['foreigntrans']._meta.model_name
        return(temp)


class ForeignPaymentCreationView(TransactionCreationView):
    template_name = "customer/foreign_payment.html"

    class Meta:
        model = ForeignTrans
        # todo incomplete


class AnonymousPaymentCreationView(TransactionCreationView):
    template_name = "customer/anonymous_payment.html"

    class Meta:
        model = UnknownTrans
        # todo incomplete


class ApplicationFeeCreationView(TransactionCreationView):
    template_name = "customer/application_fee.html"

    class Meta:
        model = UniversityTrans
        # todo incomplete


class TOFELCreationView(TransactionCreationView):
    class Meta:
        model = TOFEL


class GRECreationView(TransactionCreationView):
    class Meta:
        model = GRE


class CustomerPasswordChangeView(IsCustomer, FormView):
    form_class = UserPasswordChangeForm
    success_url = reverse_lazy('main:login')
    template_name = 'customer/change_password.html'

    def get_form_kwargs(self):
        kwargs = super(CustomerPasswordChangeView, self).get_form_kwargs()
        kwargs['user'] = Customer.objects.get(username=self.request.user)
        return kwargs

    def form_valid(self, form):
        form.save()
        return super().form_valid(form)


class CustomerSettingsView(IsLoggedInView, IsCustomer, UpdateView):
    form_class = CustomerSettingsForm
    template_name = 'customer/settings.html'
    success_url = reverse_lazy('customer:settings')

    def get_object(self, queryset=None):
        username = self.request.user.username
        print("username is " + username)
        return Customer.objects.get(username=username)

    def form_invalid(self, form):
        return self.render_to_response(self.get_context_data(form=form))

    def form_valid(self, form):
        clean = form.cleaned_data
        context = {}
        self.object = context.update(clean)
        return super(CustomerSettingsView, self).form_valid(form)


class TransactionsListView(IsLoggedInView, IsCustomer, TemplateView):
    template_name = 'customer/mytransactions.html'

    def get_context_data(self, **kwargs):
        context = super(TransactionsListView, self).get_context_data(**kwargs)
        Compilation.get_wallet_requests(context, self.request.user.username, -1)
        return context


class MakeTransactionView(IsLoggedInView, IsCustomer, FormView):
    template_name = "cuatomer/make_transaction"
