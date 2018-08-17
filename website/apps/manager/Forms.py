import random
from django import forms

from apps.customer.models import Customer, Request
from apps.employee.models import Employee, EmployeeReview
from django.db.models import AutoField
from django.core.mail import send_mail
from utils.currency_utils import Transactions
from utils import fields
from apps.main.models import GenUser
import traceback


class EmployeeCreationForm(forms.ModelForm):
    class Meta:
        model = Employee
        fields = ['username', 'current_salary', 'email', 'persian_first_name',
                  'persian_last_name']  # todo add this to tests.

    def is_valid(self):
        valid = super(EmployeeCreationForm, self).is_valid()
        if not valid:
            return False

        if GenUser.objects.filter(username=self.cleaned_data['username']).exists():
            self.errors['username'] = 'کاربری با این نام کاربری وجود دارد'
            return False

        return True


class ChangeSalaryForm(forms.Form):
    username = forms.CharField(max_length=100, required=True, label='نام کاربری')
    current_salary = forms.FloatField(required=True, label='حقوق جدید')

    def is_valid(self):
        valid = super(ChangeSalaryForm, self).is_valid()
        if not valid:
            return False
        if not Employee.objects.filter(username=self.cleaned_data['username']).exists():
            self.errors['username'] = 'چنین کارمندی وجود ندارد.'
            return False

        return True


class EmployeeAccessRemovalForm(forms.Form):
    username = forms.CharField(max_length=100, required=True, label='نام کاربری')
    reason = forms.CharField(max_length=2000, label='دلیل قطع دسترسی', widget=forms.Textarea)

    def is_valid(self):
        valid = super(EmployeeAccessRemovalForm, self).is_valid()
        if not valid:
            return False
        if not Employee.objects.filter(username=self.cleaned_data['username']).exists():
            self.errors['username'] = 'چنین کارمندی وجود ندارد.'
            return False

        return True


class CustomerAccessRemovalForm(forms.Form):
    username = forms.CharField(max_length=100, required=True, label='نام کاربری')
    reason = forms.CharField(max_length=2000, label='دلیل قطع دسترسی', widget=forms.Textarea)

    def is_valid(self):
        valid = super(CustomerAccessRemovalForm, self).is_valid()
        if not valid:
            return False
        if not Customer.objects.filter(username=self.cleaned_data['username']).exists():
            self.errors['username'] = 'چنین مشتری ای وجود ندارد'
            return False

        return True


class ReviewForm(forms.Form):
    CHOICES = Transactions.request_types_for_manager_json
    action = forms.ChoiceField(choices=CHOICES, widget=forms.RadioSelect())
    transactionId = forms.IntegerField()
    description = fields.DESCRIPTION

    def __init__(self, *args, **kwargs):
        self.user = kwargs.pop('user')
        super().__init__(*args, **kwargs)

    def action_amount(self):
        super().action_amount()
        if action == 0:
            self.transaction.accept()
        elif action == 1:
            self.transaction.reject()
        elif action == 4:
            self.transaction.report()
        texts = self.transaction.exception_texts()
        if (texts):
            raise ValidationError(texts[0])

    def clean(self):
        cleaned_data = super().clean()
        transaction = None
        try:
            transaction = Request.objects.get(id=cleaned_data['transactionId'])
        except Exception as e:
            print('in bug')
            print(traceback.format_exc())
            raise forms.ValidationError('شناسه تراکنش معتبر نمی‌باشد.')
        if (transaction):
            self.review = EmployeeReview(description=cleaned_data["description"], request=transaction, employee=self.user, new_status=int(cleaned_data['action']))
            texts = self.review.exception_texts()
            if (texts):
                raise forms.ValidationError([forms.ValidationError(e) for e in texts])
        return cleaned_data

    def update_db(self):
        # updates db
        print(self.review.request.status)
        self.review.save()
