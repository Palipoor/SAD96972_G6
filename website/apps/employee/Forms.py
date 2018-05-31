from django import forms


class FaultReportForm(forms.Form):
    transactionID = forms.IntegerField()
    reason = forms.CharField(widget=forms.Textarea)
