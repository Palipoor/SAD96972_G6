from django.shortcuts import render
from django.http import HttpResponse
from django.template import Context, loader
import os
# Create your views here.


def dashboard(request):
    template = loader.get_template("customer/dashboard.html")
    return HttpResponse(template.render())


def change_password(request):
    template = loader.get_template("customer/change_password.html")
    return HttpResponse(template.render())


def settings(request):
    template = loader.get_template("customer/settings.html")
    return HttpResponse(template.render())


def mytransactions(request):
    template = loader.get_template("customer/mytransactions.html")
    return HttpResponse(template.render())


def transaction_details(request):
    template = loader.get_template("customer/transaction_details.html")
    return HttpResponse(template.render())
