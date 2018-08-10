from django import forms
from utils import widgets
from utils.strings import *

USERNAME = forms.CharField(max_length=50, required=True, label='نام کاربری', widget=widgets.USERNAME, error_messages={'required': FIELD_REQUIRED})
PASSWORD = forms.CharField(max_length=50, required=True, label='کلمه عبور', widget=widgets.PASSWORD,
                           error_messages={'required': FIELD_REQUIRED})
PASSWORD2 = forms.CharField(max_length=50, required=True, label='تکرار کلمه عبور', widget=widgets.PASSWORD2, error_messages={'required': FIELD_REQUIRED})


FIRST_NAME = forms.CharField(max_length=50, required=True, label='نام  به انگلیسی',
                             widget=widgets.FIRST_NAME)
LAST_NAME = forms.CharField(max_length=50, required=True, label='نام خانوادگی  به انگلیسی',
                            widget=widgets.LAST_NAME)
PERSIAN_FIRST_NAME = forms.CharField(max_length=50, required=True, label='نام به فارسی',
                                     widget=widgets.PERSIAN_FIRST_NAME)
PERSIAN_LAST_NAME = forms.CharField(max_length=50, required=True, label='نام خانوادگی به فارسی',
                                    widget=widgets.PERSIAN_LAST_NAME)

EMAIL = forms.EmailField(max_length=70, required=True, label='ایمیل',
                         widget=widgets.EMAIL)
ACCOUNT_NUMBER = forms.IntegerField(
    label='شماره حساب', required=True, widget=widgets.ACCOUNT_NUMBER)
PHONE = forms.IntegerField(
    label='شماره تماس', widget=widgets.PHONE)

AMOUNT = forms.IntegerField(min_value=1, label='مبلغ درخواستی',
                            widget=widgets.AMOUNT, error_messages={'invalid': INVALID_AMOUNT})
