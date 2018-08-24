from django import forms
from django.forms import ModelForm
from utils import fields
from apps.employee.models import Employee, EmployeeReview
from apps.customer.models import Request
from utils.currency_utils import Transactions

from django.forms import widgets


class ReviewForm(forms.Form):
    CHOICES = Transactions.request_types_for_review_json
    action = forms.ChoiceField(choices=CHOICES, widget=forms.RadioSelect(attrs = {"class": "form-check-input"}))
    transactionId = forms.IntegerField(label = "شناسه تراکنش", widget = forms.NumberInput(attrs = {'class': 'form-control'}))
    description = fields.DESCRIPTION

    def __init__(self, *args, **kwargs):
        self.user = kwargs.pop('user')
        if 'transaction_id' in kwargs:
            id = kwargs.pop('transaction_id')
        else:
            id = ""
        super().__init__(*args, **kwargs)
        if id != "":
            print(self.fields.keys( ))
            self.fields['transactionId'].widget = forms.HiddenInput(attrs={'value':id})


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
        except:
            raise forms.ValidationError('شناسه تراکنش معتبر نیست.')
        if (transaction):
            self.review = EmployeeReview(description=cleaned_data["description"], request=transaction, employee=self.user, new_status=int(cleaned_data['action']))
            texts = self.review.exception_texts()
            if (texts):
                raise forms.ValidationError([forms.ValidationError(e) for e in texts])
        return cleaned_data

    def update_db(self):
        self.review.save()


class EmployeeSettingsForm(ModelForm):
    class Meta:
        model = Employee
        fields = ['username', 'first_name', 'last_name', 'persian_first_name', 'persian_last_name']
    username = fields.USERNAME
    first_name = fields.FIRST_NAME
    last_name = fields.LAST_NAME
    persian_first_name = fields.PERSIAN_FIRST_NAME
    persian_last_name = fields.PERSIAN_LAST_NAME