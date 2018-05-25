from django.db import models

# Create your models here.


class User(models.Model):

    class Meta:
        abstract = True
    '''types = (
        (0, 'customer'),
        (1, 'employee'),
        (2, 'manager'),
    )'''
    username = models.CharField(max_length=100, primary_key=True)
    password = models.CharField(max_length=50)
    email = models.EmailField(max_length=70, unique=True, null=False)
    photo = models.ImageField()
    english_first_name = models.CharField(max_length=50)
    english_last_name = models.CharField(max_length=50)
    persian_first_name = models.CharField(max_length=50)
    persian_last_name = models.CharField(max_length=50)
    #user_type = models.IntegerField(choices=types)
    #online = models.BooleanField(default=True)
    active = models.BooleanField(default=True)

    
class Notification(models.Model):
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
    type = models.IntegerField(choices=types)
    status = models.IntegerField(choices=statuses)
