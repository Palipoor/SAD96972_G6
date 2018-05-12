"""backend URL Configuration

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/2.0/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  path('', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  path('', Home.as_view(), name='home')
Including another URLconf
    1. Import the include() function: from django.urls import include, path
    2. Add a URL to urlpatterns:  path('blog/', include('blog.urls'))
"""
from django.contrib import admin
from django.urls import path
from apps.employee.views import dashboard, change_password, settings, transaction_details, customer_details, notifications
urlpatterns = [
    path('dashboard', dashboard, name='dashboard'),
    path('change_password', change_password, name='change_password'),
    path('settings', settings, name='settings'),
    path('<transaction_id>_transaction_details', transaction_details, name='transaction_details'),
    path('settings/', settings, name='settings'),
    path('<user_id>_customer_details/', customer_details, name='customer_details'),
    path('notifications/', notifications, name='notifications'),
]
