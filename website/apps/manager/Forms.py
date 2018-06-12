import random
from django import forms
from apps.employee.models import Employee
from django.db.models import AutoField
from django.core.mail import send_mail

from apps.main.models import GenUser



class EmployeeCreationForm(forms.ModelForm):
    class Meta:
        model = Employee
        fields = ['username', 'current_salary', 'email', 'persian_first_name',
                  'persian_last_name']  # todo add this to tests.

    def is_valid(self):
        valid = super(EmployeeCreationForm, self).is_valid()
        if not valid:
            return False

        if GenUser.objects.filter(username=self.cleaned_data['username']).exists():
            self.errors['username'] = 'کاربری با این نام کاربری وجود دارد'
            return False

        return True


class ChangeSalaryForm(forms.Form):
    username = forms.CharField(max_length=100, required=True, label='نام کاربری')
    current_salary = forms.FloatField(required=True, label='حقوق جدید')

    def is_valid(self):
        valid = super(ChangeSalaryForm, self).is_valid()
        if not valid:
            return False
        if not Employee.objects.filter(username=self.cleaned_data['username']).exists():
            self.errors['username'] = 'چنین کارمندی وجود ندارد.'
            return False

        return True



class AccessRemovalForm(forms.Form):
    username = forms.CharField(max_length=100, required=True, label='نام کاربری')
    reason = forms.CharField(max_length=2000, label='دلیل قطع دسترسی', widget=forms.Textarea)

    def is_valid(self):
        valid = super(AccessRemovalForm, self).is_valid()
        if not valid:
            return False
        if not Employee.objects.filter(username=self.cleaned_data['username']).exists():
            self.errors['username'] = 'چنین کارمندی وجود ندارد.'
            return False

        return True