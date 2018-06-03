from apps.main.models import GenUser
from django.db import models
from polymorphic.models import PolymorphicModel

# Create your models here.


class Customer(GenUser):
    rial_credit = models.FloatField(default=0)
    dollar_cent_credit = models.FloatField(default=0)
    euro_cent_credit = models.FloatField(default=0)
    account_number = models.CharField(max_length=20, unique=True, null=False)


class RequestType(models.Model):  # ???
    currency = (
           (0, 'rial'),
           (1, 'dollar'),
           (2, 'euro'),
    )
    title = models.CharField(max_length=20)
    profitRate = models.FloatField()
    currency = models.IntegerField(choices=currency)


class Request(PolymorphicModel):
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
    customer = models.ForeignKey('Customer', on_delete=models.DO_NOTHING, null=False, default=2)
    currency = models.IntegerField(choices=currency, null=False)
    amount = models.FloatField(null=False)
    request_time = models.DateTimeField(null=False)
    description = models.CharField(max_length=500)
    status = models.IntegerField(choices=statuses, null=False)
    profitRate = models.FloatField(null=False)


class LangTest(Request):
    username = models.CharField(max_length=100, null=False)
    password = models.CharField(max_length=50, null=False)


class IBT(LangTest):
    test_center_name = models.CharField(max_length=100, null=False)
    test_center_code = models.CharField(max_length=50, null=False)
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
    id_number = models.CharField(max_length=20)


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
    major_filed_code = models.CharField(max_length=50, null=False)
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


class CustomerWalletTransfer(models.Model):
    wallets = (
        (0, 'rial wallet'),
        (1, 'dollar wallet'),
        (2, 'euro wallet'),
    )
    source = models.IntegerField(choices=wallets, null=False)
    destination = models.IntegerField(choices=wallets, null=False)
    customer = models.ForeignKey('Customer', on_delete=models.DO_NOTHING, null=False)
    transfer_time = models.DateTimeField(null=False)
    profitًRate = models.FloatField(null=False)
    source_deposit_before = models.FloatField(null=False)
    source_deposit_after = models.FloatField(null=False)
    destination_deposit_before = models.FloatField(null=False)
    destination_deposit_after = models.FloatField(null=False)
    rial_deposit_before_profit = models.FloatField(null=False)
    rial_deposit_after_profit = models.FloatField(null=False)


class CustomerWalletCharge(models.Model):
    customer = models.ForeignKey('Customer', on_delete=models.DO_NOTHING, null=False)
    charge_time = models.DateTimeField(null=False)
    deposit_before = models.FloatField(null=False)
    deposit_after = models.FloatField(null=False)


class CostumerWalletChanges(models.Model):
        types = (
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
        type = models.IntegerField(choices=types, null=False)
        wallet = models.IntegerField(choices=wallets, null=False)
        request = models.ForeignKey('Request', on_delete=models.CASCADE, null=False)
        change_time = models.DateTimeField(null=False)
        deposit_before = models.FloatField(null=False)
        deposit_after = models.FloatField(null=False)
        rial_deposit_before_profit = models.FloatField(null=False)
        rial_deposit_after_profit = models.FloatField(null=False)
