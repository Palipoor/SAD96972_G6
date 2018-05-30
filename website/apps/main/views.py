from django.contrib.auth.mixins import LoginRequiredMixin, PermissionRequiredMixin
from django.shortcuts import render
from django.http import HttpResponse
from django.template import Context, loader
import os
# Create your views here.
from django.urls import reverse_lazy
from django.views import generic
from django.views.generic import UpdateView, FormView, ListView
from django.views.generic.detail import SingleObjectMixin
from django.views.generic.edit import FormMixin
from django.views.generic.list import MultipleObjectTemplateResponseMixin

from apps.main.Forms import SignUpForm
from apps.manager.models import WebsiteUser, Customer, Transaction, Employee


class IsLoggedInView(LoginRequiredMixin):
    def get_redirect_field_name(self):
        return 'main/login.html'

    def get_permission_denied_message(self):
        return 'شما هنوز وارد سیستم نشده اید.'


class WalletView(IsLoggedInView, PermissionRequiredMixin, FormMixin, ListView):
    def has_permission(self):
        return self.request.user.user_type == 'customer' or self.request.user.user_type == 'manager'

    def dispatch(self, request, *args, **kwargs):
        currency = kwargs['currency']
        user = self.request.user
        user_type = request.user.user_type
        if user_type == 'customer':
            username = request.user.username
            return self.wallet(currency, username)
        else:
            username = ""
            return self.wallet(currency, username)

    # todo form
    # todo retrieve wallet credit and wallet transactions! give them as a context to render function!
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


class EmployeeDetailsView(DetailsView):
    def has_permission(self):
        return self.request.user.user_type == 'manager' or self.request.user.user_type == 'employee' and self.request.user.id == self.employee_id

    model = Employee

    def dispatch(self, request, *args, **kwargs):
        self.employee_id = kwargs['employee_id']
        # todo incomplete


class TransactionDetailsView(DetailsView):
    def has_permission(self):
        return self.request.user.user_type == 'manager' or self.request.user.user_type == 'employee'

    model = Transaction
    # todo incomplete


class NotificationsView(IsLoggedInView, PermissionRequiredMixin, ListView):
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
