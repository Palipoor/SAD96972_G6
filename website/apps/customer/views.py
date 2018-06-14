from django.contrib.auth.mixins import PermissionRequiredMixin
from django.contrib.auth.views import PasswordChangeView
from django.shortcuts import render
from django.http import HttpResponse
from django.template import Context, loader
import os
# Create your views here.
from django.views.generic.edit import FormView
from django.views.generic import CreateView, UpdateView, ListView,TemplateView
from apps.main.views import IsLoggedInView, IsCustomer
from apps.customer.models import Customer
from views import Compilation


class CustomerTemplateView(TemplateView):
    def get_context_data(self, **kwargs):
        context = super().get_context_data(**kwargs)        
        context,customer = Compilation.get_customer_context_data(context, self.request.user.username)
        return context

class CustomerFormView(FormView, IsLoggedInView, IsCustomer):
    def get_context_data(self, **kwargs):
        context = super().get_context_data(**kwargs)        
        context,customer =  Compilation.get_customer_context_data(context, self.request.user.username)
        return context

class CustomerWallet (CustomerFormView):
    template_name = "customer/wallet.html"
    def get_context_data(self, **kwargs):
        context = super().get_context_data(**kwargs)        
        context['currency'] = self.kwargs['currency']
        return context
    

class CustomerDashboardView(CustomerTemplateView, IsLoggedInView, IsCustomer):
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
