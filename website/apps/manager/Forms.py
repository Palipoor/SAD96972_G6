import random
from django import forms
from apps.manager.models import Employee
from django.db.models import AutoField
from django.core.mail import send_mail


def copy_model_instance(obj):
    # copy kardam in code ro!
    initial = dict([(f.name, getattr(obj, f.name)) for f in obj._meta.fields if
                    not isinstance(f, AutoField) and not f in obj._meta.parents.values()])
    return obj.__class__(**initial)


class EmployeeAccessRemovalForm(forms.Form):
    username = forms.CharField(max_length=100, label='نام کاربری')
    reason = forms.CharField(max_length=2000, label='دلیل قطع دسترسی', widget=forms.Textarea)

    # todo complete


class EmployeeCreationForm(forms.ModelForm):
    model = Employee
    fields = ['username', 'salary', 'email']  # todo add this to tests.

    def save(self, commit=True, *args, **kwargs):
        m = super(EmployeeCreationForm, self).save(commit=False)
        m_new = copy_model_instance(m)
        password = "someRandomPassword"  # todo generate randomly
        m_new.password = password
        results = []
        if commit:
            m_new.save()
            message = 'رمز شما عبارت است از:' + password
            send_mail(
                    'کارمندی در سپاا',
                    message,
                    'info@sapaa.com',
                    [m_new.email],
                    fail_silently=True,
            )
        results.append(m_new)
        return results
