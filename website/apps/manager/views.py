from django.contrib.auth.mixins import PermissionRequiredMixin
from django.contrib.auth.views import PasswordChangeView
from django.shortcuts import render
from django.http import HttpResponse
from django.template import Context, loader
import os
# Create your views here.
from django.views.generic import UpdateView, ListView

from apps.main.MultiForm import MultiFormsView
from apps.main.views import IsLoggedInView, DetailsView
from apps.manager.models import Company, Customer


def dashboard(request):
    template = loader.get_template("manager/dashboard.html")
    return HttpResponse(template.render())


class ManagerPasswordChangeView(PasswordChangeView):
    def get_template_names(self):
        return 'manager/change_password.html'


class CompanySettingsView(IsLoggedInView, PermissionRequiredMixin, UpdateView):
    model = Company
    template_name = "manager/settings.html"
    fields = ['english_name', 'persian_name', 'account', 'photo']
    # todo incomplete


class UsersListView(IsLoggedInView, PermissionRequiredMixin, ListView):
    user_type = ""


class CustomersListView(UsersListView):
    template_name = "manager/users.html"

    def get_context_data(self, **kwargs):
        context = super(CustomersListView, self).get_context_data(**kwargs)
        context['type'] = "مشتری"
        return context
        #todo incomplete

class EmployeeListView(UsersListView, MultiFormsView):
    template_name = "manager/users.html"
    form_classes = {'add_employee':"",'remove_access':"",'change_salary':""}
    
    def get_context_data(self, **kwargs):
        context = super(EmployeeListView, self).get_context_data(**kwargs)
        context['type'] = "کارمند"
        return context
        #todo incomplete

def users(request, id, user_type):
    if user_type == "customer":
        return CustomersListView.as_view()
    else:
        return EmployeeListView.as_view()


class CustomerDetailsView(DetailsView, ListView):
    model = Customer
    fields = ['persian_first_name', 'persian_last_name', 'english_first_name', 'english_last_name', 'username', 'email',
              'phone', 'account-number']
    context_object_name = 'transactions'
    template_name = 'manager/customer_details'

    def get_context_data(self, **kwargs):
        return []  # todo query bezan transaction haye user e marboot ro biar va khode adame ro.
