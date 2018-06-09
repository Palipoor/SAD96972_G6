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
from apps.main.views import index, register_success, Register, Login, login_success

urlpatterns = [
    path('', index),
    path('login', Login.as_view(), name = 'login'),
    path('login_success',login_success, name = "login_success"),
    path('register', Register.as_view(), name = 'register'),
    path('register_success', register_success, name = 'register_success'),
]
