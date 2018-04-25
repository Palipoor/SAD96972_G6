from django.shortcuts import render
from django.http import HttpResponse
from django.template import Context, loader
import os
# Create your views here.


def index(request):
    template = loader.get_template("main/index.html")
    return HttpResponse(template.render())

def login(request):
    template = loader.get_template("main/login.html")
    return HttpResponse(template.render())


def register(request):
    template = loader.get_template("main/register.html")
    return HttpResponse(template.render())


def register_success(request):
    template = loader.get_template("main/success.html")
    return HttpResponse(template.render())
