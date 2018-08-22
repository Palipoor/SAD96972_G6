from django import forms
from apps.customer import models
from django.forms import ModelForm
from apps.customer.models import Customer
from django.utils.translation import ugettext_lazy as _


class CustomerSettingsForm(ModelForm):
    class Meta:
        model = Customer
        fields = ['first_name', 'last_name', 'persian_first_name', 'persian_last_name', 'account_number', 'phone_number', 'email']


class Charge(forms.Form):
    charge = forms.IntegerField()


class DateInput(forms.DateInput):
    input_type = 'date'


def get_form_class(type, *args, **kwargs):
    my_excludes = ["source_user"]
    my_widgets = {}
    my_labels = {}
    if type == 'tofel':
        model_class = models.TOFEL
        my_labels["source_user"] = "مبدا"
        my_labels["status"] = "وضعیت"
        my_widgets["date"] = DateInput()
        print(my_labels)

    class _ObjectForm(forms.ModelForm):
        class Meta:
            labels = my_labels
            model = model_class
            exclude = my_excludes
            widgets = my_widgets

        def __init__(self, *args, **kwargs):
            self.user = kwargs.pop('user')
            temp = super(forms.ModelForm, self).__init__(*args, **kwargs)
            self.instance.source_user = self.user
            self.transaction = None
            for field in self.fields.values():
                field.widget.attrs.update({"class": "form-control"})
            return temp

    return _ObjectForm
