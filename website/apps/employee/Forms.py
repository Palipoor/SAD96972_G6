from django import forms
from django.forms import ModelForm
from utils import fields

from apps.employee.models import Employee


class FaultReportForm(forms.Form):
    transactionID = forms.IntegerField()
    reason = forms.CharField(widget=forms.Textarea)


class EmployeeSettingsForm(ModelForm):
    class Meta:
        model = Employee
        fields = ['username', 'first_name', 'last_name', 'persian_first_name', 'persian_last_name']
    username = fields.USERNAME
    first_name = fields.FIRST_NAME
    last_name = fields.LAST_NAME
    persian_first_name = fields.PERSIAN_FIRST_NAME
    persian_last_name = fields.PERSIAN_LAST_NAME