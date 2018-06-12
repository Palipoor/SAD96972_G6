from django.db import models
from django.contrib.auth.models import User,Group
from polymorphic.models import PolymorphicModel


# Create your models here.
class GenUser(User):
    types = (
        (0, 'Customer'),
        (1, 'Employee'),
        (2, 'Manager'),
    )
    '''username = models.CharField(max_length=100, primary_key=True)
    password = models.CharField(max_length=50)
    email = models.EmailField(max_length=70, unique=True, null=False)'''
    # are email and username unique in Django User? fix if they are not.
    #photo = models.ImageField()   imagefiled needs something before makemigration
    '''english_first_name = models.CharField(max_length=50)
    english_last_name = models.CharField(max_length=50)'''
    persian_first_name = models.CharField(max_length=50)
    persian_last_name = models.CharField(max_length=50)
    phone_number = models.CharField(max_length=20)
    user_type = models.IntegerField(choices=types, default=0)
    # online = models.BooleanField(default=True)
    # active = models.BooleanField(default=True)

    def save(self, *args, **kwargs):
        super().save(*args, **kwargs)
        g = Group.objects.get(name = self.types[self.user_type][1])
        self.groups.add(g)



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
    user = models.ForeignKey('GenUser', on_delete=models.CASCADE)
    text = models.CharField(max_length=500)
    type = models.IntegerField(choices=types)
    status = models.IntegerField(choices=statuses)
