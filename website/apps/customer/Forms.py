from django import forms
from apps.customer import models
from django.forms import ModelForm
from apps.customer.models import Customer
from django.utils.translation import ugettext_lazy as _
from django.forms.models import model_to_dict


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
    my_fields = []
    my_labels = {}
    my_title = "درخواست تراکنش"
    my_sub_title = ""
    print("type in form<"+type+"<")
    if type == 'tofel':
        model_class = models.TOFEL
        my_labels.update(model_class.labels)
        for field in my_labels:
            my_fields.insert(0, field)
        my_widgets["date"] = DateInput()
        my_widgets["password"] = forms.PasswordInput
        my_sub_title += "ثبت نام تافل"
    elif type == 'gre':
        model_class = models.GRE
        my_labels.update(model_class.labels)
        for field in my_labels:
            my_fields.insert(0, field)
        my_widgets["date"] = DateInput()
        my_widgets["password"] = forms.PasswordInput
        my_sub_title += "ثبت نام تافل"
    else:
        model_class = models.CustomTransactionInstance
        # TODO return nothing if type is wrong
        print("TYPE : " + type)
        details = model_to_dict(models.CustomTransactionType.objects.get(type=type))
        for name, value in details.items():
            # print(str(name) + str(value))
            if name.endswith("exists") and not value:
                my_excludes.append(name[:-7])
                # print(name[:-6])

            if name.endswith("label") and value:
                my_labels[name[:-6]] = value

    class _ObjectForm(forms.ModelForm):
        class Meta:
            labels = my_labels
            model = model_class
            exclude = my_excludes
            widgets = my_widgets
            fields = my_fields

        def __init__(self, *args, **kwargs):
            self.user = kwargs.pop('user')
            self.title = my_title
            self.sub_title = my_sub_title
            temp = super(forms.ModelForm, self).__init__(*args, **kwargs)
            self.instance.source_user = self.user
            self.transaction = None
            for field in self.fields.values():
                field.widget.attrs.update({"class": "form-control"})
            return temp

    return _ObjectForm
