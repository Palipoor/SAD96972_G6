from django import forms

class Charge(forms.Form):
    charge = forms.IntegerField()

from django.forms import ModelForm

from apps.customer.models import Customer


class CustomerSettingsForm(ModelForm):
    class Meta:
        model = Customer
        fields = ['first_name', 'last_name', 'persian_first_name', 'persian_last_name', 'account_number', 'phone_number', 'email']
