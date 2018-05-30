from django.contrib.auth.mixins import PermissionRequiredMixin
from django.contrib.auth.views import PasswordChangeView
from django.shortcuts import render
from django.http import HttpResponse
from django.template import Context, loader
import os
# Create your views here.
from django.views.generic import UpdateView, ListView
from django.views.generic.list import MultipleObjectTemplateResponseMixin

from apps.main.MultiForm import MultiFormsView
from apps.main.views import IsLoggedInView, DetailsView
from apps.manager import models
from apps.manager.models import Company, Customer


def dashboard(request):
    template = loader.get_template("manager/dashboard.html")
    return HttpResponse(template.render())


class ManagerPasswordChangeView(PasswordChangeView):
    def get_template_names(self):
        return 'manager/change_password.html'


def transaction_details(request, id):
    template = loader.get_template("manager/transaction_details.html")
    return HttpResponse(template.render({"id": id, "type": "mammad"}))


class CompanySettingsView(IsLoggedInView, PermissionRequiredMixin, UpdateView):
    model = Company
    template_name = "manager/settings.html"
    fields = ['english_name', 'persian_name', 'account', 'photo']
    # todo incomplete


def users(request, id):
    user_type = "customer"
    if user_type == "customer":
        user_type = "مشتری"
    else:
        user_type = "کارمند"
    template = loader.get_template("manager/users.html")
    return HttpResponse(template.render({"type": user_type}))


class CustomerDetailsView(DetailsView, ListView):
    model = Customer
    fields = ['persian_first_name', 'persian_last_name', 'english_first_name', 'english_last_name', 'username', 'email',
              'phone', 'account-number']
    context_object_name = 'transactions'
    template_name = 'manager/customer_details'

    #todo az koja username ro biarim? :(

    def get_context_data(self, **kwargs):
        return [] #todo query bezan transaction haye user e marboot ro biar


def employee_details(request, employee_id):
    template = loader.get_template("manager/employee_details.html")
    return HttpResponse(template.render())


def wallet(request, currency):
    if currency == "dollar":
        currency = "دلار"
    elif currency == "euro":
        currency = "یورو"
    elif currency == "rial":
        currency = "ریال"
    template = loader.get_template("manager/wallet.html")
    return HttpResponse(template.render({"currency": currency}))


def notifications(request):
    template = loader.get_template("manager/notifications.html")
    return HttpResponse(template.render())
