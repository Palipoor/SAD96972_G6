from django.db import models

# Create your models here.

class User(models.Model):
    types = (
        (0, 'customer'),
        (1, 'employee'),
        (2, 'manager'),
    )
    username = models.CharField(max_length=100, primary_key=True)
    password = models.CharField(max_length=50)
    photo = models.ImageField()
    english_first_name = models.CharField(max_length=50)
    english_last_name = models.CharField(max_length=50)
    persian_first_name = models.CharField(max_length=50)
    persian_last_name = models.CharField(max_length=50)
    user_type = models.IntegerField(choices=types)
    email = models.EmailField(max_length=70, unique=True, null = False)
    active = models.BooleanField()

class Employee(User):
    salary = models.IntegerField()


class Customer(User):
    rial_credit = models.IntegerField()
    dollar_cent_credit = models.IntegerField()
    euro_cent_credit = models.IntegerField()
    account = models.IntegerField()

class Manager(Employee):
    a = 3


class Transaction(models.Model):
    statuses = (
        (0, 'accepted'),
        (1, 'rejected'),
        (2, 'pending'),
        (3, 'reported'),
    )
    currency = (
        (0, 'rial'),
        (1, 'dollar'),
        (2, 'euro'),
    )
    customer = models.ForeignKey('Customer', on_delete=models.DO_NOTHING)
    status = models.IntegerField(choices=statuses)
    currency = models.IntegerField(choices=currency)
    destination = models.IntegerField()
    amount = models.IntegerField()
    profit = models.IntegerField()
    #this is in riaaal, always? always


class notication(models.Model):
    statuses = (
        (0, 'seen'),
        (1, 'unseen'),
    )
    types = (
        (0, 'message'),
        (1, 'blah'),
        (2, 'blah  blah'),
    )
    # todo handle correct types
    user = models.ForeignKey('User', on_delete=models.CASCADE)
    text = models.IntegerField(max_length=500)



class Company(models.Model):
    english_name = models.CharField(max_length=100)
    persian_name = models.CharField(max_length=100)
    account = models.IntegerField()
    photo = models.ImageField()
    rial_credit = models.IntegerField()
    dollar_cent_credit = models.IntegerField()
    euro_cent_credit = models.IntegerField()

class Employee_review(models.Model):
    statuses = (
        (0, 'accept'),
        (1, 'reject'),
        (2, 'report'),
    )
    employee = models.ForeignKey("Employee", on_delete=models.DO_NOTHING)
    transaction = models.ForeignKey("Transaction", on_delete=models.DO_NOTHING)


class Report(models.Model):
    transaction = models.ForeignKey("Transaction", on_delete=models.DO_NOTHING)
    employee = models.ForeignKey("Employee", on_delete=models.DO_NOTHING)
    description = models.TextField(max_length=300)

    
class User_Status(models.Model):
    manager = models.ForeignKey("Manager", on_delete=models.DO_NOTHING)
    user = models.ForeignKey("User", on_delete=models.DO_NOTHING)
    status = models.BooleanField()


class Salary(models.Model):
    employee = models.ForeignKey("User", on_delete=models.DO_NOTHING)
    salary = models.IntegerField()


