import random
from django import forms
from utils import fields
from utils.strings import *
from apps.customer.models import Customer
from apps.employee.models import Employee
from django.db.models import AutoField
from django.core.mail import send_mail

from apps.main.models import GenUser


class EmployeeCreationForm(forms.ModelForm):
    class Meta:
        model = Employee
        fields = ['username', 'current_salary', 'email', 'persian_first_name',
                  'persian_last_name', 'first_name', 'last_name']  # todo add this to tests.

    username = fields.USERNAME
    first_name = fields.FIRST_NAME
    last_name = fields.LAST_NAME
    persian_first_name = fields.PERSIAN_FIRST_NAME
    persian_last_name = fields.PERSIAN_LAST_NAME
    email = fields.EMAIL
    current_salary = forms.IntegerField(required=True,  error_messages={'required': FIELD_REQUIRED} , widget = forms.NumberInput(attrs = {'class':'form-control'}))

    def clean_username(self):
        if GenUser.objects.filter(username=self.cleaned_data['username']).exists():
            raise forms.ValidationError(REPEATED_USER)
        return self.cleaned_data['username']

    def clean_email(self):
        if GenUser.objects.filter(email=self.cleaned_data['email']).exists():
            raise forms.ValidationError(REPEATED_USER)
        return self.cleaned_data['email']


class ChangeSalaryForm(forms.Form):
    username = fields.USERNAME
    current_salary = forms.IntegerField(required=True,  error_messages={'required': FIELD_REQUIRED} , widget = forms.NumberInput(attrs = {'class':'form-control'}))

    def clean_username(self):
        if not Employee.objects.filter(username=self.cleaned_data['username']).exists():
            raise forms.ValidationError("چنین کارمندی وجود ندارد.")
        return self.cleaned_data['username']
    
class EmployeeAccessRemovalForm(forms.Form):
    username = fields.USERNAME
    reason = forms.CharField(max_length=2000, label='دلیل قطع دسترسی', widget=forms.Textarea(attrs = {'class': 'form-control', 'placeholder': 'دلیل قطع دسترسی...'}))

    def clean_username(self):
        if not Employee.objects.filter(username=self.cleaned_data['username']).exists():
            raise forms.ValidationError("چنین کارمندی وجود ندارد.")
        return self.cleaned_data['username']
    



class CustomerAccessRemovalForm(forms.Form):
    username = fields.USERNAME
    reason = forms.CharField(max_length=2000, label='دلیل قطع دسترسی', widget=forms.Textarea(attrs = {'class': 'form-control', 'placeholder': 'دلیل قطع دسترسی...'}))

    def clean_username(self):
        if not Customer.objects.filter(username=self.cleaned_data['username']).exists():
            raise forms.ValidationError("چنین مشتری‌ای وجود ندارد.")
        return self.cleaned_data['username']
