from django.db import models
from apps import main
from apps import employee
# Create your models here.


class Manager(employee.Employee):
    a = 3


class Company(models.Model):
    english_name = models.CharField(max_length=100)
    persian_name = models.CharField(max_length=100)
    account = models.IntegerField()
    photo = models.ImageField()
    rial_credit = models.IntegerField()
    dollar_cent_credit = models.IntegerField()
    euro_cent_credit = models.IntegerField()


class User_Status(models.Model): #???
    manager = models.ForeignKey("Manager", on_delete=models.DO_NOTHING) 
    user = models.ForeignKey("User", on_delete=models.DO_NOTHING)
    status = models.BooleanField()


