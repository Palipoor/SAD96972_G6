from django import forms
from django.forms import ModelForm

from apps.employee.models import Employee


class FaultReportForm(forms.Form):
    transactionID = forms.IntegerField()
    reason = forms.CharField(widget=forms.Textarea)


class EmployeeSettingsForm(ModelForm):
    class Meta:
        model = Employee
        fields = ['username', 'first_name', 'last_name', 'persian_first_name', 'persian_last_name']
