from django import forms
from phonenumber_field.modelfields import PhoneNumberField

USERNAME = forms.TextInput(attrs={'name': 'username', 'class': 'form-control', 'placeholder': 'نام کاربری'})
PASSWORD = forms.PasswordInput(attrs={'name': 'password', 'class': 'form-control', 'placeholder': 'کلمه عبور'})
PASSWORD2 = forms.PasswordInput(attrs={'name': 'password-repeat', 'class': 'form-control', 'placeholder': 'تکرار کلمه عبور'})
EMAIL = forms.EmailInput(attrs={'name': 'email', 'class': 'form-control', 'placeholder': 'example@site.domain'})


FIRST_NAME = forms.TextInput(attrs={'name': 'english-first-name', 'class': 'form-control'})
LAST_NAME = forms.TextInput(attrs={'name': 'english-last-name', 'class': 'form-control'})
PERSIAN_FIRST_NAME = forms.TextInput(attrs={'name': 'persian-first-name', 'class': 'form-control'})
PERSIAN_LAST_NAME = forms.TextInput(attrs={'name': 'persian-last-name', 'class': 'form-control'})

PHONE = forms.TextInput(attrs={'name': 'phone-number', 'class': 'form-control'})
URL = forms.TextInput(attrs={'name': 'url', 'class': 'form-control', 'class': 'form-control'})
FILE = forms.FileInput(attrs={'name': 'file', 'class': 'form-control', 'class': 'form-control'})
ACCOUNT_NUMBER = forms.TextInput(attrs={'name': 'account-number', 'class': 'form-control'})

AMOUNT = forms.TextInput(attrs={'name': 'desired-amount', 'class': 'form-control'})
