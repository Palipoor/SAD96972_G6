from django.contrib import admin

from apps.period.models import Photo


class PhotoAdmin(admin.ModelAdmin):
    pass

admin.site.register(Photo, PhotoAdmin)
