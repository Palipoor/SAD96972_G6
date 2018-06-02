from django.db import models
from apps import main

# Create your models here.
class Employee(main.User):
    salary = models.IntegerField()

class Report(models.Model):
    transaction = models.ForeignKey("apps.customer.models.Request", on_delete=models.DO_NOTHING)
    employee = models.ForeignKey("Employee", on_delete=models.DO_NOTHING)
    description = models.TextField(max_length=300)

class Employee_review(models.Model):
    statuses = (
        (0, 'accept'),
        (1, 'reject'),
        (2, 'report'),
    )
    employee = models.ForeignKey("Employee", on_delete=models.DO_NOTHING)
    transaction = models.ForeignKey("apps.customer.models.Request", on_delete=models.DO_NOTHING)

class Salary(models.Model):
    employee = models.ForeignKey("User", on_delete=models.DO_NOTHING)
    salary = models.IntegerField()
