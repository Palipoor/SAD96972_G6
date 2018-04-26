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


def wallet(request,currency):
    if currency == "dollar":
        currency = "دلار"
    elif currency == "euro":
        currency = "یورو"
    elif currency == "rial":
        currency = "ریال"
    template = loader.get_template("customer/wallet.html")
    return HttpResponse(template.render({"currency":currency}))

def reverse_charge(request):
    template = loader.get_template("customer/reverse_charge.html")
    return HttpResponse(template.render())


def foreign_payment(request):
    template = loader.get_template("customer/foreign_payment.html")
    return HttpResponse(template.render())


def application_fee(request):
    template = loader.get_template("customer/application_fee.html")
    return HttpResponse(template.render())


def anonymous_payment(request):
    template = loader.get_template("customer/anonymous_payment.html")
    return HttpResponse(template.render())
