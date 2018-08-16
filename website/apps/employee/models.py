from django.db import models
from apps.main import models as main_models
from django.contrib.auth.models import Group


# Create your models here.


class Employee(main_models.GenUser):
    current_salary = models.FloatField()

    def __init__(self, *args, **kwargs):
        super(Employee, self).__init__(*args, **kwargs)
        self.user_type = 1

    def save(self, *args, **kwargs):
        super(Employee, self).save(*args, **kwargs)
        customer_group = Group.objects.get(name='employee')
        customer_group.user_set.add(self)
        customer_group = Group.objects.get(name='staff')
        customer_group.user_set.add(self)


# class Report(models.Model):
#     transaction = models.ForeignKey("customer.Request", on_delete=models.CASCADE, null=False)
#     employee = models.ForeignKey("Employee", on_delete=models.DO_NOTHING, null=False)
#     description = models.TextField(max_length=300)


class EmployeeReview(models.Model):
    # every change in status that employee makes
    statuses = (
        (0, 'accepted'),
        (1, 'rejected'),
        (2, 'pending'),
        (3, 'failed'),
        (4, 'reported'),
    )
    description = models.TextField(max_length=300, default='')
    request = models.ForeignKey("customer.Request", on_delete=models.CASCADE, null=False)
    employee = models.ForeignKey("Employee", on_delete=models.DO_NOTHING, null=False)
    new_status = models.IntegerField(choices=statuses, null=False, default=0)

    def __init__(self, *args, **kwargs):
        temp = super().__init__(*args, **kwargs)
        if self.new_status == 0:
            self.request.accept()
        if self.new_status == 1:
            self.request.reject()
        if self.new_status == 3:
            self.request.fail()
        if self.new_status == 4:
            self.request.report()
        return temp

    def save(self, *args, **kwargs):
        # TODO cover all scenarios
        self.request.save()
        super(EmployeeReview, self).save(*args, **kwargs)

    def exception_texts(self):
        return self.request.exception_texts()


class Salary(models.Model):
    employee = models.ForeignKey("main.GenUser", on_delete=models.DO_NOTHING)
    salary = models.IntegerField()
