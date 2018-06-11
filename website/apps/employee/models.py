from django.db import models
from apps.main import models as main_models


# Create your models here.


class Employee(main_models.GenUser):
    current_salary = models.FloatField()

    def __init__(self, *args, **kwargs):
        super(Employee, self).__init__( *args, **kwargs)
        self.user_type = 1



class Report(models.Model):
    transaction = models.ForeignKey("customer.Request", on_delete=models.CASCADE, null=False)
    employee = models.ForeignKey("Employee", on_delete=models.DO_NOTHING, null=False)
    description = models.TextField(max_length=300)


class EmployeeReview(models.Model):
    statuses = (
        (0, 'accept'),
        (1, 'reject'),
    )
    request = models.ForeignKey("customer.Request", on_delete=models.CASCADE, null=False)
    employee = models.ForeignKey("Employee", on_delete=models.DO_NOTHING, null=False)


class Salary(models.Model):
    employee = models.ForeignKey("main.GenUser", on_delete=models.DO_NOTHING)
    salary = models.IntegerField()
