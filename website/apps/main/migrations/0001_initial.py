# Generated by Django 2.0.6 on 2018-08-27 07:18

from django.conf import settings
import django.contrib.auth.models
from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    initial = True

    dependencies = [
        ('auth', '0009_alter_user_last_name_max_length'),
        ('contenttypes', '0002_remove_content_type_name'),
    ]

    operations = [
        migrations.CreateModel(
            name='GenUser',
            fields=[
                ('user_ptr', models.OneToOneField(auto_created=True, on_delete=django.db.models.deletion.CASCADE, parent_link=True, primary_key=True, serialize=False, to=settings.AUTH_USER_MODEL)),
                ('persian_first_name', models.CharField(blank=True, max_length=50)),
                ('persian_last_name', models.CharField(blank=True, max_length=50)),
                ('phone_number', models.CharField(blank=True, max_length=20)),
                ('user_type', models.IntegerField(blank=True, choices=[(0, 'Customer'), (1, 'Employee'), (2, 'Manager')], default=0)),
            ],
            options={
                'verbose_name': 'user',
                'verbose_name_plural': 'users',
                'abstract': False,
            },
            bases=('auth.user',),
            managers=[
                ('objects', django.contrib.auth.models.UserManager()),
            ],
        ),
        migrations.CreateModel(
            name='Notification',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('sent_date', models.DateTimeField(auto_now_add=True)),
                ('seen', models.BooleanField(default=False)),
                ('text', models.CharField(max_length=500)),
            ],
            options={
                'abstract': False,
                'base_manager_name': 'objects',
            },
        ),
        migrations.CreateModel(
            name='WalletChange',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('value', models.FloatField()),
                ('wallet', models.IntegerField(choices=[(0, 'rial'), (1, 'dollar'), (2, 'euro')])),
                ('time', models.DateTimeField()),
                ('is_profit', models.BooleanField(default=False)),
            ],
        ),
        migrations.CreateModel(
            name='Critical_Credit_Notification',
            fields=[
                ('notification_ptr', models.OneToOneField(auto_created=True, on_delete=django.db.models.deletion.CASCADE, parent_link=True, primary_key=True, serialize=False, to='main.Notification')),
            ],
            options={
                'abstract': False,
                'base_manager_name': 'objects',
            },
            bases=('main.notification',),
        ),
        migrations.CreateModel(
            name='Wallet_User',
            fields=[
                ('genuser_ptr', models.OneToOneField(auto_created=True, on_delete=django.db.models.deletion.CASCADE, parent_link=True, primary_key=True, serialize=False, to='main.GenUser')),
                ('rial_credit', models.FloatField(default=0)),
                ('dollar_cent_credit', models.FloatField(default=0)),
                ('euro_cent_credit', models.FloatField(default=0)),
                ('account_number', models.CharField(max_length=20, unique=True)),
                ('minimum_rial_credit', models.IntegerField(blank=True, null=True)),
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
        migrations.AddField(
            model_name='walletchange',
            name='user',
            field=models.ForeignKey(on_delete=django.db.models.deletion.DO_NOTHING, to='main.GenUser'),
        ),
        migrations.AddField(
            model_name='notification',
            name='polymorphic_ctype',
            field=models.ForeignKey(editable=False, null=True, on_delete=django.db.models.deletion.CASCADE, related_name='polymorphic_main.notification_set+', to='contenttypes.ContentType'),
        ),
        migrations.AddField(
            model_name='notification',
            name='user',
            field=models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='main.GenUser'),
        ),
    ]