from django.shortcuts import render
from django.http import HttpResponse
from django.template import Context, loader
import os
# Create your views here.


def dashboard(request):
    template = loader.get_template("employee/dashboard.html")
    return HttpResponse(template.render())


def change_password(request):
    template = loader.get_template("employee/change_password.html")
    return HttpResponse(template.render())


def settings(request):
    template = loader.get_template("employee/settings.html")
    return HttpResponse(template.render())



def transaction_details(request):
    template = loader.get_template("employee/transaction_details.html")
    return HttpResponse(template.render())


def customer_details(request, user_id):
    template = loader.get_template("employee/customer_details.html")
    return HttpResponse(template.render())


