from apps.main.models import GenUser
from django.db import models
from django.apps import apps
from django.contrib.auth.models import Group
from utils.currency_utils import Transactions
from utils.notification_tools import notify, send_notification


# Create your models here.


class Employee(GenUser):
    current_salary = models.IntegerField()

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
    employee = models.ForeignKey("main.GenUser", on_delete=models.DO_NOTHING, null=False)
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
        self.request.save()

        message = 'وضعیت تراکنش شما به شماره {0} به {1} تغییر یافت.'.format(self.request.id, Transactions.get_persian_status(self.request.status) )
        send_notification(self.request.creator.username, message)
        notify(self.request.creator.username,message, 'تغییر وضعیت تراکنش')

        if self.new_status == 4:
            message = "کارمند با نام کاربری {0} تراکنش {1} را با این توضیح گزارش کرد: {2}".format(self.employee.username, self.request.id, self.description)
            manager = apps.get_model('manager', 'Manager').objects.first().username
            send_notification(manager, message)

        super(EmployeeReview, self).save(*args, **kwargs)
    def exception_texts(self):
        return self.request.exception_texts()

    def clean(self):
        temp = super().clean()
        self.request.full_clean()
        return temp


class Salary(models.Model):
    employee = models.ForeignKey("main.GenUser", on_delete=models.DO_NOTHING)
    salary = models.IntegerField()
