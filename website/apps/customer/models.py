from apps.main.models import GenUser
from django.db import models


# Create your models here.


class Customer(GenUser):
    rial_credit = models.IntegerField()
    dollar_cent_credit = models.IntegerField()
    euro_cent_credit = models.IntegerField()
    account_number = models.CharField(max_length=20, unique=True, null=False)


class RequestType(models.Model):  # ???
    currency = (
           (0, 'rial'),
           (1, 'dollar'),
           (2, 'euro'),
    )
    title = models.CharField()
    profit = models.IntegerField()
    currency = models.IntegerField(choices=currency)


class Request(models.Model):  # ???
    class Meta:
        abstract = True
    statuses = (
        (0, 'accepted'),
        (1, 'rejected'),
        (2, 'pending'),
        (3, 'failed'),
        # (3, 'reported'),   request can be failed and reported in the same time
    )
    currency = (
        (0, 'rial'),
        (1, 'dollar'),
        (2, 'euro'),
    )
    customer = models.ForeignKey('Customer', on_delete=models.DO_NOTHING)
    currency = models.IntegerField(choices=currency, null=False)
    amount = models.IntegerField(null=False)
    request_time = models.DateTimeField(null=False)
    description = models.CharField(max_length=500)
    status = models.IntegerField(choices=statuses, default='pending')
    profit = models.IntegerField()
    # destination = models.IntegerField()
    # this is in riaaal, always? always


class TestTrans(Request):
        class Meta:
            abstract = True

        username = models.CharField(max_length=100, null=False)
        password = models.CharField(max_length=50, null=False)


class IBT(TestTrans):
    class Meta:
        abstract = True
    test_center_name = models.CharField(max_length=100, null=False)
    test_center_code = models.IntegerField(max_length=50, null=False)
    city = models.CharField(max_length=100, null=False)
    country = models.CharField(max_length=100, null=False)
    date = models.DateTimeField(null=False)


class TOEFL(IBT):
    id_types = (
        (0, 'Passport'),
        (1, 'National ID'),

    )
    reasons = (
        (0, 'blah'),
        (1, 'blah'),
        (2, 'blah'),
        (3, 'blah'),
        (4, 'blah'),
        (5, 'blah'),
        (6, 'blah'),
        (7, 'blah'),
        (8, 'blah'),
        (9, 'blah'),

    )
    countries_for_studying = (
        (0, 'blah'),
        (1, 'blah'),
        (2, 'blah'),
        (3, 'blah'),
        (4, 'blah'),
        (5, 'blah'),
        (6, 'blah'),
        (7, 'blah'),
        (8, 'blah'),
        (9, 'blah'),
    )
    reason = models.IntegerField(choices=reasons, null=False)  # handle several reasons
    country_for_study = models.IntegerField(choices=countries_for_studying, null=False)  # handle several country
    id_type = models.IntegerField(choices=id_types, null=False)
    id = models.CharField(max_length=20)


class GRE(IBT):
    citizenships = (
        (0, 'blah'),
        (1, 'blah'),
        (2, 'blah'),
    )
    statuses = (
        (0, 'blah'),
        (1, 'blah'),
        (2, 'blah'),
        (3, 'blah'),
        (4, 'blah'),
        (5, 'blah'),
        (6, 'blah'),
        (7, 'blah'),

    )
    major_filed_code = models.IntegerField(null=False)
    major_filed_name = models.CharField(max_length=50, null=False)
    citizenship = models.IntegerField(choices=citizenships, null=False)
    educational_status = models.IntegerField(choices=statuses, null=False)


class UniversityTrans(Request):
        types = (
            (0, 'application fee'),
            (1, 'deposit fee')
        )
        type = models.IntegerField(choices=types, null=False)
        university_name = models.CharField(max_length=50, null=False)
        link = models.URLField(null=False)
        username = models.CharField(max_length=100, null=False)
        password = models.CharField(max_length=50, null=False)
        guide = models.CharField(max_length=1000, null=False)


class ForeignTrans(Request):
    account_number = models.CharField(max_length=20,  null=False)
    bank_name = models.CharField(max_length=50,  null=False)


class InternalTrans(Request):
    account_number = models.CharField(max_length=20)
    bank_name = models.CharField(max_length=50)


class UnknownTrans(Request):
    account_number = models.CharField(max_length=20, null=False)
    bank_name = models.CharField(max_length=50, null=False)
    email = models.EmailField()
    phone_number = models.CharField(max_length=20)


class CustomerWalletChanges(models.Model):
    types = (
        (0, 'wallet charge'),
        (1, 'request submit'),
        (2, 'request profit'),
        (3, 'request failure'),  # rejected or failed
        (4, 'request failure profit'),
    )
    wallets = (
        (0, 'rial'),
        (1, 'dollar'),
        (2, 'euro'),
    )
    type = models.IntegerField(choices=types)
    wallet = models.IntegerField(choices=wallets)
    customer = models.ForeignKey('Customer', on_delete=models.DO_NOTHING)
    request = models.ForeignKey('Request', on_delete=models.CASCADE, null=True)
    # if type is submitted or failed request, or profit
    change_time = models.DateTimeField(null=False)
    deposit_before = models.IntegerField()
    deposit_after = models.IntegerField()
