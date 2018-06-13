from django.contrib.auth.mixins import PermissionRequiredMixin
from django.contrib.auth.views import PasswordChangeView
from django.shortcuts import render
from django.http import HttpResponse
from django.template import Context, loader
import os
# Create your views here.
from django.urls import reverse_lazy
from django.views.generic import CreateView, UpdateView, ListView

from apps.customer.Forms import CustomerSettingsForm
from apps.main.views import IsLoggedInView, IsCustomer, CustomerDetailsView
from apps.customer.models import Customer, TOFEL, GRE, UniversityTrans, ForeignTrans, InternalTrans, UnknownTrans


class CustomerDashboardView(IsLoggedInView, IsCustomer, ListView):
    template_name = "customer/dashboard.html"


class TransactionCreationView(IsLoggedInView, IsCustomer, CreateView):
    ""


class ReverseChargeCreationView(TransactionCreationView):
    template_name = "customer/reverse_charge.html"

    class Meta:
        model = InternalTrans
        # todo incomplete


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

class CustomerPasswordChangeView(IsLoggedInView, IsCustomer, PasswordChangeView):
    success_url = reverse_lazy('customer:change_password')
    template_name = 'customer/change_password.html'


class CustomerSettingsView(IsLoggedInView, IsCustomer, UpdateView):
    form_class = CustomerSettingsForm
    template_name = 'customer/settings.html'
    success_url = reverse_lazy('customer:settings')

    def get_object(self, queryset=None):
        username = self.request.user.username
        return Customer.objects.get(username=username)

    def form_valid(self, form):
        clean = form.cleaned_data
        context = {}
        self.object = context.update(clean)
        return super(CustomerSettingsView, self).form_valid(form)


class TransactionsListView(IsLoggedInView, IsCustomer, ListView):
    template_name = 'customer/mytransactions.html'
