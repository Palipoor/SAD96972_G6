# Generated by Django 2.0.3 on 2018-08-18 17:04

from django.db import migrations, models


class Migration(migrations.Migration):

    initial = True

    dependencies = [
    ]

    operations = [
        migrations.CreateModel(
            name='Photo',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('created_on', models.DateTimeField(auto_now_add=True, verbose_name='Created on')),
                ('updated_on', models.DateTimeField(auto_now=True, verbose_name='Updated on')),
                ('title', models.CharField(max_length=255, verbose_name='Title')),
                ('link', models.URLField(help_text='The URL to the image page', max_length=255, verbose_name='Photo Link')),
                ('image_url', models.URLField(help_text='The URL to the image file itself', max_length=255, verbose_name='Image URL')),
                ('description', models.TextField(verbose_name='Description')),
            ],
            options={
                'verbose_name': 'Photo',
                'verbose_name_plural': 'Photos',
                'ordering': ['-created_on', 'title'],
            },
        ),
    ]
