from django import forms
from django.forms import ModelForm
from utils import fields
from apps.employee.models import Employee, EmployeeReview
from apps.customer.models import Request
from utils.currency_utils import Transactions

from django.forms import widgets


class ReviewForm(forms.Form):
    CHOICES = Transactions.request_types_for_review_json
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
        except:
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


class EmployeeSettingsForm(ModelForm):
    class Meta:
        model = Employee
        fields = ['username', 'first_name', 'last_name', 'persian_first_name', 'persian_last_name']
