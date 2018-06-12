from django.contrib.auth.mixins import PermissionRequiredMixin
from django.contrib.auth.views import PasswordChangeView
import os
# Create your views here.
from django.views.generic import UpdateView, ListView, View, FormView
from django.views.generic.edit import ProcessFormView
from django.views.generic.list import BaseListView, MultipleObjectMixin

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


class EmployeeDetailsView(DetailsView):
    template_name = 'manageer/employee_details.html'

    # model = Employee

    # def get_object(self, queryset=None):
    #   return Employee.objects.get(id=self.employee_id)  # todo id e ya username?

    def dispatch(self, request, *args, **kwargs):
        self.employee_id = kwargs['employee_id']
        # todo incomplete


class CustomersListView(IsLoggedInView, IsManager, ListView, ProcessFormView):
    template_name = "manager/users.html"

    def get_context_data(self, **kwargs):
        context = super(CustomersListView, self).get_context_data(**kwargs)
        context['type'] = "مشتری"
        return context

    def get_queryset(self):
        return Customer.objects.all()


class EmployeeListView(IsLoggedInView, IsManager, MultiFormsView):
    template_name = "manager/users.html"
    form_classes = {'add_employee': EmployeeCreationForm, 'remove_access': AccessRemovalForm,
                    'change_salary': ChangeSalaryForm}

    def get_context_data(self, **kwargs):
        context = super(EmployeeListView, self).get_context_data(**kwargs)
        context['type'] = "کارمند"
        context['object_list'] = Employee.objects.all()
        return context

    def add_employee_form_valid(self, form):
        new_user = Employee(username=form.cleaned_data['username'],
                            persian_first_name=form.cleaned_data["persian_first_name"],
                            persian_last_name=form.cleaned_data["persian_last_name"],
                            current_salary=form.cleaned_data['current_salary'])
        new_user.set_password("someRandomPassword")  # todo generate random
        new_user.save()
        new_form = EmployeeCreationForm()
        context = self.get_context_data(object_list=Employee.objects.all(), add_employee=new_form)
        return self.render_to_response(context)

    def change_salary_form_valid(self, form):
        the_employee = Employee.objects.filter(username=form.cleaned_data['username'])[0]
        the_employee.current_salary = form.cleaned_data['current_salary']
        the_employee.save()
        new_form = ChangeSalaryForm()
        context = self.get_context_data(object_list=Employee.objects.all(), add_employee=new_form)
        return self.render_to_response(context)
