from django.db import models
from apps.customer import models as customer_models


# Create your models here.


class Employee(customer_models.Request):
    salary = models.IntegerField()


class Report(models.Model):
    transaction = models.ForeignKey("Request", on_delete=models.CASCADE)
    employee = models.ForeignKey("Employee", on_delete=models.DO_NOTHING)
    description = models.TextField(max_length=300)


class EmployeeReview(models.Model):
    statuses = (
        (0, 'accept'),
        (1, 'reject'),
    )
    transaction = models.ForeignKey("Request", on_delete=models.CASCADE)
    employee = models.ForeignKey("Employee", on_delete=models.DO_NOTHING)


class Salary(models.Model):
    employee = models.ForeignKey("User", on_delete=models.DO_NOTHING)
    salary = models.IntegerField()
