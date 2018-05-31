from django.contrib.auth.mixins import PermissionRequiredMixin
from django.contrib.auth.views import PasswordChangeView
from django.shortcuts import render
from django.http import HttpResponse
from django.template import Context, loader
import os
# Create your views here.
from django.views.generic import UpdateView, FormView, ListView

from apps.main.views import IsLoggedInView


class EmployeeDashboardView(IsLoggedInView, PermissionRequiredMixin, ListView, FormView): #todo not sure if this works:/
    ""

class EmployeePasswordChangeView(PasswordChangeView):
    def get_template_names(self):
        return 'employee/change_password.html'


class EmployeeSettingsView(IsLoggedInView, PermissionRequiredMixin, UpdateView):
    template_name = 'employee/settings.html'
    # todo incomplete
