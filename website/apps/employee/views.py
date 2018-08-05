from django.contrib.auth.mixins import PermissionRequiredMixin
from django.contrib.auth.views import PasswordChangeView
from django.shortcuts import render
from django.http import HttpResponse
from django.template import Context, loader
import os
# Create your views here.
from django.urls import reverse_lazy
from django.views.generic import UpdateView, FormView, ListView

from apps.employee.Forms import EmployeeSettingsForm
from apps.employee.models import Employee
from apps.main.views import IsLoggedInView, IsEmployee, EmployeeDetailsView


def dashboard(request):
     template = loader.get_template("employee/dashboard.html")
     return HttpResponse(template.render())
    
class EmployeeDashboardView(IsLoggedInView, IsEmployee, ListView, FormView):  # todo not sure if this works:/
    ""


class EmployeeProfile(IsEmployee, EmployeeDetailsView):
    ""

class EmployeePasswordChangeView(PasswordChangeView, IsEmployee):
    success_url = reverse_lazy('employee:change_password')
    template_name =  'employee/change_password.html'


class EmployeeSettingsView(IsLoggedInView, IsEmployee, UpdateView):
    form_class = EmployeeSettingsForm
    template_name = 'employee/settings.html'
    success_url = reverse_lazy('employee:settings')

    def get_object(self, queryset=None):
        username = self.request.user.username
        return Employee.objects.get(username=username)

    def form_valid(self, form):
        clean = form.cleaned_data
        context = {}
        self.object = context.update(clean)
        return super(EmployeeSettingsView, self).form_valid(form)
