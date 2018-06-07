# Generated by Django 2.0.3 on 2018-06-03 22:26

from django.conf import settings
import django.contrib.auth.models
from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    initial = True

    dependencies = [
        ('contenttypes', '0002_remove_content_type_name'),
        ('auth', '0009_alter_user_last_name_max_length'),
    ]

    operations = [
        migrations.CreateModel(
            name='GenUser',
            fields=[
                ('user_ptr', models.OneToOneField(auto_created=True, on_delete=django.db.models.deletion.CASCADE, parent_link=True, primary_key=True, serialize=False, to=settings.AUTH_USER_MODEL)),
                ('persian_first_name', models.CharField(max_length=50)),
                ('persian_last_name', models.CharField(max_length=50)),
                ('phone_number', models.CharField(max_length=20)),
                ('active', models.BooleanField(default=True)),
                ('polymorphic_ctype', models.ForeignKey(editable=False, null=True, on_delete=django.db.models.deletion.CASCADE, related_name='polymorphic_main.genuser_set+', to='contenttypes.ContentType')),
            ],
            options={
                'verbose_name': 'user',
                'verbose_name_plural': 'users',
                'abstract': False,
            },
            bases=('auth.user', models.Model),
            managers=[
                ('objects', django.contrib.auth.models.UserManager()),
            ],
        ),
        migrations.CreateModel(
            name='Notification',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('text', models.CharField(max_length=500)),
                ('type', models.IntegerField(choices=[(0, 'message'), (1, 'blah'), (2, 'blah  blah')])),
                ('status', models.IntegerField(choices=[(0, 'seen'), (1, 'unseen')])),
                ('user', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='main.GenUser')),
            ],
        ),
    ]
