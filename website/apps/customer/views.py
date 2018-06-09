from django.contrib.auth.mixins import PermissionRequiredMixin
from django.contrib.auth.views import PasswordChangeView
from django.shortcuts import render
from django.http import HttpResponse
from django.template import Context, loader
import os
# Create your views here.
from django.views.generic import CreateView, UpdateView, ListView

from apps.main.views import IsLoggedInView, IsCustomer
from apps.customer.models import Customer


class CustomerDashboardView(IsLoggedInView, IsCustomer, ListView):
    template_name = "customer/dashboard.html"


class TransactionCreationView(IsLoggedInView, CreateView):
    ""


class ReverseChargeCreationView(TransactionCreationView):
    template_name = "customer/reverse_charge.html"
    # todo incomplete


class ForeignPaymentCreationView(TransactionCreationView):
    template_name = "customer/foreign_payment.html"
    # todo incomplete


class AnonymousPaymentCreationView(TransactionCreationView):
    template_name = "customer/anonymous_payment.html"
    # todo incomplete


class ApplicationFeeCreationView(TransactionCreationView):
    template_name = "customer/application_fee.html"
    # todo incomplete


# todo add other forms as well


class CustomerPasswordChangeView(IsLoggedInView, IsCustomer, PasswordChangeView):
    def get_template_names(self):
        return 'customer/change_password.html'


class CustomerSettingsView(IsLoggedInView, IsCustomer, UpdateView):
    model = Customer
    template_name = 'customer/settings.html'

    def get_context_data(self, **kwargs):
        return ""  # todo query bezan oon customer ro biab

    fields = ['persian_first_name', 'persian_last_name', 'english_first_name', 'english_last_name', 'email', 'phone',
              'account-number', 'photo']


class TransactionsListView(IsLoggedInView, IsCustomer, ListView):
    template_name = 'customer/mytransactions.html'
