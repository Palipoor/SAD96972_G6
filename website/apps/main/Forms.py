import unicodedata
from django import forms
from django.contrib.auth.models import User
from django.contrib.auth.forms import PasswordChangeForm
from django.core.exceptions import ValidationError

from apps.customer.models import Customer, Exchange, Charge
from apps.main.models import GenUser, Wallet_User
from apps.manager.models import Manager
from utils.strings import *
from utils import fields
from utils.currency_utils import Transactions
from utils import fields


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
    # form for charging wallets
    amount = forms.IntegerField(min_value=1, label='مبلغ درخواستی',
                                widget=forms.widgets.TextInput(attrs={'name': 'desired-amount', 'oninput':'calculate()'}))

    # TODO make exchanging from each wallet possible. make real rial charge possible.
    def __init__(self, *args, **kwargs):
        print("in init")
        self.user = kwargs.pop('user')
        self.dest = kwargs.pop('dest')
        self.source = "0"
        self.transaction = None
        super(WalletChargeForm, self).__init__(*args, **kwargs)

    def clean_amount(self):
        # makes exception if charge is invalid
        self.needed_value = self.cleaned_data['amount'] * Transactions.get_exchange_rate(self.dest, self.source)
        if(self.dest != "0"):
            self.transaction = Exchange(source_user=self.user, dest_user=self.user, source_wallet=self.source, dest_wallet=self.dest, amount=self.needed_value)
        else:
            print("hello")
            self.transaction = Charge(dest_user=self.user, dest_wallet=self.dest, amount=self.needed_value)
        # TODO generalize peyments
        # exps = self.transaction.exception_texts()
        # print("clean_amount")
        # print(exps)
        # if (exps):
        #     raise ValidationError(exps[0])
        return self.cleaned_data['amount']

    def clean(self):
        temp = super().clean()
        self.transaction.full_clean()
        return temp

    def update_db(self):
        # updates db
        self.transaction.save()
        pass


class UserPasswordChangeForm(forms.Form):
    error_messages = {
        'password_mismatch': "تکرار رمز عبور با آن یکسان نیست."
    }

    old_password = forms.CharField(
        label="رمز عبور فعلی",
        widget=forms.PasswordInput(attrs={'autofocus': True, 'class': 'form-control'}),
    )

    new_password1 = forms.CharField(
        label="رمز عبور جدید",
        widget=forms.PasswordInput(attrs = {'class': 'form-control'})
    )
    new_password2 = forms.CharField(
        label="تکرار رمز عبور جدید",
        widget=forms.PasswordInput(attrs = {'class': 'form-control'})
    )

    def __init__(self, *args, **kwargs):
        self.user = kwargs.pop('user')
        super().__init__(*args, **kwargs)

    def clean_old_password(self):
        if not self.user.check_password(self.cleaned_data['old_password']):
            raise forms.ValidationError(WRONG_PASSWORD)
        else:
            return self.cleaned_data['old_password']

    def clean_new_password2(self):
        password1 = self.cleaned_data.get('new_password1')
        password2 = self.cleaned_data.get('new_password2')
        if password1 and password2:
            if password1 != password2:
                raise forms.ValidationError(
                    self.error_messages['password_mismatch'])
        return password2

    def save(self, commit=True):
        password = self.cleaned_data["new_password1"]
        self.user.set_password(password)
        if commit:
            self.user.save()
        return self.user
