from django.shortcuts import render
from django.http import HttpResponse
from django.template import Context, loader
import os
# Create your views here.


def dashboard(request):
    template = loader.get_template("manager/dashboard.html")
    return HttpResponse(template.render())


def change_password(request):
    template = loader.get_template("manager/change_password.html")
    return HttpResponse(template.render())


def settings(request):
    template = loader.get_template("manager/settings.html")
    return HttpResponse(template.render())

def transaction_details(request,id):
    template = loader.get_template("manager/transaction_details.html")
    return HttpResponse(template.render({"id": id, "type": "mammad"}))


def users(request, id):
    user_type = "employee"
    if user_type == "customer":
        user_type = "مشتری"
    else:
        user_type = "کارمند"
    template = loader.get_template("manager/users.html")
    return HttpResponse(template.render({"type": user_type}))


def customer_details(request, user_id):
    template = loader.get_template("manager/customer_details.html")
    return HttpResponse(template.render())


def employee_details(request, employee_id):
    template = loader.get_template("manager/employee_details.html")
    return HttpResponse(template.render())


def wallet(request, currency):
    if currency == "dollar":
        currency = "دلار"
    elif currency == "euro":
        currency = "یورو"
    elif currency == "rial":
        currency = "ریال"
    template = loader.get_template("customer/wallet.html")
    return HttpResponse(template.render({"currency": currency}))


def notifications(request):
    template = loader.get_template("manager/notifications.html")
    return HttpResponse(template.render())
