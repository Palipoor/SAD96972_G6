import random
from django import forms
from utils import fields
from utils.strings import *
from apps.customer.models import Customer
from apps.employee.models import Employee

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
                  'persian_last_name', 'first_name', 'last_name']  # todo add this to tests.

    username = fields.USERNAME
    first_name = fields.FIRST_NAME
    last_name = fields.LAST_NAME
    persian_first_name = fields.PERSIAN_FIRST_NAME
    persian_last_name = fields.PERSIAN_LAST_NAME
    email = fields.EMAIL
    current_salary = forms.IntegerField(required=True,  error_messages={'required': FIELD_REQUIRED} , widget = forms.NumberInput(attrs = {'class':'form-control'}))

    def clean_username(self):
        if GenUser.objects.filter(username=self.cleaned_data['username']).exists():
            raise forms.ValidationError(REPEATED_USER)
        return self.cleaned_data['username']

    def clean_email(self):
        if GenUser.objects.filter(email=self.cleaned_data['email']).exists():
            raise forms.ValidationError(REPEATED_USER)
        return self.cleaned_data['email']


class ChangeSalaryForm(forms.Form):
    username = fields.USERNAME
    current_salary = forms.IntegerField(required=True,  error_messages={'required': FIELD_REQUIRED} , widget = forms.NumberInput(attrs = {'class':'form-control'}))

    def clean_username(self):
        if not Employee.objects.filter(username=self.cleaned_data['username']).exists():
            raise forms.ValidationError("چنین کارمندی وجود ندارد.")
        return self.cleaned_data['username']
    
class EmployeeAccessRemovalForm(forms.Form):
    username = fields.USERNAME
    reason = forms.CharField(max_length=2000, label='دلیل قطع دسترسی', widget=forms.Textarea(attrs = {'class': 'form-control', 'placeholder': 'دلیل قطع دسترسی...'}))

    def clean_username(self):
        if not Employee.objects.filter(username=self.cleaned_data['username']).exists():
            raise forms.ValidationError("چنین کارمندی وجود ندارد.")
        return self.cleaned_data['username']
    



class CustomerAccessRemovalForm(forms.Form):
    username = fields.USERNAME
    reason = forms.CharField(max_length=2000, label='دلیل قطع دسترسی', widget=forms.Textarea(attrs = {'class': 'form-control', 'placeholder': 'دلیل قطع دسترسی...'}))

    def clean_username(self):
        if not Customer.objects.filter(username=self.cleaned_data['username']).exists():
            raise forms.ValidationError("چنین مشتری‌ای وجود ندارد.")
        return self.cleaned_data['username']
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
