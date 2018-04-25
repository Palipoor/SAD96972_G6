from django.shortcuts import render
from django.http import HttpResponse
from django.template import Context, loader
import os
# Create your views here.


def dashboard(request):
    template = loader.get_template("customer/dashboard.html")
    return HttpResponse(template.render())
