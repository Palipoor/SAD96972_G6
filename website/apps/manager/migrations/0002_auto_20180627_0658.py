# Generated by Django 2.0.6 on 2018-06-27 06:58

from django.db import migrations


class Migration(migrations.Migration):

    dependencies = [
        ('manager', '0001_initial'),
    ]

    operations = [
        migrations.RemoveField(
            model_name='companywalletchanges',
            name='request',
        ),
        migrations.DeleteModel(
            name='CompanyWalletCharge',
        ),
        migrations.DeleteModel(
            name='CompanyWalletTransfer',
        ),
        migrations.DeleteModel(
            name='CompanyWalletChanges',
        ),
    ]
