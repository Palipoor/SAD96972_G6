# Generated by Django 2.0.7 on 2018-08-21 13:39

import django.contrib.auth.models
from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    initial = True

    dependencies = [
        ('main', '0001_initial'),
        ('contenttypes', '0002_remove_content_type_name'),
    ]

    operations = [
        migrations.CreateModel(
            name='Customer',
            fields=[
                ('wallet_user_ptr', models.OneToOneField(auto_created=True, on_delete=django.db.models.deletion.CASCADE, parent_link=True, primary_key=True, serialize=False, to='main.Wallet_User')),
            ],
            options={
                'verbose_name': 'user',
                'verbose_name_plural': 'users',
                'abstract': False,
            },
            bases=('main.wallet_user',),
            managers=[
                ('objects', django.contrib.auth.models.UserManager()),
            ],
        ),
        migrations.CreateModel(
            name='Request',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('source_wallet', models.IntegerField(blank=True, choices=[(0, 'rial'), (1, 'dollar'), (2, 'euro')], default=0)),
                ('dest_wallet', models.IntegerField(blank=True, choices=[(0, 'rial'), (1, 'dollar'), (2, 'euro')], default=0)),
                ('fianl_wallet', models.IntegerField(blank=True, choices=[(0, 'rial'), (1, 'dollar'), (2, 'euro')], default=0)),
                ('amount', models.FloatField(blank=True)),
                ('request_time', models.DateTimeField(auto_now_add=True)),
                ('description', models.CharField(blank=True, max_length=500)),
                ('status', models.IntegerField(blank=True, choices=[(0, 'accepted'), (1, 'rejected'), (2, 'pending'), (3, 'failed'), (4, 'reported')], default=2)),
                ('profitRate', models.FloatField(blank=True, default=0)),
                ('exchange_rate', models.FloatField(blank=True, null=True)),
                ('type', models.CharField(max_length=100)),
            ],
            options={
                'abstract': False,
                'base_manager_name': 'objects',
            },
        ),
        migrations.CreateModel(
            name='Charge',
            fields=[
                ('request_ptr', models.OneToOneField(auto_created=True, on_delete=django.db.models.deletion.CASCADE, parent_link=True, primary_key=True, serialize=False, to='customer.Request')),
            ],
            options={
                'abstract': False,
                'base_manager_name': 'objects',
            },
            bases=('customer.request',),
        ),
        migrations.CreateModel(
            name='CustomTransactionInstance',
            fields=[
                ('request_ptr', models.OneToOneField(auto_created=True, on_delete=django.db.models.deletion.CASCADE, parent_link=True, primary_key=True, serialize=False, to='customer.Request')),
                ('email1', models.EmailField(max_length=254, null=True)),
                ('email2', models.EmailField(max_length=254, null=True)),
                ('email3', models.EmailField(max_length=254, null=True)),
                ('short_text1', models.CharField(max_length=100, null=True)),
                ('short_text2', models.CharField(max_length=100, null=True)),
                ('short_text3', models.CharField(max_length=100, null=True)),
                ('short_text4', models.CharField(max_length=100, null=True)),
                ('short_text5', models.CharField(max_length=100, null=True)),
                ('short_text6', models.CharField(max_length=100, null=True)),
                ('short_text7', models.CharField(max_length=100, null=True)),
                ('short_text8', models.CharField(max_length=100, null=True)),
                ('long_text1', models.TextField(null=True)),
                ('long_text2', models.TextField(null=True)),
                ('long_text3', models.TextField(null=True)),
                ('long_text4', models.TextField(null=True)),
                ('long_text5', models.TextField(null=True)),
                ('int1', models.IntegerField(null=True)),
                ('int2', models.IntegerField(null=True)),
                ('int3', models.IntegerField(null=True)),
                ('int4', models.IntegerField(null=True)),
                ('int5', models.IntegerField(null=True)),
                ('date1', models.DateTimeField(null=True)),
                ('date2', models.DateTimeField(null=True)),
                ('date3', models.DateTimeField(null=True)),
                ('date4', models.DateTimeField(null=True)),
                ('float1', models.FloatField(null=True)),
                ('float2', models.FloatField(null=True)),
                ('float3', models.FloatField(null=True)),
                ('float4', models.FloatField(null=True)),
                ('boolean1', models.NullBooleanField()),
                ('boolean2', models.NullBooleanField()),
                ('boolean3', models.NullBooleanField()),
                ('boolean4', models.NullBooleanField()),
                ('boolean5', models.NullBooleanField()),
                ('file1', models.FileField(null=True, upload_to='')),
                ('file2', models.FileField(null=True, upload_to='')),
                ('file3', models.FileField(null=True, upload_to='')),
                ('image1', models.ImageField(null=True, upload_to='')),
                ('image2', models.ImageField(null=True, upload_to='')),
                ('url1', models.URLField(null=True)),
                ('url2', models.URLField(null=True)),
            ],
            options={
                'abstract': False,
                'base_manager_name': 'objects',
            },
            bases=('customer.request',),
        ),
        migrations.CreateModel(
            name='Exchange',
            fields=[
                ('request_ptr', models.OneToOneField(auto_created=True, on_delete=django.db.models.deletion.CASCADE, parent_link=True, primary_key=True, serialize=False, to='customer.Request')),
            ],
            options={
                'abstract': False,
                'base_manager_name': 'objects',
            },
            bases=('customer.request',),
        ),
        migrations.CreateModel(
            name='ForeignTrans',
            fields=[
                ('request_ptr', models.OneToOneField(auto_created=True, on_delete=django.db.models.deletion.CASCADE, parent_link=True, primary_key=True, serialize=False, to='customer.Request')),
                ('account_number', models.CharField(max_length=20)),
                ('bank_name', models.CharField(max_length=50)),
            ],
            options={
                'abstract': False,
                'base_manager_name': 'objects',
            },
            bases=('customer.request',),
        ),
        migrations.CreateModel(
            name='InternalTrans',
            fields=[
                ('request_ptr', models.OneToOneField(auto_created=True, on_delete=django.db.models.deletion.CASCADE, parent_link=True, primary_key=True, serialize=False, to='customer.Request')),
                ('account_number', models.CharField(max_length=20)),
                ('bank_name', models.CharField(max_length=50)),
            ],
            options={
                'abstract': False,
                'base_manager_name': 'objects',
            },
            bases=('customer.request',),
        ),
        migrations.CreateModel(
            name='LangTest',
            fields=[
                ('request_ptr', models.OneToOneField(auto_created=True, on_delete=django.db.models.deletion.CASCADE, parent_link=True, primary_key=True, serialize=False, to='customer.Request')),
                ('username', models.CharField(max_length=100)),
                ('password', models.CharField(max_length=50)),
            ],
            options={
                'abstract': False,
                'base_manager_name': 'objects',
            },
            bases=('customer.request',),
        ),
        migrations.CreateModel(
            name='Reverse_Request',
            fields=[
                ('request_ptr', models.OneToOneField(auto_created=True, on_delete=django.db.models.deletion.CASCADE, parent_link=True, primary_key=True, serialize=False, to='customer.Request')),
            ],
            options={
                'abstract': False,
                'base_manager_name': 'objects',
            },
            bases=('customer.request',),
        ),
        migrations.CreateModel(
            name='UniversityTrans',
            fields=[
                ('request_ptr', models.OneToOneField(auto_created=True, on_delete=django.db.models.deletion.CASCADE, parent_link=True, primary_key=True, serialize=False, to='customer.Request')),
                ('type1', models.IntegerField(choices=[(0, 'application fee'), (1, 'deposit fee')])),
                ('university_name', models.CharField(max_length=50)),
                ('link', models.URLField()),
                ('username', models.CharField(max_length=100)),
                ('password', models.CharField(max_length=50)),
                ('guide', models.CharField(max_length=1000)),
            ],
            options={
                'abstract': False,
                'base_manager_name': 'objects',
            },
            bases=('customer.request',),
        ),
        migrations.CreateModel(
            name='UnknownTrans',
            fields=[
                ('request_ptr', models.OneToOneField(auto_created=True, on_delete=django.db.models.deletion.CASCADE, parent_link=True, primary_key=True, serialize=False, to='customer.Request')),
                ('account_number', models.CharField(max_length=20)),
                ('bank_name', models.CharField(max_length=50)),
                ('email', models.EmailField(max_length=254)),
                ('phone_number', models.CharField(max_length=20)),
            ],
            options={
                'abstract': False,
                'base_manager_name': 'objects',
            },
            bases=('customer.request',),
        ),
        migrations.AddField(
            model_name='request',
            name='dest_user',
            field=models.ForeignKey(blank=True, null=True, on_delete=django.db.models.deletion.DO_NOTHING, related_name='dest_user', to='main.Wallet_User'),
        ),
        migrations.AddField(
            model_name='request',
            name='final_user',
            field=models.ForeignKey(blank=True, null=True, on_delete=django.db.models.deletion.DO_NOTHING, related_name='final_user', to='main.Wallet_User'),
        ),
        migrations.AddField(
            model_name='request',
            name='polymorphic_ctype',
            field=models.ForeignKey(editable=False, null=True, on_delete=django.db.models.deletion.CASCADE, related_name='polymorphic_customer.request_set+', to='contenttypes.ContentType'),
        ),
        migrations.AddField(
            model_name='request',
            name='source_user',
            field=models.ForeignKey(blank=True, null=True, on_delete=django.db.models.deletion.DO_NOTHING, related_name='source_user', to='main.Wallet_User'),
        ),
        migrations.CreateModel(
            name='IBT',
            fields=[
                ('langtest_ptr', models.OneToOneField(auto_created=True, on_delete=django.db.models.deletion.CASCADE, parent_link=True, primary_key=True, serialize=False, to='customer.LangTest')),
                ('test_center_name', models.CharField(max_length=100)),
                ('test_center_code', models.CharField(max_length=50)),
                ('city', models.CharField(max_length=100)),
                ('country', models.CharField(max_length=100)),
                ('date', models.DateTimeField()),
            ],
            options={
                'abstract': False,
                'base_manager_name': 'objects',
            },
            bases=('customer.langtest',),
        ),
        migrations.AddField(
            model_name='reverse_request',
            name='reference',
            field=models.ForeignKey(on_delete=django.db.models.deletion.DO_NOTHING, related_name='related_request', to='customer.Request', unique=True),
        ),
        migrations.AddField(
            model_name='customtransactioninstance',
            name='request1',
            field=models.ForeignKey(null=True, on_delete=django.db.models.deletion.DO_NOTHING, related_name='first_request', to='customer.Request'),
        ),
        migrations.AddField(
            model_name='customtransactioninstance',
            name='request2',
            field=models.ForeignKey(null=True, on_delete=django.db.models.deletion.DO_NOTHING, related_name='second_request', to='customer.Request'),
        ),
        migrations.AddField(
            model_name='customtransactioninstance',
            name='user1',
            field=models.ForeignKey(null=True, on_delete=django.db.models.deletion.DO_NOTHING, related_name='first_user', to='customer.Customer'),
        ),
        migrations.AddField(
            model_name='customtransactioninstance',
            name='user2',
            field=models.ForeignKey(null=True, on_delete=django.db.models.deletion.DO_NOTHING, related_name='second_user', to='customer.Customer'),
        ),
        migrations.CreateModel(
            name='GRE',
            fields=[
                ('ibt_ptr', models.OneToOneField(auto_created=True, on_delete=django.db.models.deletion.CASCADE, parent_link=True, primary_key=True, serialize=False, to='customer.IBT')),
                ('major_filed_code', models.CharField(max_length=50)),
                ('major_filed_name', models.CharField(max_length=50)),
                ('citizenship', models.IntegerField(choices=[(0, 'blah'), (1, 'blah'), (2, 'blah')])),
                ('educational_status', models.IntegerField(choices=[(0, 'blah'), (1, 'blah'), (2, 'blah'), (3, 'blah'), (4, 'blah'), (5, 'blah'), (6, 'blah'), (7, 'blah')])),
            ],
            options={
                'abstract': False,
                'base_manager_name': 'objects',
            },
            bases=('customer.ibt',),
        ),
        migrations.CreateModel(
            name='TOFEL',
            fields=[
                ('ibt_ptr', models.OneToOneField(auto_created=True, on_delete=django.db.models.deletion.CASCADE, parent_link=True, primary_key=True, serialize=False, to='customer.IBT')),
                ('reason', models.IntegerField(choices=[(0, 'blah'), (1, 'blah'), (2, 'blah'), (3, 'blah'), (4, 'blah'), (5, 'blah'), (6, 'blah'), (7, 'blah'), (8, 'blah'), (9, 'blah')])),
                ('country_for_study', models.IntegerField(choices=[(0, 'blah'), (1, 'blah'), (2, 'blah'), (3, 'blah'), (4, 'blah'), (5, 'blah'), (6, 'blah'), (7, 'blah'), (8, 'blah'), (9, 'blah')])),
                ('id_type', models.IntegerField(choices=[(0, 'Passport'), (1, 'National ID')])),
                ('id_number', models.CharField(max_length=20)),
            ],
            options={
                'abstract': False,
                'base_manager_name': 'objects',
            },
            bases=('customer.ibt',),
        ),
    ]
