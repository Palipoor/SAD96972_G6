from django import forms
from django.contrib.auth.forms import AuthenticationForm
from django.contrib.auth.mixins import LoginRequiredMixin, PermissionRequiredMixin
from django.contrib.auth.views import LoginView
from django.core.mail import send_mail, BadHeaderError
from django.http import HttpResponse, HttpResponseForbidden
from django.template import loader
# Create your views here.
from django.urls import reverse_lazy
from django.views import generic
from django.views.generic import ListView, DetailView, FormView, RedirectView
from django.views.generic.edit import FormMixin, ProcessFormView
from django.shortcuts import redirect, render
from braces.views import GroupRequiredMixin
from apps.main.models import Wallet_User
from apps.main.MultiForm import MultiFormsView
from views import Compilation
from apps.main.Forms import WalletChargeForm, SignUpForm, ContactForm, ConvertForm
from django.utils.decorators import method_decorator

from django.views.decorators.csrf import csrf_exempt
from apps.customer.models import Customer
from apps.manager.models import Manager
import lxml.etree
import lxml.html
import requests
from utils.currency_utils import Transactions


# returns a dictionary containing dollar and euro prices
def get_prices():
    request = requests.get("http://2gheroon.ir")
    root = lxml.html.fromstring(request.content)

    table_rows = root.xpath('//td[@class = "priceTitle"]')
    prices = {}
    for row in table_rows:
        if 'دلار' in str(row.text):
            prices['dollar'] = int((row.xpath('./following::td')[0]).text)
            break
    for row in table_rows:
        if 'یورو' in str(row.text):
            prices['euro'] = int((row.xpath('./following::td')[0]).text)
            break
    return prices


class IsLoggedInView(LoginRequiredMixin):
    def get_redirect_field_name(self):
        return 'main/login.html'

    def get_permission_denied_message(self):
        return 'شما هنوز وارد سیستم نشده اید.'


class IsCustomer(GroupRequiredMixin):
    group_required = u"customer"


class IsEmployee(GroupRequiredMixin):
    group_required = u"employee"


class IsManager(GroupRequiredMixin):
    group_required = u"manager"


class IsWalletUser(GroupRequiredMixin):
    group_required = u"wallet_user"


class IsStaff(GroupRequiredMixin):
    group_required = [u"manager", u"employee"]


class WalletView(FormView, IsLoggedInView, IsWalletUser):
    currency = ""
    user_type = ""

    def get_context_data(self, **kwargs):
        context = super().get_context_data(**kwargs)
        context['currency'] = Transactions.currency_to_persian(self.kwargs['currency'])
        if (self.user_type == "customer"):
            context, user = Compilation.get_customer_context_data(context, self.request.user.username)
        else:
            context, user = Compilation.get_manager_context_data(cotext, self.request.user.username)
        context = Compilation.get_wallet_requests(context, self.request.user.username, Transactions.currency_to_num(self.kwargs['currency']))
        context['credit'] = context[self.kwargs['currency'] + "_credit"]
        return context

    def get_form_kwargs(self):
        kwargs = super(WalletView, self).get_form_kwargs()
        kwargs['user'] = Wallet_User.objects.get(username=self.request.user)
        kwargs['dest'] = Transactions.currency_to_num(self.kwargs['currency'])
        return kwargs

    def dispatch(self, request, *args, **kwargs):
        self.currency = kwargs['currency']
        self.user = request.user
        isManager = self.user.groups.filter(name='manager').exists()
        isCustomer = self.user.groups.filter(name='customer').exists()
        if isManager:
            self.template_name = 'manager/wallet.html'
            self.user_type = "manager"
            self.success_url = "/manager/" + self.currency + "_wallet"
        elif isCustomer:
            self.template_name = 'customer/wallet.html'
            self.user_type = "customer"
            self.success_url = "/customer/" + self.currency + "_wallet"

        else:
            return HttpResponseForbidden()

        self.form_class = WalletChargeForm
        if request.method == 'GET':
            return self.get(request, *args, **kwargs)
        else:
            return self.post(request, *args, **kwargs)

            # todo form
            # todo retrieve wallet credit and wallet transactions! give them as a context to render function!

    def form_valid(self, form):
        form.update_db()
        return super().form_valid(form)


class DetailsView(IsLoggedInView, DetailView):
    ""  # todo undone


class TransactionDetailsView(DetailsView):
    def dispatch(self, request, *args, **kwargs):
        self.transaction_id = kwargs['transaction_id']
        # todo incomplete


class AllTransactionDetails(IsStaff, TransactionDetailsView):
    ""


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


class EmployeeDetailsView(DetailsView):

    def dispatch(self, request, *args, **kwargs):
        self.employee_id = kwargs['employee_id']
        # todo incomplete


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
    if request.user.groups.filter(name="manager").exists():
        return redirect("manager/dashboard")
    elif request.user.groups.filter(name='employee').exists():
        return redirect("employee/dashboard")
    else:
        return redirect("customer/dashboard")


class LandingPageView(MultiFormsView):
    template_name = 'main/index.html'
    form_classes = {'conversion': ConvertForm, 'contact_us': ContactForm}

    def get_context_data(self, **kwargs):
        context = super(LandingPageView, self).get_context_data(forms=self.get_forms(self.form_classes))
        prices = get_prices()
        context.update(prices)
        return context

    def contact_us_form_valid(self, form):
        subject = form.cleaned_data['subject']
        from_email = form.cleaned_data['email']
        message = form.cleaned_data['message']
        try:
            send_mail(subject, message, from_email, ['palipoor976@gmail.com'])
        except BadHeaderError:
            return HttpResponse('Invalid header found.')
        context = self.get_context_data()  # todo man balad nistam ino. dorost bayad beshe.
        return render(self.request, "main/index.html", context)  # todo bere bakhshe contact us!

    def conversion_form_valid(self, form):
        prices = get_prices()
        conversion_type = form.cleaned_data['conversion_type']
        amount = form.cleaned_data['amount']
        dollar_price = prices['dollar']
        euro_price = prices['euro']

        if conversion_type == 'dollar2rial':
            converted = dollar_price * amount
        elif conversion_type == 'rial2dollar':
            converted = amount / dollar_price
        elif conversion_type == 'euro2rial':
            converted = euro_price * amount
        elif conversion_type == 'rial2euro':
            converted = amount / euro_price
        elif conversion_type == 'euro2dollar':
            converted = amount * euro_price / dollar_price
        else:
            converted = amount * dollar_price / euro_price

        context = self.get_context_data()
        context['result'] = converted
        return render(self.request, 'main/index.html', context)  # todo bere bakhshe finance!


def register_success(request):
    template = loader.get_template("main/success.html")
    return HttpResponse(template.render())

# def user_panel(request):  # in bayad be jaye dorost bere.
#     template = loader.get_template("user_panel.html")
#     return HttpResponse(template.render())
