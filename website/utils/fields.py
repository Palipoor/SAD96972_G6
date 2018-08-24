from django import forms
from utils import widgets
from utils.strings import *
from utils.validators import *
from django.contrib.auth.validators import ASCIIUsernameValidator

USERNAME = forms.CharField(max_length=50, required=True, label='نام کاربری', widget=widgets.USERNAME, error_messages={
                           'required': FIELD_REQUIRED, 'invalid': 'لطفا فقط از حروف انگلیسی، اعداد و علامت ـ استفاده کنید.'}, validators=[ASCIIUsernameValidator])
PASSWORD = forms.CharField(max_length=50, required=True, label='کلمه عبور', widget=widgets.PASSWORD,
                           error_messages={'required': FIELD_REQUIRED})
PASSWORD2 = forms.CharField(max_length=50, required=True, label='تکرار کلمه عبور', widget=widgets.PASSWORD2, error_messages={'required': FIELD_REQUIRED})
FIRST_NAME = forms.CharField(max_length=50, required=True, label='نام  به انگلیسی',
                             widget=widgets.FIRST_NAME, error_messages={'required': FIELD_REQUIRED},  validators=[english_name_validator])
LAST_NAME = forms.CharField(max_length=50, required=True, label='نام خانوادگی  به انگلیسی',
                            widget=widgets.LAST_NAME, error_messages={'required': FIELD_REQUIRED},  validators=[english_name_validator])
PERSIAN_FIRST_NAME = forms.CharField(max_length=50, required=True, label='نام به فارسی',
                                     widget=widgets.PERSIAN_FIRST_NAME, error_messages={'required': FIELD_REQUIRED}, validators=[persian_name_validator])

PERSIAN_LAST_NAME = forms.CharField(max_length=50, required=True, label='نام خانوادگی به فارسی',
                                    widget=widgets.PERSIAN_LAST_NAME, error_messages={'required': FIELD_REQUIRED}, validators=[persian_name_validator])
EMAIL = forms.EmailField(max_length=70, required=True, label='ایمیل',
                         widget=widgets.EMAIL, error_messages={'required': FIELD_REQUIRED})
ACCOUNT_NUMBER = forms.IntegerField(
    label='شماره حساب', required=True, widget=widgets.ACCOUNT_NUMBER, error_messages={'required': FIELD_REQUIRED, 'invalid': INVALID_ACCOUNT})

PHONE = forms.IntegerField(
    label='شماره تماس', widget=widgets.PHONE)

AMOUNT = forms.IntegerField(min_value=1, label='مبلغ درخواستی',
                            widget=widgets.AMOUNT, error_messages={'invalid': INVALID_AMOUNT})
DESCRIPTION = forms.CharField(label = "توضیح" , widget=forms.Textarea(attrs = {'class': 'form-control'}))
