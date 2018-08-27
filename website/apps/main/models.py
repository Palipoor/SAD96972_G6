from django.db import models
from django.contrib.auth.models import User, Group
from polymorphic.models import PolymorphicModel
from django.core.validators import MinValueValidator
from utils import strings
from django.apps import apps
from utils.notification_tools import send_critical_credit_notification
from django.db.models import Sum


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
    minimum_rial_credit = models.IntegerField(null=True, blank=True)

    def exception_texts(self):
        exceptions = []
        if (self.rial_credit < 0):
            exceptions += [strings.NOT_ENOUGH_RIAL]
        if (self.dollar_cent_credit < 0):
            exceptions += [strings.NOT_ENOUGH_DOLLAR]
        if (self.euro_cent_credit < 0):
            exceptions += [strings.NOT_ENOUGH_EURO]
        return exceptions

    def save(self, *args, **kwargs):
        print("in save")
        if self.minimum_rial_credit == None:
            print("in none")
            print(apps.get_model('employee', "Employee"))
            print(apps.get_model('employee', "Employee").objects)
            print(apps.get_model('employee', "Employee").objects.aggregate(Sum('current_salary')))
            print(self.rial_credit)
            if apps.get_model('employee', "Employee").objects.aggregate(Sum('current_salary'))["current_salary__sum"] > self.rial_credit:
                send_critical_credit_notification(self.username)
        else:
            if self.minimum_rial_credit < self.rial_credit:
                send_critical_credit_notification(self.username)

        super(Wallet_User, self).save(*args, **kwargs)  # Call the real save() method


class Notification(PolymorphicModel):

    sent_date = models.DateTimeField(auto_now_add=True)
    seen = models.BooleanField(default=False)
    user = models.ForeignKey('GenUser', on_delete=models.CASCADE)
    text = models.CharField(max_length=500)

    @staticmethod
    def create(username, message):
        user = GenUser.objects.get(username=username)
        temp = Notification(user=user, text=message)
        return temp


class Critical_Credit_Notification(Notification):

    def __init__(self, *args, **kwargs):
        super(Critical_Credit_Notification, self).__init__(*args, **kwargs)
        self.text = "موجودی کیف پول ریالی از مقدار تعیین شده کمتر است."

    @staticmethod
    def get_or_create(username):
        print(username)
        user = GenUser.objects.get(username=username)
        temp = Critical_Credit_Notification.objects.get_or_create(seen=False, user=user)[0]
        return temp


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
