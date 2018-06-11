from django import forms
from django.contrib.auth.models import User

from apps.customer.models import Customer
from apps.main.models import GenUser


class SignUpForm(forms.Form):
    username = forms.CharField(max_length=100, required=True, label='نام کاربری', widget=forms.widgets.TextInput(attrs={'name':'username'}))
    password = forms.CharField(max_length=50, required=True, label='کلمه عبور', widget= forms.PasswordInput)
    password2 = forms.CharField(max_length=50, required=True, label='تکرار کلمه عبور', widget= forms.PasswordInput)
    first_name = forms.CharField(max_length=50, required=True, label= 'نام  به انگلیسی')
    last_name = forms.CharField(max_length=50, required=True, label= 'نام خانوادگی  به انگلیسی' )
    persian_first_name = forms.CharField(max_length=50, required=True, label='نام به فارسی')
    persian_last_name = forms.CharField(max_length=50, required=True, label='نام خانوادگی به فارسی')
    email = forms.EmailField(max_length=70, required=True, label='ایمیل')
    account_number = forms.IntegerField(label='شماره حساب')# todo behtaresh konim vase validation
    phone_number = forms.IntegerField(label='شماره تماس')  # todo behtaresh konim vase validation
    i_agree = forms.BooleanField(label= 'با قوانین و مقررات موافقم', required=True)

    def is_valid(self):
        flag = False
        valid = super(SignUpForm, self).is_valid()
        if not valid:
            flag = True

        if  GenUser.objects.filter(username=self.cleaned_data['username']).exists():
            self.errors['username'] = 'کاربری با این مشخصات وجود دارد'
            flag = True

        if  GenUser.objects.filter(email=self.cleaned_data['email']).exists():
            self.errors['email'] = 'کاربری با این ایمیل وجود دارد'
            flag = True

        if  Customer.objects.filter(account_number=self.cleaned_data['account_number']).exists():
            self.errors['account_number'] = 'کاربری با این شماره حساب وجود دارد'
            flag = True

        if self.cleaned_data['password'] != self.cleaned_data['password2']:
            self._errors['repeat_not_match'] = 'تکرار رمز عبور با آن یکی نیست'
            flag = True
        return not flag