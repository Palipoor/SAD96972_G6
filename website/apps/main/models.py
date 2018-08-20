from django.db import models
from django.contrib.auth.models import User, Group
from polymorphic.models import PolymorphicModel
from django.core.validators import MinValueValidator
from utils import strings


# Create your models here.
class GenUser(User):
    types = (
        (0, 'Customer'),
        (1, 'Employee'),
        (2, 'Manager'),
    )
    persian_first_name = models.CharField(max_length=50, blank=True)
    persian_last_name = models.CharField(max_length=50, blank=True)
    phone_number = models.CharField(max_length=20, blank=True)
    user_type = models.IntegerField(choices=types, default=0, blank=True)

    def exception_texts(self):
        return []

class Wallet_User(GenUser):
    rial_credit = models.FloatField(default=0)
    dollar_cent_credit = models.FloatField(default=0)
    euro_cent_credit = models.FloatField(default=0)
    account_number = models.CharField(max_length=20, unique=True, null=False)

    def exception_texts(self):
        exceptions = []
        if (self.rial_credit < 0):
            exceptions += [strings.NOT_ENOUGH_RIAL]
        if (self.dollar_cent_credit < 0):
            exceptions += [strings.NOT_ENOUGH_DOLLAR]
        if (self.euro_cent_credit < 0):
            exceptions += [strings.NOT_ENOUGH_EURO]
        return exceptions


class Notification(models.Model):

    sent_date = models.DateTimeField(auto_now_add=True)
    seen = models.BooleanField(default = False)
    user = models.ForeignKey('GenUser', on_delete=models.CASCADE)
    text = models.CharField(max_length=500)


class WalletChange(models.Model):
    currency_types = (
        (0, 'rial'),
        (1, 'dollar'),
        (2, 'euro'),
    )
    value = models.FloatField(null=False)
    wallet = models.IntegerField(choices=currency_types)
    user = models.ForeignKey('GenUser', on_delete=models.DO_NOTHING, null=False)
    time = models.DateTimeField()
    is_profit = models.BooleanField(default=False)
