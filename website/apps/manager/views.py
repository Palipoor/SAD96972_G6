from django.contrib.auth.mixins import PermissionRequiredMixin
from django.contrib.auth.views import PasswordChangeView
import os
from utils import notification_tools
from utils import strings
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
            email = the_customer.email
            message = strings.CUSTOMER_ACCESS_REMOVAL + form.cleaned_data['reason']
            the_customer.is_active = False
            notification_tools.send_email(message = message, email  = email, subject = 'اعلان قطع دسترسی')
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


class EmployeeListView(IsLoggedInView, IsManager, FormView):
    add_employee_form_class = EmployeeCreationForm
    remove_access_form_class = EmployeeAccessRemovalForm
    change_salary_form_class = ChangeSalaryForm
    form_class = add_employee_form_class
    template_name = "manager/users.html"
    
    def get_context_data(self, **kwargs):
        context = super(EmployeeListView, self).get_context_data(**kwargs)
        if 'add_employee_form' not in context:
            context['add_employee_form'] = self.add_employee_form_class()
        if 'remove_access_form' not in context:
            context['remove_access_form'] = self.remove_access_form_class()
        if 'change_salary_form' not in context:
            context['change_salary_form'] = self.change_salary_form_class()
        context.update({'type': 'کارمند'})
        context.update({'object_list': Employee.objects.all()})        
        return context

    def post(self, request, *args, **kwargs):
        if 'add_employee_form' in request.POST:
            form_class = self.add_employee_form_class
            form_name = 'add_employee_form'
        elif 'remove_access_form' in request.POST:
            form_class = self.remove_access_form_class
            form_name = 'remove_access_form'
        else:
            form_class = self.change_salary_form_class
            form_name = 'change_salary_form'

        form = self.get_form(form_class)
        if form.is_valid():
            return self.form_valid(form)
        else:
            return self.form_invalid(**{form_name: form})    

    def form_valid(self, form):
        if isinstance(form, EmployeeCreationForm):
            return self.add_employee_form_valid(form)
        elif isinstance(form, EmployeeAccessRemovalForm):
            return self.remove_access_form_valid(form)
        else:
            return self.change_salary_form_valid(form)

    def add_employee_form_valid(self, form):
        new_user = Employee(username=form.cleaned_data['username'],
                            persian_first_name=form.cleaned_data["persian_first_name"],
                            persian_last_name=form.cleaned_data["persian_last_name"],
                            current_salary=form.cleaned_data['current_salary'], 
                            first_name = form.cleaned_data['first_name'],
                            last_name = form.cleaned_data['last_name'], 
                            email = form.cleaned_data['email'])
        new_user.set_password("someRandomPassword")  # todo generate random
        new_user.save()
        new_form = EmployeeCreationForm()
        context = self.get_context_data(object_list=Employee.objects.all(), add_employee=new_form)
        name = form.cleaned_data['persian_first_name'] + ' ' + form.cleaned_data['persian_last_name']
        context.update({'add_success' : name + ' با موفقیت به کارمندان اضافه شد.' })
        return self.render_to_response(context)

    def change_salary_form_valid(self, form):
        the_employee = Employee.objects.filter(username=form.cleaned_data['username'])[0]
        the_employee.current_salary = form.cleaned_data['current_salary']
        the_employee.save()
        new_form = ChangeSalaryForm()
        context = self.get_context_data(object_list=Employee.objects.all(), add_employee=new_form)
        context.update({'change_success' : 'حقوق کارمند مورد نظر تغییر کرد.' })
        return self.render_to_response(context)

    def remove_access_form_valid(self, form):
        the_employee = Employee.objects.filter(username=form.cleaned_data['username'])[0]
        the_employee.is_active = False
        the_employee.save()
        new_form = EmployeeAccessRemovalForm()
        context = self.get_context_data(object_list=Employee.objects.all(), add_employee=new_form)
        context.update({'remove_success' : 'دسترسی کارمند مورد نظر از سامانه قطع شد.'})
        return self.render_to_response(context)

    def form_invalid(self, **kwargs):
        return self.render_to_response(self.get_context_data(**kwargs))