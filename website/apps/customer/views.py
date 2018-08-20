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
from apps.customer.models import Customer, Request
from views import Compilation
from django.urls import reverse_lazy
from django.views.generic import CreateView, UpdateView, ListView
from django.views.generic.detail import DetailView
from apps.customer.Forms import CustomerSettingsForm
from apps.main.Forms import UserPasswordChangeForm
from apps.main.views import IsLoggedInView, IsCustomer, CustomerDetailsView
from apps.customer.models import Customer, TOFEL, GRE, UniversityTrans, ForeignTrans, InternalTrans, UnknownTrans


class CustomerTemplateView(TemplateView):
    def get_context_data(self, **kwargs):
        context = super().get_context_data(**kwargs)
        context, customer = Compilation.get_customer_context_data(context, self.request.user.username)
        return context


class CustomerFormView(FormView, IsLoggedInView, IsCustomer):
    def get_context_data(self, **kwargs):
        context = super().get_context_data(**kwargs)
        context, customer = Compilation.get_customer_context_data(context, self.request.user.username)
        context.update({'notifications' : Notification.objects.filter(user__username = self.request.user.username, seen = False).order_by('-sent_date')})
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
        return context


class TransactionCreationView(IsLoggedInView, IsCustomer, CreateView):
    ""


class ReverseChargeCreationView(TransactionCreationView):
    template_name = "customer/reverse_charge.html"

    class Meta:
        model = InternalTrans
        # todo incomplete


class TransactionDetailsView(DetailView):
    template_name = "customer/transaction_details.html"
    mdoel = Request
    # queryset = Request.objects.all()

    def get_queryset(self):
        # """Return the last five published questions."""
        return Request.objects.filter(id=self.kwargs['pk'])

    def get_context_data(self, **kwargs):
        temp = super().get_context_data(**kwargs)
        print(temp)
        print(temp['foreigntrans'])
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


# todo add other forms as well

class CustomerProfile(IsCustomer, CustomerDetailsView):
    ""


class CustomerPasswordChangeView(IsCustomer, FormView):
    form_class = UserPasswordChangeForm
    success_url = reverse_lazy('main:login')
    template_name = 'customer/change_password.html'

    def get_form_kwargs(self):
        kwargs = super(CustomerPasswordChangeView, self).get_form_kwargs()
        kwargs['user'] = Customer.objects.get(username=self.request.user)
        return kwargs

    def form_valid(self,form):
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
        print("*" * 20 + str(clean))
        context = {}
        self.object = context.update(clean)
        return super(CustomerSettingsView, self).form_valid(form)


class TransactionsListView(IsLoggedInView, IsCustomer, ListView):
    template_name = 'customer/mytransactions.html'
