from django import forms
from django.contrib.auth.models import User

from apps.customer.models import Customer
from apps.main.models import GenUser
from apps.manager.models import Manager


class SignUpForm(forms.Form):
    username = forms.CharField(max_length=100, required=True, label='نام کاربری',
                               widget=forms.widgets.TextInput(attrs={'name': 'username'}))
    password = forms.CharField(max_length=50, required=True, label='کلمه عبور',
                               widget=forms.PasswordInput(attrs={'name': 'password'}))
    password2 = forms.CharField(max_length=50, required=True, label='تکرار کلمه عبور',
                                widget=forms.PasswordInput(attrs={'name': 'password-repeat'}))
    first_name = forms.CharField(max_length=50, required=True, label='نام  به انگلیسی',
                                 widget=forms.widgets.TextInput(attrs={'name': 'english-first-name'}))
    last_name = forms.CharField(max_length=50, required=True, label='نام خانوادگی  به انگلیسی',
                                widget=forms.widgets.TextInput(attrs={'name': 'english-last-name'}))
    persian_first_name = forms.CharField(max_length=50, required=True, label='نام به فارسی',
                                         widget=forms.widgets.TextInput(attrs={'name': 'persian-first-name'}))
    persian_last_name = forms.CharField(max_length=50, required=True, label='نام خانوادگی به فارسی',
                                        widget=forms.widgets.TextInput(attrs={'name': 'persian-last-name'}))
    email = forms.EmailField(max_length=70, required=True, label='ایمیل',
                             widget=forms.widgets.EmailInput(attrs={'name': 'email'}))
    account_number = forms.IntegerField(label='شماره حساب', widget=forms.widgets.NumberInput(
            attrs={'name': 'account-number'}))  # todo behtaresh konim vase validation
    phone_number = forms.IntegerField(label='شماره تماس', widget=forms.widgets.NumberInput(
            attrs={'name': 'phone-number'}))  # todo behtaresh konim vase validation
    i_agree = forms.BooleanField(label='با قوانین و مقررات موافقم', required=True)

    def is_valid(self):
        flag = False
        valid = super(SignUpForm, self).is_valid()
        if not valid:
            flag = True

        if GenUser.objects.filter(username=self.cleaned_data['username']).exists():
            self.errors['username'] = 'کاربری با این مشخصات وجود دارد'
            flag = True

        if GenUser.objects.filter(email=self.cleaned_data['email']).exists():
            self.errors['email'] = 'کاربری با این ایمیل وجود دارد'
            flag = True

        if Customer.objects.filter(account_number=self.cleaned_data['account_number']).exists():
            self.errors['account_number'] = 'کاربری با این شماره حساب وجود دارد'
            flag = True

        if self.cleaned_data['password'] != self.cleaned_data['password2']:
            self._errors['repeat_not_match'] = 'تکرار رمز عبور با آن یکی نیست'
            flag = True
        return not flag


class WalletChargeForm(forms.Form):
    amount = forms.IntegerField(min_value=1, label='مبلغ درخواستی',
                                widget=forms.widgets.TextInput(attrs={'name': 'desired-amount'}))

    def __init__(self, user, *args, **kwargs):
        super(WalletChargeForm, self).__init__(*args, **kwargs)
        self.user = user

    def is_valid(self):
        return super(WalletChargeForm, self).is_valid()


class EuroChargeForm(WalletChargeForm):
    def is_valid(self):
        euro_price = 5000  # todo retrieve it !
        flag = False
        valid = super(EuroChargeForm, self).is_valid()
        if not valid:
            return False
        user = self.user
        if user.user_type == 0:  # customer
            the_customer = Customer.objects.get(username=user.username)
            rial_credit = the_customer.rial_credit
            converted_amount = int(self.cleaned_data['amount']) * euro_price
            if rial_credit < converted_amount:
                flag = True
                self.errors['not-enough'] = 'موجودی کیف  پول ریالی کافی نیست.'
            else:
                the_customer.rial_credit = rial_credit - converted_amount
                the_customer.euro_credit = the_customer.euro_credit + converted_amount
            the_customer.save()
        else:  # manager
            the_manager = Manager.objects.get(username=user.username)
            rial_credit = the_manager.company_rial_credit
            converted_amount = int(self.cleaned_data['amount']) * euro_price
            if rial_credit < converted_amount:
                flag = True
                self.errors['not-enough'] = 'موجودی کیف  پول ریالی کافی نیست.'
            else:
                the_manager.company_rial_credit = rial_credit - converted_amount
                the_manager.company_euro_credit = the_manager.company_euro_credit + converted_amount
                the_manager.save()

        return not flag


class DollarChargeForm(WalletChargeForm):
    def is_valid(self):
        dollar_price = 5000  # todo retrieve it !
        flag = False
        valid = super(DollarChargeForm, self).is_valid()
        if not valid:
            return False
        user = self.user
        if user.user_type == 0:  # customer
            the_customer = Customer.objects.get(username=user.username)
            rial_credit = the_customer.rial_credit
            converted_amount = int(self.cleaned_data['amount']) * dollar_price
            if rial_credit < converted_amount:
                flag = True
                self.errors['not-enough'] = 'موجودی کیف  پول ریالی کافی نیست.'
            else:
                the_customer.rial_credit = rial_credit - converted_amount
                the_customer.dollar_credit = the_customer.dollar_credit + converted_amount
                the_customer.save()
        else:  # manager
            the_manager = Manager.objects.get(username=user.username)
            rial_credit = the_manager.company_rial_credit
            converted_amount = int(self.cleaned_data['amount']) * dollar_price
            if rial_credit < converted_amount:
                flag = True
                self.errors['not-enough'] = 'موجودی کیف  پول ریالی کافی نیست.'
            else:
                the_manager.company_rial_credit = rial_credit - converted_amount
                the_manager.company_dollar_credit = the_manager.company_dollar_credit + converted_amount
                the_manager.save()

        return not flag


class RialChargeForm(WalletChargeForm):
    def is_valid(self):
        valid = super(RialChargeForm, self).is_valid()
        if not valid:
            return False
        user = self.user
        charge_amount = int(self.cleaned_data['amount'])
        if user.groups.filter(name='Customer').exists():  # customer
            the_customer = Customer.objects.get(username=user.username)
            the_customer.rial_credit = the_customer.rial_credit + charge_amount
            the_customer.save()

        else:  # manager
            the_manager = Manager.objects.get(username=user.username)
            the_manager.company_rial_credit = the_manager.company_rial_credit + charge_amount
            the_manager.save()

        return True
