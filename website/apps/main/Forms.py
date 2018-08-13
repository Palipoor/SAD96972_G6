import unicodedata
from django import forms
from django.contrib.auth.models import User

from apps.customer.models import Customer, Exchange
from apps.main.models import GenUser, Wallet_User
from apps.manager.models import Manager
from utils.strings import *
from utils import fields
from utils.currency_utils import Transactions


class ConvertForm(forms.Form):
    amount = fields.AMOUNT
    amount.error_messages = {'invalid': INVALID_AMOUNT}
    conversion_type = forms.ChoiceField(choices=[('rial2euro', 'ده هزار ریال به یورو'),
                                                 ('rial2dollar', 'ده هزار ریال به دلار'),
                                                 ('dollar2euro', 'دلار به یورو'),
                                                 ('dollar2rial', 'دلار به ده هزار ریال'),
                                                 ('euro2rial', 'یورو به ده هزار ریال'),
                                                 ('euro2dollar', 'یورو به دلار')])


class ContactForm(forms.Form):

    email = fields.EMAIL
    email.error_messages = {'required': FIELD_REQUIRED, 'invalid': INVALID_EMAIL}
    name = fields.PERSIAN_FIRST_NAME
    name.error_messages = {'required': FIELD_REQUIRED}
    subject = forms.CharField(widget=forms.TextInput(attrs={'name': 'subject'}), required=True, error_messages={'required': FIELD_REQUIRED})
    message = forms.CharField(widget=forms.Textarea(attrs={'name': 'message'}), required=True, error_messages={'required': FIELD_REQUIRED})

    def __init__(self, *args, **kwargs):
        super(ContactForm, self).__init__(*args, **kwargs)


class SignUpForm(forms.Form):
    username = fields.USERNAME
    password = fields.PASSWORD
    password2 = fields.PASSWORD2
    first_name = fields.FIRST_NAME
    last_name = fields.LAST_NAME
    email = fields.EMAIL
    account_number = fields.ACCOUNT_NUMBER
    phone_number = fields.PHONE
    phone_number.required = True
    phone_number.error_messages = {'required': FIELD_REQUIRED}
    i_agree = forms.BooleanField(label='با قوانین و مقررات موافقم', required=True, error_messages={'required': FIELD_REQUIRED})

    def clean_username(self):
        if GenUser.objects.filter(username=self.cleaned_data['username']).exists():
            raise forms.ValidationError(REPEATED_USER)
        return self.cleaned_data['username']

    def clean_email(self):
        if GenUser.objects.filter(email=self.cleaned_data['email']).exists():
            raise forms.ValidationError(REPEATED_USER)
        return self.cleaned_data['email']

    def clean_account_number(self):
        if Customer.objects.filter(account_number=self.cleaned_data['account_number']).exists():
            raise forms.ValidationError(REPEATED_USER)
        return self.cleaned_data['account_number']

    def clean_password2(self):
        if self.cleaned_data['password'] != self.cleaned_data['password2']:
            raise forms.ValidationError('تکرار رمز عبور با آن یکی نیست.')
        return not self.cleaned_data['password2']


class WalletChargeForm(forms.Form):
    amount = forms.IntegerField(min_value=1, label='مبلغ درخواستی',
                                widget=forms.widgets.TextInput(attrs={'name': 'desired-amount'}))

    def __init__(self, *args, **kwargs):
        print("in init")
        self.user = kwargs.pop('user')
        self.dest = kwargs.pop('dest')
        self.source = 0
        super(WalletChargeForm, self).__init__(*args, **kwargs)

    def is_valid(self):
        print('in is valid')
        valid = super(WalletChargeForm, self).is_valid()
        if (not valid):
            return False
        self.needed_value = self.cleaned_data['amount'] * Transactions.get_exchange_rate(self.dest, self.source)
        if (self.source == 0):
            temp = self.user.rial_credit - self.needed_value
        elif (self.source == 1):
            temp = self.user.rial_credit - self.needed_value
        elif (self.source == 2):
            temp = self.user.rial_credit - self.needed_value
        if temp < 0:
            return False
        return True

    def update_db(self):
        print('hello')
        exchange = Exchange(source_user=self.user, dest_user=self.user, source_wallet=self.source, dest_wallet=self.dest, amount=self.needed_value)
        exchange.save()


# class EuroChargeForm(WalletChargeForm):
#     def is_valid(self):
#         user = Wallet_User.objects.get(username=)


# class DollarChargeForm(WalletChargeForm):
#     def is_valid(self):
#         dollar_price = 5000  # todo retrieve it !
#         flag = False
#         valid = super(DollarChargeForm, self).is_valid()
#         if not valid:
#             return False
#         user = self.user
#         if user.groups.filter(name='customer').exists():  # customer
#             the_customer = Customer.objects.get(username=user.username)
#             rial_credit = the_customer.rial_credit
#             converted_amount = int(self.cleaned_data['amount']) * dollar_price
#             if rial_credit < converted_amount:
#                 flag = True
#                 self.errors['not-enough'] = 'موجودی کیف  پول ریالی کافی نیست.'
#             else:
#                 the_customer.rial_credit = rial_credit - converted_amount
#                 the_customer.dollar_cent_credit = the_customer.dollar_cent_credit + int(self.cleaned_data['amount'])
#                 the_customer.save()
#         else:  # manager
#             the_manager = Manager.objects.get(username=user.username)
#             rial_credit = the_manager.company_rial_credit
#             converted_amount = int(self.cleaned_data['amount']) * dollar_price
#             if rial_credit < converted_amount:
#                 flag = True
#                 self.errors['not-enough'] = 'موجودی کیف  پول ریالی کافی نیست.'
#             else:
#                 the_manager.company_rial_credit = rial_credit - converted_amount
#                 the_manager.company_dollar_cent_credit = the_manager.company_dollar_cent_credit + int(self.cleaned_data['amount'])
#                 the_manager.save()

#         return not flag


# class RialChargeForm(WalletChargeForm):
#     def is_valid(self):
#         valid = super(RialChargeForm, self).is_valid()
#         if not valid:
#             return False
#         user = self.user
#         charge_amount = int(self.cleaned_data['amount'])
#         if user.groups.filter(name='customer').exists():  # customer
#             the_customer = Customer.objects.get(username=user.username)
#             the_customer.rial_credit = the_customer.rial_credit + charge_amount
#             the_customer.save()

#         else:  # manager
#             the_manager = Manager.objects.get(username=user.username)
#             the_manager.company_rial_credit = the_manager.company_rial_credit + charge_amount
#             the_manager.save()

#         return True
