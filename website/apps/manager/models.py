from django.contrib.auth.models import User
from django.db import models
from apps.main import models as main_models
from django.contrib.auth.models import Group

# Create your models here.


class Manager(main_models.Wallet_User):

    def __init__(self, *args, **kwargs):
        super(Manager, self).__init__(*args, **kwargs)
        self.user_type = 2

    def save(self, *args, **kwargs):
        super(Manager, self).save(*args, **kwargs)
        customer_group = Group.objects.get(name='manager')
        customer_group.user_set.add(self)
        customer_group = Group.objects.get(name='wallet_user')
        customer_group.user_set.add(self)
        customer_group = Group.objects.get(name='staff')
        customer_group.user_set.add(self)

    @staticmethod
    def get_manager():
        return Manager.objects.all()[0]
