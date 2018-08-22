from django.contrib.auth.mixins import PermissionRequiredMixin
from django.contrib.auth.views import PasswordChangeView
import os
# Create your views here.
from django.urls import reverse_lazy
from django.views.generic import UpdateView, ListView, View, FormView, CreateView
from django.views.generic.edit import ProcessFormView
from django.views.generic.list import BaseListView, MultipleObjectMixin
from views import Compilation
from apps.customer.models import Customer, CustomTransactionType
from apps.employee.models import Employee
from apps.main.MultiForm import MultiFormsView
from django.views.generic import TemplateView
from apps.main.views import IsLoggedInView, DetailsView, IsManager, CustomerDetailsView, EmployeeDetailsView
from apps.manager.Forms import EmployeeCreationForm, EmployeeAccessRemovalForm, ChangeSalaryForm, \
    CustomerAccessRemovalForm
from apps.manager.Forms import ReviewForm
from apps.manager.models import Manager


class ManagerFormView(FormView):

    def get_context_data(self, **kwargs):
        context = super().get_context_data(**kwargs)
        context, manager = Compilation.get_manager_context_data(context, self.request.user.username)
        return context


class ManagerCreateView(CreateView):

    def get_context_data(self, **kwargs):
        context = super().get_context_data(**kwargs)
        context, manager = Compilation.get_manager_context_data(context, self.request.user.username)
        return context


class CreateTransactionTypeView(IsLoggedInView, IsManager, ManagerCreateView):
    # TODO has problems with being child og managercreateview probably because of its get context
    template_name = "customer/render_form.html"
    model = CustomTransactionType
    fields = '__all__'

    def get_success_url(self):
        return ""


class ManagerDashboardView(IsLoggedInView, IsManager, ManagerFormView):
    template_name = "manager/dashboard.html"
    form_class = ReviewForm

    def get_success_url(self):
        # success_url = reverse_lazy(EmployeeDashboardView.as_view)
        # TODO use reverse
        return ""

    def get_context_data(self, **kwargs):
        context = super(ManagerFormView, self).get_context_data(**kwargs)
        context = Compilation.get_all_requests(context)
        return context

    def get_form_kwargs(self):
        kwargs = super(ManagerFormView, self).get_form_kwargs()
        kwargs['user'] = Manager.objects.get(username=self.request.user)
        return kwargs

    def form_valid(self, form):
        form.update_db()
        return super().form_valid(form)


class ManagerPasswordChangeView(IsLoggedInView, IsManager, PasswordChangeView):
    success_url = reverse_lazy('manager:change_password')
    template_name = 'manager/change_password.html'


class CompanySettingsView(IsLoggedInView, IsManager, UpdateView):
    # model = Company
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
