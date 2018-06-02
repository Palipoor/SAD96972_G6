from django.db import models
from apps.main import models as main_models
# Create your models here.


class Manager(main_models.GenUser):
    company_rial_credit = models.IntegerField()
    company_dollar_cent_credit = models.IntegerField()
    company_euro_cent_credit = models.IntegerField()
    company_account_number = models.CharField(max_length=20, null=False)


'''class User_Status(models.Model): #???
    manager = models.ForeignKey("Manager", on_delete=models.DO_NOTHING) 
    user = models.ForeignKey("User", on_delete=models.DO_NOTHING)
    status = models.BooleanField()'''


class ManagerWalletChanges(models.Model):
    types = (
        (0, 'wallet charge'),
        (1, 'request submit'),
        (2, 'request profit'),
        (3, 'request failure'),  # rejected or failed
        (4, 'request failure profit'),
        (5, 'request accept')
    )
    wallet = (
        (0, 'rial'),
        (1, 'dollar'),
        (2, 'euro'),
    )
    type = models.IntegerField(choices=types)
    request = models.ForeignKey('Request', on_delete=models.CASCADE, null=True)  # if type is submitted failed or accepted request\profit
    change_time = models.DateTimeField(null=False)
    deposit_before = models.IntegerField()
    deposit_after = models.IntegerField()