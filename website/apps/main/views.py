from django.contrib.auth.mixins import LoginRequiredMixin, PermissionRequiredMixin
from django.shortcuts import render
from django.http import HttpResponse
from django.template import Context, loader
import os
# Create your views here.
from django.views.generic import UpdateView, FormView
from django.views.generic.detail import SingleObjectMixin
from django.views.generic.list import MultipleObjectTemplateResponseMixin


class IsLoggedInView(LoginRequiredMixin):
    '''is the user logged in'''
    ''' baraye redirect kardan be login url'''


class IsManagerView(IsLoggedInView):
    def dispatch(self, request, *args, **kwargs):
        ''' authenticate the user as the manager '''


class IsEmployeeView(IsLoggedInView):
    def dispatch(self, request, *args, **kwargs):
        ''' authenticate the user as an employee (could be an employee or the manager!) '''


class IsCustomerView(IsLoggedInView):
    def dispatch(self, request, *args, **kwargs):
        ''' authenticate the user as a customer'''


class UserSettingsView(IsLoggedInView, PermissionRequiredMixin, UpdateView):
    ""


class WalletView(IsLoggedInView, PermissionRequiredMixin, FormView):
    ""


class DetailsView(IsLoggedInView, PermissionRequiredMixin, SingleObjectMixin):
    ""


class CustomerDetailsView(DetailsView):
    ""


class EmployeeDetailsView(DetailsView):
    ""


class TransactionDetailsView(DetailsView):
    ""


class NotificationsView(IsLoggedInView, PermissionRequiredMixin, MultipleObjectTemplateResponseMixin):
    ""


def index(request):
    template = loader.get_template("main/index.html")
    return HttpResponse(template.render())


def login(request):
    template = loader.get_template("main/login.html")
    return HttpResponse(template.render())


def register(request):
    template = loader.get_template("main/register.html")
    return HttpResponse(template.render())


def register_success(request):
    template = loader.get_template("main/success.html")
    return HttpResponse(template.render())


def user_panel(request):  # in bayad be jaye dorost bere.
    template = loader.get_template("user_panel.html")
    return HttpResponse(template.render())
