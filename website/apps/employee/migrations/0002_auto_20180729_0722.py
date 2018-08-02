# Generated by Django 2.0.6 on 2018-07-29 07:22

from django.db import migrations

def add_employee(apps, schema_editor):
    Group = apps.get_model('auth', 'Group')
    employee_group = Group.objects.get(name='employee')
    Employee = apps.get_model('employee', 'Employee')
    employee = Employee.objects.get_or_create(username= 'employee', email = 'employee@employee.com')
    employee.set_password('employeeemployee')
    employee_group.user_set.add(employee)

def remove_employee(apps,schema_editor):
    Employee = apps.get_model('employee', 'Employee')
    employee = Employee.objects.get_or_create(username= 'employee')
    employee.delete()    
    
class Migration(migrations.Migration):

    dependencies = [
        ('employee', '0001_initial'),
    ]

    operations = [
        migrations.RunPython(add_employee, remove_employee)
    ]
