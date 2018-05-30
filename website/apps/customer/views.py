from django.contrib.auth.mixins import PermissionRequiredMixin
from django.contrib.auth.views import PasswordChangeView
from django.shortcuts import render
from django.http import HttpResponse
from django.template import Context, loader
import os
# Create your views here.
from django.views.generic import CreateView, UpdateView

from apps.main.views import IsLoggedInView
from apps.manager.models import Customer


class TransactionCreationView(IsLoggedInView, PermissionRequiredMixin, CreateView):
    ""


class ReverseChargeCreationView(TransactionCreationView):
    ""


class ForeignPaymentCreationView(TransactionCreationView):
    ""


class AnonymousPaymentCreationView(TransactionCreationView):
    ""


class ApplicationFeeCreationView(TransactionCreationView):
    ""


# todo add other forms as well


def dashboard(request):
    template = loader.get_template("customer/dashboard.html")
    return HttpResponse(template.render())


def notifications(request):
    template = loader.get_template("customer/notifications.html")
    return HttpResponse(template.render())


class CustomerPasswordChangeView(PasswordChangeView):
    def get_template_names(self):
        return 'customer/change_password.html'


class CustomerSettingsView(IsLoggedInView, PermissionRequiredMixin, UpdateView):
    model = Customer

    def get_context_data(self, **kwargs):
        return ""  # todo query bezan oon customer ro biab

    fields = ['persian_first_name', 'persian_last_name', 'english_first_name', 'english_last_name', 'email', 'phone',
              'account-number', 'photo']


def mytransactions(request):
    template = loader.get_template("customer/mytransactions.html")
    return HttpResponse(template.render())


def transaction_details(request, id):
    template = loader.get_template("customer/transaction_details.html")
    return HttpResponse(template.render())


def reverse_charge(request):
    template = loader.get_template("customer/reverse_charge.html")
    return HttpResponse(template.render())


def foreign_payment(request):
    template = loader.get_template("customer/foreign_payment.html")
    return HttpResponse(template.render())


def application_fee(request):
    template = loader.get_template("customer/application_fee.html")
    return HttpResponse(template.render())


def anonymous_payment(request):
    template = loader.get_template("customer/anonymous_payment.html")
    return HttpResponse(template.render())
