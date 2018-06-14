from django import forms

class Charge(forms.Form):
    charge = forms.IntegerField()

