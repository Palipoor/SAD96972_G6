from django import forms
from django.contrib.auth.forms import UserCreationForm
from django.db import models

from apps.manager.models import Customer


class SignUpForm(UserCreationForm):

    email = forms.EmailField(max_length=70, unique=True, null=False)
    persian_first_name = forms.CharField(max_length=50, help_text='به زبان فارسی')
    persian_last_name = forms.CharField(max_length=50, help_text= 'به زبان فارسی')
    phone_number = forms.RegexField(regex=r'^\+?1?\d{9,15}$',
                                    error_message=(
                                    "Phone number must be entered in the format: '+999999999'. Up to 15 digits allowed."))
    account_number = forms.IntegerField()

    class Meta:
        model = Customer
        fields = ('username', 'persian_first_name', 'persian_last_name', 'email', 'phone_number','account_number','password1', 'password2', )


