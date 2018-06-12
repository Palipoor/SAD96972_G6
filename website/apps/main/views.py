from django import forms
from django.contrib.auth.forms import AuthenticationForm
from django.contrib.auth.mixins import LoginRequiredMixin, PermissionRequiredMixin
from django.contrib.auth.views import LoginView
from django.http import HttpResponse, HttpResponseForbidden
from django.template import loader
# Create your views here.
from django.urls import reverse_lazy
from django.views import generic
from django.views.generic import ListView, DetailView, FormView, RedirectView
from django.views.generic.edit import FormMixin, ProcessFormView
from django.shortcuts import redirect
from braces.views import GroupRequiredMixin

from apps.main.Forms import SignUpForm, RialChargeForm, DollarChargeForm, EuroChargeForm
from apps.customer.models import Customer


class IsLoggedInView(LoginRequiredMixin):
    def get_redirect_field_name(self):
        return 'main/login.html'

    def get_permission_denied_message(self):
        return 'شما هنوز وارد سیستم نشده اید.'


class IsCustomer(GroupRequiredMixin):
    group_required = u"Customer"


class IsEmployee(GroupRequiredMixin):
    group_required = u"Employee"


class IsManager(GroupRequiredMixin):
    group_required = u"Manager"


class IsWalletUser(GroupRequiredMixin):
    group_required = [u"Manager", u"Customer"]


class HasAccessToTransactions(GroupRequiredMixin):
    group_required = [u"Manager", u"Employee"]


class WalletView(IsLoggedInView, IsWalletUser, FormView):
    user_type = ""

    def get_form_kwargs(self):
        kwargs = super(WalletView, self).get_form_kwargs()
        kwargs['user'] = self.request.user
        return kwargs

    def dispatch(self, request, *args, **kwargs):
        currency = kwargs['currency']
        user = request.user
        isManager = user.groups.filter(name='Manager').exists()
        isCustomer = user.groups.filter(name='Customer').exists()
        if isManager and self.user_type == "Manager":
            self.template_name = 'manager/wallet.html'
        elif isCustomer and self.user_type == "Customer":
            self.template_name = 'customer/wallet.html'
        else:
            return HttpResponseForbidden()

        if currency == 'rial':
            self.form_class = RialChargeForm
        elif currency == 'dollar':
            self.form_class = DollarChargeForm
        else:
            self.form_class = EuroChargeForm
        if request.method == 'GET':
            return self.get(request, *args, **kwargs)
        else:
            return self.post(request, *args, **kwargs)

    # todo form
    # todo retrieve wallet credit and wallet transactions! give them as a context to render function!
    def wallet(self, currency, user_type):
        if currency == "dollar":
            currency = "دلار"
        elif currency == "euro":
            currency = "یورو"
        elif currency == "rial":
            currency = "ریال"
        template = loader.get_template(user_type + "/wallet.html")
        return HttpResponse(template.render({"currency": currency}))


class DetailsView(IsLoggedInView, DetailView):
    ""  # todo undone


class TransactionDetailsView(DetailsView):
    def dispatch(self, request, *args, **kwargs):
        self.transaction_id = kwargs['transaction_id']
        # todo incomplete


class NotificationsView(IsLoggedInView, ListView):
    ""


class CustomerDetailsView(DetailsView, ListView):
    model = Customer
    fields = ['persian_first_name', 'persian_last_name', 'english_first_name', 'english_last_name', 'username', 'email',
              'phone', 'account-number']
    context_object_name = 'transactions'
    template_name = 'manager/customer_details'

    def get_context_data(self, **kwargs):
        return []  # todo query bezan transaction haye user e marboot ro biar va khode adame ro.


class Register(FormView):
    # todo errors are not shown properly. validation is not good! accepts ! as a valid username. shame on us.
    form_class = SignUpForm
    success_url = reverse_lazy('main:register_success')
    template_name = 'main/register.html'

    def post(self, request, *args, **kwargs):
        form = self.get_form()
        if form.is_valid():
            new_user = Customer(username=form.cleaned_data['username'],
                                first_name=form.cleaned_data["first_name"], last_name=form.cleaned_data["last_name"],
                                persian_first_name=form.cleaned_data['persian_first_name'],
                                persian_last_name=form.cleaned_data['persian_last_name'],
                                email=form.cleaned_data['email'], phone_number=form.cleaned_data['phone_number'],
                                account_number=form.cleaned_data['account_number'])
            new_user.set_password(form.cleaned_data['password'])
            new_user.save()
            return self.form_valid(form)
        else:
            return self.form_invalid(form)


class Login(LoginView):
    template_name = 'main/login.html'


def login_success(request):
    if request.user.groups.filter(name="Manager").exists():
        return redirect("manager/dashboard")
    elif request.user.groups.filter(name='Employee').exists():
        return redirect("employee/dashboard")
    else:
        return redirect("customer/dashboard")


def index(request):
    template = loader.get_template("main/index.html")
    return HttpResponse(template.render())


def register_success(request):
    template = loader.get_template("main/success.html")
    return HttpResponse(template.render())

# def user_panel(request):  # in bayad be jaye dorost bere.
#     template = loader.get_template("user_panel.html")
#     return HttpResponse(template.render())
