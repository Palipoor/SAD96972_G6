from django.contrib.auth.mixins import PermissionRequiredMixin
from django.contrib.auth.views import PasswordChangeView
import os
# Create your views here.
from django.views.generic import UpdateView, ListView, View

from apps.customer.models import Customer
from apps.employee.models import Employee
from apps.main.MultiForm import MultiFormsView
from apps.main.views import IsLoggedInView, DetailsView, IsManager
from apps.manager.Forms import EmployeeCreationForm, AccessRemovalForm, ChangeSalaryForm


class ManagerDashboardView(IsLoggedInView, PermissionRequiredMixin, View):
    template_name = "manager/dashboard.html"
    ""


class ManagerPasswordChangeView(IsLoggedInView, IsManager, PasswordChangeView):
    template_name = 'manager/change_password.html'


class CompanySettingsView(IsLoggedInView, IsManager, UpdateView):
    # model = Company
    template_name = "manager/settings.html"
    fields = ['english_name', 'persian_name', 'account', 'photo']
    # todo incomplete


class UsersListView(IsLoggedInView, IsManager, ListView):
    template_name = "manager/users.html"
    user_type = ""


class EmployeeDetailsView(DetailsView):
    template_name = 'manageer/employee_details.html'

    # model = Employee

    # def get_object(self, queryset=None):
    #   return Employee.objects.get(id=self.employee_id)  # todo id e ya username?

    def dispatch(self, request, *args, **kwargs):
        self.employee_id = kwargs['employee_id']
        # todo incomplete


class CustomersListView(UsersListView):
    def get_context_data(self, **kwargs):
        context = super(CustomersListView, self).get_context_data(**kwargs)
        context['type'] = "مشتری"
        return context


    def get_queryset(self):
        return Customer.objects.all()


class EmployeeListView(UsersListView, MultiFormsView):
    form_classes = {'add_employee': EmployeeCreationForm, 'remove_access': AccessRemovalForm,
                    'change_salary': ChangeSalaryForm}

    def get_context_data(self, **kwargs):
        context = super(EmployeeListView, self).get_context_data(**kwargs)
        context['type'] = "کارمند"
        return context

    def get_queryset(self):
        return Employee.objects.all()
