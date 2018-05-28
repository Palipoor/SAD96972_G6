from django.shortcuts import render
from django.http import HttpResponse
from django.template import Context, loader
import os
# Create your views here.

class IsLoggedInView(LoginRequiredMixin):
    '''is the user logged in'''
    ''' baraye redirect kardan be login url'''

class IsManagerView(LoginRequiredMixin):
    def dispatch(self, request, *args, **kwargs):
        ''' authenticate the user as the manager '''

class IsEmployeeView(LoginRequiredMixin):
    def dispatch(self, request, *args, **kwargs):
        ''' authenticate the user as an employee (could be an employee or the manager!) '''

class IsCustomerView(LoginRequiredMixin):
    def dispatch(self, request, *args, **kwargs):
                ''' authenticate the user as a customer'''
                
class IsAWalletUserView():
    def dispatch(self, request, *args, **kwargs):
        '''authenticate the user as a wallet user'''

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


def user_panel(request): #in bayad be jaye dorost bere.
    template = loader.get_template("user_panel.html")
    return HttpResponse(template.render())
