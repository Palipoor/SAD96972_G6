from django import forms
from utils import fields
from apps.customer import models
from django.forms import ModelForm
from apps.customer.models import Customer
from django.utils.translation import ugettext_lazy as _
from django.forms.models import model_to_dict
from utils.currency_utils import Transactions


class Charge(forms.Form):
    charge = forms.IntegerField()


class DateInput(forms.DateInput):
    input_type = 'date'


class CustomerSettingsForm(ModelForm):
    class Meta:
        model = Customer
        fields = ['first_name', 'last_name', 'persian_first_name', 'persian_last_name', 'account_number', 'phone_number', 'email', 'contact_way']

    first_name = fields.FIRST_NAME
    last_name = fields.LAST_NAME
    persian_first_name = fields.PERSIAN_FIRST_NAME
    persian_last_name = fields.PERSIAN_LAST_NAME
    account_number = fields.ACCOUNT_NUMBER
    phone_number = fields.PHONE
    phone_number.required = True
    email = fields.EMAIL
    email.required = True


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
        my_sub_title += "ثبت نام جی‌ار‌ای"
    elif type == 'universitytrans':
        model_class = models.UniversityTrans
        my_labels.update(model_class.labels)
        for field in my_labels:
            my_fields.insert(0, field)
        # my_widgets["date"] = DateInput()
        my_widgets["password"] = forms.PasswordInput
        my_sub_title += "فرم دانشگاه"
    elif type == 'banktrans':
        model_class = models.BankTrans
        my_labels.update(model_class.labels)
        for field in my_labels:
            my_fields.insert(0, field)
        # my_widgets["date"] = DateInput()
        # my_widgets["password"] = forms.PasswordInput
        my_sub_title += "پرداخت به حساب بانکی"
    elif type == 'unkowntrans':
        model_class = models.UnknownTrans
        my_labels.update(model_class.labels)
        for field in my_labels:
            my_fields.insert(0, field)
        # my_widgets["date"] = DateInput()
        # my_widgets["password"] = forms.PasswordInput
        my_sub_title += "پرداخت ناشناس"
    else:
        model_class = models.CustomTransactionInstance
        try:
            type_class = models.CustomTransactionType.objects.get(type=type)
        except Exception as e:
            pass
        for field in type_class._meta.get_fields():
            if (field.name.endswith("_exists")):
                if (getattr(type_class, field.name)):
                    my_labels[field.name[:-7]] = getattr(type_class, field.name[:-7] + "_label")
                    my_fields.insert(0, field.name[:-7])
        print(my_labels)
        # print("TYPE : " + type)
        # details = model_to_dict(models.CustomTransactionType.objects.get(type=type))
        # for name, value in details.items():
        #     # print(str(name) + str(value))
        #     if name.endswith("exists") and not value:
        #         my_excludes.append(name[:-7])
        #         # print(name[:-6])

        #     if name.endswith("label") and value:
        #         my_labels[name[:-6]] = value

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
            temp = super(_ObjectForm, self).__init__(*args, **kwargs)
            self.instance.creator = self.user
            self.instance.type = type
            self.instance.set_initials()
            self.transaction = None
            self.has_amount = "amount" in self.Meta.labels
            print("has amount " + str(self.has_amount))
            self.currency_sign = Transactions.get_currency_symbol(self.instance.source_wallet)
            self.profitRate = self.instance.profitRate
            self.pre_amount = self.instance.amount
            if (self.pre_amount == None):
                self.pre_amount = 0
            self.pre_profit = self.pre_amount*self.profitRate
            self.pre_payable = self.pre_amount+self.pre_profit
            # print(self.M)
            for field in self.fields.values():
                field.widget.attrs.update({"class": "form-control"})
                field.error_messages.update({'required': 'پر کردن این فیلد الزامی است.'})
            return temp

    return _ObjectForm
