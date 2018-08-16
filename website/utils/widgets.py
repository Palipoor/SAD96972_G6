from django import forms
from phonenumber_field.modelfields import PhoneNumberField

USERNAME = forms.TextInput(attrs={'name': 'username', 'class': 'form-control', 'placeholder': 'نام کاربری'})
PASSWORD = forms.PasswordInput(attrs={'name': 'password', 'class': 'form-control', 'placeholder': 'کلمه عبور'})
PASSWORD2 = forms.PasswordInput(attrs={'name': 'password-repeat', 'class': 'form-control', 'placeholder': 'تکرار کلمه عبور'})
EMAIL = forms.EmailInput(attrs={'name': 'email', 'class': 'form-control', 'placeholder': 'example@site.domain'})


FIRST_NAME = forms.TextInput(attrs={'name': 'first-name', 'class': 'form-control','placeholder': 'نام به انگلیسی'})
LAST_NAME = forms.TextInput(attrs={'name': 'last-name', 'class': 'form-control', 'placeholder': 'نام خانوادگی به انگلیسی'})
PERSIAN_FIRST_NAME = forms.TextInput(attrs={'name': 'persian-first-name', 'class': 'form-control', 'placeholder': 'نام به فارسی'})
PERSIAN_LAST_NAME = forms.TextInput(attrs={'name': 'persian-last-name', 'class': 'form-control', 'placeholder': 'نام خانوادگی به فارسی'})

PHONE = forms.TextInput(attrs={'name': 'phone-number', 'class': 'form-control', 'placeholder': '09*********'})
URL = forms.TextInput(attrs={'name': 'url', 'class': 'form-control', 'class': 'form-control'})
FILE = forms.FileInput(attrs={'name': 'file', 'class': 'form-control', 'class': 'form-control'})
ACCOUNT_NUMBER = forms.TextInput(attrs={'name': 'account-number', 'class': 'form-control', 'placeholder': 'شماره حساب'})

AMOUNT = forms.TextInput(attrs={'name': 'desired-amount', 'class': 'form-control', 'id' : 'amount'})
