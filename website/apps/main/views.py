from django.contrib.auth.mixins import LoginRequiredMixin, PermissionRequiredMixin
from django.shortcuts import render
from django.http import HttpResponse
from django.template import Context, loader
import os
# Create your views here.
from django.urls import reverse_lazy
from django.views import generic
from django.views.generic import UpdateView, FormView
from django.views.generic.detail import SingleObjectMixin
from django.views.generic.list import MultipleObjectTemplateResponseMixin

from apps.main.Forms import SignUpForm


class IsLoggedInView(LoginRequiredMixin):
    def get_redirect_field_name(self):
        return 'main/login.html'

    def get_permission_denied_message(self):
        return 'شما هنوز وارد سیستم نشده اید.'


class IsManagerView(IsLoggedInView):
    def dispatch(self, request, *args, **kwargs):
        ''' authenticate the user as the manager '''  # todo undone


class IsEmployeeView(IsLoggedInView):
    def dispatch(self, request, *args, **kwargs):
        ''' authenticate the user as an employee (could be an employee or the manager!) '''  # todo undone


class IsCustomerView(IsLoggedInView):
    def dispatch(self, request, *args, **kwargs):
        ''' authenticate the user as a customer'''  # todo undone


class UserSettingsView(IsLoggedInView, PermissionRequiredMixin, UpdateView):
    ""  # todo undone


class WalletView(IsLoggedInView, PermissionRequiredMixin, FormView):
    def dispatch(self, request, *args, **kwargs):
        currency = kwargs['currency']
        user_type = kwargs['user_type']
        if user_type == 'customer':
            url = request.url
            username = " parse username from url ! don't know how "  # todo
            return self.wallet(currency, username)
        else:
            username = ""
            return self.wallet(currency, username)

    # todo authenticate o ina!
    #todo retrieve wallet credit and wallet transactions! give them as a context to render function!
    def wallet(self, currency, username):
        if currency == "dollar":
            currency = "دلار"
        elif currency == "euro":
            currency = "یورو"
        elif currency == "rial":
            currency = "ریال"
        if username != "":
            template = loader.get_template("customer/" + username + "/wallet.html")
            return HttpResponse(template.render({"currency": currency}))
        else:
            template = loader.get_template("manager/" + "/wallet.html")
            return HttpResponse(template.render({"currency": currency}))


class DetailsView(IsLoggedInView, PermissionRequiredMixin, SingleObjectMixin):
    ""  # todo undone


class CustomerDetailsView(DetailsView):
    ""  # todo undone


class EmployeeDetailsView(DetailsView):
    ""  # todo undone


class TransactionDetailsView(DetailsView):
    ""  # todo undone


class NotificationsView(IsLoggedInView, PermissionRequiredMixin, MultipleObjectTemplateResponseMixin):
    ""


def index(request):
    template = loader.get_template("main/index.html")
    return HttpResponse(template.render())


def login(request):
    template = loader.get_template("main/login.html")
    return HttpResponse(template.render())


class Register(generic.CreateView):
    form_class = SignUpForm
    success_url = reverse_lazy('login')
    template_name = 'main/register.html'


def register_success(request):
    template = loader.get_template("main/success.html")
    return HttpResponse(template.render())

# def user_panel(request):  # in bayad be jaye dorost bere.
#     template = loader.get_template("user_panel.html")
#     return HttpResponse(template.render())
