# Generated by Django 2.0.6 on 2018-06-12 13:34

from django.db import migrations


class Migration(migrations.Migration):

    dependencies = [
        ('main', '0003_genuser_user_type'),
    ]

    operations = [
        migrations.RemoveField(
            model_name='genuser',
            name='active',
        ),
    ]