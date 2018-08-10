
# Generated by Django 2.0.6 on 2018-07-29 07:22
from django.contrib.auth.hashers import make_password
from django.db import migrations


def add_customer(apps, schema_editor):
    Group = apps.get_model('auth', 'Group')
    Customer = apps.get_model('customer', 'Customer')
    try:
        customer = Customer.objects.get(username='customer', email='customer@customer.com')
    except:
        customer = Customer(username='customer', email='customer@customer.com')
        customer.password = make_password('customercustomer')
        customer.save()
        customer_group = Group.objects.get(name='customer')
        customer_group.user_set.add(customer)
        customer_group = Group.objects.get(name='wallet_user')
        customer_group.user_set.add(customer)


def remove_customer(apps, schema_editor):
    Customer = apps.get_model('customer', 'Customer')
    try:
        customer = Customer.objects.get(username='customer')
        customer.delete()
    except:
        pass


class Migration(migrations.Migration):

    dependencies = [
        ('customer', '0004_auto_20180729_0723'),
        ('main', '0006_auto_20180729_0709')
    ]

    operations = [
        migrations.RunPython(add_customer, remove_customer)
    ]
