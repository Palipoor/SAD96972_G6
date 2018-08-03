from django.contrib.auth.models import User
from django.db import models
from apps.main import models as main_models
from django.contrib.auth.models import Group

# Create your models here.

class Manager(main_models.GenUser):
    company_rial_credit = models.FloatField()
    company_dollar_cent_credit = models.FloatField()
    company_euro_cent_credit = models.FloatField()
    company_account_number = models.CharField(max_length=20, null=False)

    def __init__(self, *args, **kwargs):
        super(Manager, self).__init__( *args, **kwargs)
        self.user_type = 2

    def save(self, *args, **kwargs):
        super(Manager,self).save(*args, **kwargs)
        customer_group = Group.objects.get(name='manager')
        customer_group.user_set.add(self)
        customer_group = Group.objects.get(name='wallet_user')
        customer_group.user_set.add(self)
        customer_group = Group.objects.get(name='staff')
        customer_group.user_set.add(self)


'''class User_Status(models.Model): #???
    manager = models.ForeignKey("Manager", on_delete=models.DO_NOTHING) 
    user = models.ForeignKey("User", on_delete=models.DO_NOTHING)
    status = models.BooleanField()'''
#
#
# class CompanyWalletTransfer(models.Model):
#     wallets = (
#         (0, 'rial wallet'),
#         (1, 'dollar wallet'),
#         (2, 'euro wallet'),
#     )
#     source = models.IntegerField(choices=wallets, null=False)
#     destination = models.IntegerField(choices=wallets, null=False)
#     transfer_time = models.DateTimeField(null=False)
#     source_deposit_before = models.FloatField(null=False)
#     source_deposit_after = models.FloatField(null=False)
#     destination_deposit_before = models.FloatField(null=False)
#     destination_deposit_after = models.FloatField(null=False)
#
#
# class CompanyWalletCharge(models.Model):
#     wallets = (
#         (0, 'rial wallet'),
#         (1, 'dollar wallet'),
#         (2, 'euro wallet'),
#     )
#     destination = models.IntegerField(choices=wallets, null=False)
#     charge_time = models.DateTimeField(null=False)
#     deposit_before = models.FloatField()
#     deposit_after = models.FloatField()
#
#
# class CompanyWalletChanges(models.Model):
#     types = (
#         (1, 'request submit'),
#         (2, 'request profit'),
#         (3, 'request failure'),  # rejected or failed
#         (4, 'request failure profit'),
#         (5, 'request accept'),
#     )
#     wallets = (
#         (0, 'rial'),
#         (1, 'dollar'),
#         (2, 'euro'),
#     )
#     type = models.IntegerField(choices=types)
#     wallet = models.IntegerField(choices=wallets)
#     request = models.ForeignKey('customer.Request', on_delete=models.CASCADE, null=False)
#     change_time = models.DateTimeField(null=False)
#     deposit_before = models.FloatField(null=False)
#     deposit_after = models.FloatField(null=False)
