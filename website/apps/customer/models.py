from django.db import models
from apps.main import models
# Create your models here.


class Customer(models.User):
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


class TransactionType(models.Model): #???
    currency = (
           (0, 'rial'),
           (1, 'dollar'),
           (2, 'euro'),
    )
    title = models.IntegerField()
    profit = models.IntegerField()
    currency = models.IntegerField(choices=currency)


class TestTrans(Transaction):
        class Meta:
            abstract = True

        username = models.CharField(max_length=100)
        password = models.CharField(max_length=50)


class IBT(TestTrans):
    class Meta:
        abstract = True

    test_center_name = models.CharField(max_length=100)
    test_center_code = models.IntegerField(max_length=50)
    city = models.CharField(max_length=100)
    country = models.CharField(max_length=100)
    date = models.DateTimeField()


class TOEFL(IBT):
    reasons = (
        (0, 'blah'),
        (1, 'blah'),
        (2, 'blah'),
    )
    
    reason = models.IntegerField(choices=reasons)


