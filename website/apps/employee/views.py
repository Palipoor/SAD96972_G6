from django.contrib.auth.mixins import PermissionRequiredMixin
from django.contrib.auth.views import PasswordChangeView
from django.shortcuts import render
from django.http import HttpResponse
from django.template import Context, loader
import os
from django.urls import reverse
from django.urls import reverse_lazy
from django.views.generic import UpdateView, FormView, ListView, TemplateView, DetailView

from apps.employee.Forms import EmployeeSettingsForm, ReviewForm
from apps.employee.models import Employee
from apps.main.views import IsLoggedInView, IsEmployee
from apps.main.Forms import UserPasswordChangeForm
from apps.customer.models import Request
from apps.main.models import Notification
from apps.main.views import IsLoggedInView, IsEmployee, Compilation
from apps.main.views import TransactionDetailsView as MainTransactionDetails
from django.urls import reverse_lazy
from django.views.generic.edit import FormMixin
from django.contrib import messages
from django.http import HttpResponseRedirect



class EmployeeTemplateView(TemplateView):
    def get_context_data(self, **kwargs):
        context = super().get_context_data(**kwargs)
        context, employee = Compilation.get_employee_context_data(context, self.request.user.username)
        return context


class EmployeeFormView(FormView):
    def get_context_data(self, **kwargs):
        context = super().get_context_data(**kwargs)
        context, employee = Compilation.get_employee_context_data(context, self.request.user.username)
        return context


class EmployeeDashboardView(IsLoggedInView, IsEmployee, EmployeeFormView):
    template_name = "employee/dashboard.html"
    form_class = ReviewForm

    def get_success_url(self):
        return "employee:dashboard"

    def get_context_data(self, **kwargs):
        context = super(EmployeeFormView, self).get_context_data(**kwargs)
        context = Compilation.get_all_requests(context)
        context.update({'notifications': Notification.objects.filter(user__username=self.request.user.username, seen=False).order_by('-sent_date')})
        context = Compilation.get_last_request_and_transaction_id(context)
        return context

    def get_form_kwargs(self):
        kwargs = super(EmployeeFormView, self).get_form_kwargs()
        kwargs['user'] = Employee.objects.get(username=self.request.user)
        return kwargs

    def form_valid(self, form):
        form.update_db()
        messages.add_message(
            self.request, messages.SUCCESS, 'بررسی تراکنش با موفقیت انجام شد.')
        return HttpResponseRedirect(reverse(self.get_success_url()))


class EmployeePasswordChangeView(IsEmployee, FormView):
    form_class = UserPasswordChangeForm
    success_url = reverse_lazy('main:login')
    template_name = 'employee/change_password.html'

    def get_context_data(self, **kwargs):
        context = super(EmployeePasswordChangeView, self).get_context_data(**kwargs)
        context = Compilation.get_last_request_and_transaction_id(context)
        return context

    def get_form_kwargs(self):
        kwargs = super(EmployeePasswordChangeView, self).get_form_kwargs()
        kwargs['user'] = Employee.objects.get(username=self.request.user)
        return kwargs

    def form_valid(self, form):
        form.save()
        return super().form_valid(form)


class EmployeeSettingsView(IsLoggedInView, IsEmployee, UpdateView):
    form_class = EmployeeSettingsForm
    template_name = 'employee/settings.html'
    success_url = reverse_lazy('employee:settings')

    def get_context_data(self, **kwargs):
        context = super(EmployeeSettingsView, self).get_context_data(**kwargs)
        context = Compilation.get_last_request_and_transaction_id(context)
        return context

    def get_object(self, queryset=None):
        username = self.request.user.username
        return Employee.objects.get(username=username)

    def form_valid(self, form):
        clean = form.cleaned_data
        context = {}
        self.object = context.update(clean)
        return super(EmployeeSettingsView, self).form_valid(form)


class TransactionDetailsView(FormMixin, MainTransactionDetails):

    template_name = "employee/transaction_details.html"
    form_class = ReviewForm

    def get_success_url(self):
        return reverse('employee:transaction_details', kwargs={'pk': self.object.id})

    def get_context_data(self, **kwargs):
        context = super(TransactionDetailsView, self).get_context_data(**kwargs)
        context.update({'notifications': Notification.objects.filter(user__username=self.request.user.username, seen=False).order_by('-sent_date')})
        context = Compilation.get_last_request_and_transaction_id(context)
        context['form'] = self.get_form()
        return context

    def get_form_kwargs(self):
        kwargs = super(TransactionDetailsView, self).get_form_kwargs()
        kwargs['user'] = Employee.objects.get(username=self.request.user)
        kwargs['transaction_id'] = self.kwargs['pk']
        return kwargs

    def post(self, request, *args, **kwargs):
        self.object = self.get_object()
        form = self.get_form()
        if form.is_valid():
            return self.form_valid(form)
        else:
            print(form.errors)
            return self.form_invalid(form)

    def form_valid(self, form):
        form.update_db()
        messages.add_message(
            self.request, messages.SUCCESS, 'بررسی تراکنش با موفقیت انجام شد.')
        return super().form_valid(form)

        

class CustomerDetailsForEmployee(IsEmployee, TemplateView):

    template_name = 'employee/customer_details.html'

    def dispatch(self, request, *args, **kwargs):
        self.user_id = kwargs['user_id']
        return super(CustomerDetailsForManager, self).dispatch(request, *args, **kwargs)

    def get_context_data(self, **kwargs):
        context = super(CustomerDetailsForManager, self).get_context_data(**kwargs)
        customer = Customer.objects.get(id=self.user_id)
        context['customer'] = customer
        context = Compilation.get_wallet_requests(context, customer.username, -1)
        context = Compilation.get_last_request_and_transaction_id(context)
        return context
