from django import forms
from utils import fields

class Charge(forms.Form):
    charge = forms.IntegerField()


from django.forms import ModelForm

from apps.customer.models import Customer


class CustomerSettingsForm(ModelForm):
    class Meta:
        model = Customer
        fields = ['first_name', 'last_name', 'persian_first_name', 'persian_last_name', 'account_number', 'phone_number', 'email', 'contact_way']
    
    first_name = fields.FIRST_NAME
    last_name = fields.LAST_NAME
    persian_first_name = fields.PERSIAN_FIRST_NAME
    persian_last_name = fields.PERSIAN_LAST_NAME
    account_number = fields.ACCOUNT_NUMBER
    phone_number = fields.PHONE
    phone_number.required = True
    email = fields.EMAIL
    email.required = True
