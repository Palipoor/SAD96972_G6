from django import forms
from django.contrib.auth.models import User

from apps.manager.models import Customer, WebsiteUser


class SignUpForm(forms.Form):
    username = forms.CharField(max_length=100, required=True, label='نام کاربری', widget=forms.widgets.TextInput(attrs={'name':'username'}))
    password = forms.CharField(max_length=50, required=True, label='کلمه عبور')
    password2 = forms.CharField(max_length=50, required=True, label='تکرار کلمه عبور')
    persian_first_name = forms.CharField(max_length=50, required=True, label='نام(به فارسی)')
    persian_last_name = forms.CharField(max_length=50, required=True, label='نام خانوادگی (به فارسی)')
    email = forms.EmailField(max_length=70, required=True, label='ایمیل')
    account = forms.IntegerField(label='شماره حساب')# todo behtaresh konim vase validation
    phone = forms.IntegerField(label='شماره تماس')  # todo behtaresh konim vase validation

    def is_valid(self):

        valid = super(SignUpForm, self).is_valid()
        if not valid:
            return valid

        try:
            user = User.objects.get(
                    WebsiteUser(username=self.cleaned_data['username']))
        except User.DoesNotExist:
            if self.cleaned_data['password'] != self.cleaned_data['password2']:
                self._errors['repeat_not_match'] = 'تکرار رمز عبور با آن یکی نیست'
                return False
            else:
                return True

        self.errors['already_signed_up'] = 'کاربری با این مشخصات وجود دارد'
        return False