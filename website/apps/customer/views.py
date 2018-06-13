from django.contrib.auth.mixins import PermissionRequiredMixin
from django.contrib.auth.views import PasswordChangeView
from django.shortcuts import render
from django.http import HttpResponse
from django.template import Context, loader
import os
# Create your views here.
from django.urls import reverse_lazy
from django.views.generic import CreateView, UpdateView, ListView

from apps.main.views import IsLoggedInView, IsCustomer
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


class CustomerPasswordChangeView(IsLoggedInView, IsCustomer, PasswordChangeView):
    success_url = reverse_lazy('customer:change_password')
    template_name = 'customer/change_password.html'


class CustomerSettingsView(IsLoggedInView, IsCustomer, UpdateView):
    model = Customer
    template_name = 'customer/settings.html'

    def get_context_data(self, **kwargs):
        return ""  # todo query bezan oon customer ro biab

    fields = ['persian_first_name', 'persian_last_name', 'english_first_name', 'english_last_name', 'email', 'phone',
              'account-number', 'photo']


class TransactionsListView(IsLoggedInView, IsCustomer, ListView):
    template_name = 'customer/mytransactions.html'
