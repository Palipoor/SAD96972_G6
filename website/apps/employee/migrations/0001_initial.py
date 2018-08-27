# Generated by Django 2.0.6 on 2018-08-27 07:18

import django.contrib.auth.models
from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    initial = True

    dependencies = [
        ('main', '0001_initial'),
        ('customer', '0001_initial'),
    ]

    operations = [
        migrations.CreateModel(
            name='Employee',
            fields=[
                ('genuser_ptr', models.OneToOneField(auto_created=True, on_delete=django.db.models.deletion.CASCADE, parent_link=True, primary_key=True, serialize=False, to='main.GenUser')),
                ('current_salary', models.IntegerField()),
            ],
            options={
                'verbose_name': 'user',
                'verbose_name_plural': 'users',
                'abstract': False,
            },
            bases=('main.genuser',),
            managers=[
                ('objects', django.contrib.auth.models.UserManager()),
            ],
        ),
        migrations.CreateModel(
            name='EmployeeReview',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('description', models.TextField(default='', max_length=300)),
                ('new_status', models.IntegerField(choices=[(0, 'accepted'), (1, 'rejected'), (2, 'pending'), (3, 'failed'), (4, 'reported')], default=0)),
                ('employee', models.ForeignKey(on_delete=django.db.models.deletion.DO_NOTHING, to='main.GenUser')),
                ('request', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='customer.Request')),
            ],
        ),
        migrations.CreateModel(
            name='Salary',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('salary', models.IntegerField()),
                ('employee', models.ForeignKey(on_delete=django.db.models.deletion.DO_NOTHING, to='main.GenUser')),
            ],
        ),
    ]