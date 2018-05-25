from django.db import models
from apps import main
# Create your models here.


class Customer(main.User):
    rial_credit = models.IntegerField()
    dollar_cent_credit = models.IntegerField()
    euro_cent_credit = models.IntegerField()
    account_number = models.IntegerField(unique=True, null=False)


class Transaction(models.Model): #???
    class Meta:
        abstract = True

    '''statuses = (
        (0, 'accepted'),
        (1, 'rejected'),
        (2, 'pending'),
    )'''
    currency = (
        (0, 'rial'),
        (1, 'dollar'),
        (2, 'euro'),
    )
    customer = models.ForeignKey('Customer', on_delete=models.DO_NOTHING)
    currency = models.IntegerField(choices=currency)
    amount = models.IntegerField()
    request_time = models.DateTimeField()
    description = models.CharField(max_length=500)
    #status = models.IntegerField(choices=statuses)
    #destination = models.IntegerField()
    #profit = models.IntegerField()
    #this is in riaaal, always? always


class Transaction_Type(models.Model): #???
    currency = (
           (0, 'rial'),
           (1, 'dollar'),
           (2, 'euro'),
    )
    title = models.IntegerField()
    profit = models.IntegerField()
    currency = models.IntegerField(choices=currency)


