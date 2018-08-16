from django.contrib.auth.mixins import PermissionRequiredMixin
from django.contrib.auth.views import PasswordChangeView
import os
# Create your views here.
from django.urls import reverse_lazy
from django.views.generic import UpdateView, ListView, View, FormView
from django.views.generic.edit import ProcessFormView
from django.views.generic.list import BaseListView, MultipleObjectMixin

from apps.customer.models import Customer
from apps.employee.models import Employee
from apps.manager.models import Manager
from apps.main.MultiForm import MultiFormsView
from apps.main.Forms import UserPasswordChangeForm
from django.views.generic import TemplateView
from apps.main.views import IsLoggedInView, DetailsView, IsManager, CustomerDetailsView, EmployeeDetailsView
from apps.manager.Forms import EmployeeCreationForm, EmployeeAccessRemovalForm, ChangeSalaryForm, \
    CustomerAccessRemovalForm


class ManagerDashboardView(IsLoggedInView, IsManager, TemplateView):
    template_name = "manager/dashboard.html"


class ManagerPasswordChangeView(IsManager, FormView):
    form_class = UserPasswordChangeForm
    success_url = reverse_lazy('main:login')
    template_name = 'manager/change_password.html'

    def get_form_kwargs(self):
        kwargs = super(ManagerPasswordChangeView, self).get_form_kwargs()
        kwargs['user'] = Manager.objects.get(username=self.request.user)
        return kwargs

    def form_valid(self,form):
        form.save()
        return super().form_valid(form)

class CompanySettingsView(IsLoggedInView, IsManager, UpdateView):
    #model = Company
    template_name = "manager/settings.html"
    fields = ['english_name', 'persian_name', 'account', 'photo']
    # todo incomplete


class CustomerDetailsForManager(IsManager, CustomerDetailsView):
    ""


class EmployeeDetailsForManager(IsManager, EmployeeDetailsView):
    ""


class CustomersListView(IsLoggedInView, IsManager, FormView):
    template_name = "manager/users.html"
    form_class = CustomerAccessRemovalForm
    success_url = reverse_lazy("manager:customer_users")

    def post(self, request, *args, **kwargs):
        form_class = self.form_class
        form = self.get_form(form_class)
        if form.is_valid():
            the_customer = Customer.objects.filter(username=form.cleaned_data['username'])[0]
            the_customer.is_active = False
            the_customer.save()
            return self.form_valid(form)
        else:
            return self.form_invalid(form)

    def get_context_data(self, **kwargs):
        context = super(CustomersListView, self).get_context_data(**kwargs)
        context['object_list'] = Customer.objects.all()
        context['type'] = "مشتری"
        return context

    def get_queryset(self):
        return Customer.objects.all()


class EmployeeListView(IsLoggedInView, IsManager, MultiFormsView):
    template_name = "manager/users.html"
    form_classes = {'add_employee': EmployeeCreationForm, 'remove_access': EmployeeAccessRemovalForm,
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

    def remove_access_form_valid(self, form):
        the_employee = Employee.objects.filter(username=form.cleaned_data['username'])[0]
        the_employee.is_active = False
        the_employee.save()
        new_form = EmployeeAccessRemovalForm()
        context = self.get_context_data(object_list=Employee.objects.all(), add_employee=new_form)
        return self.render_to_response(context)
