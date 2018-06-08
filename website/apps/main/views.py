from django import forms
from django.contrib.auth.forms import AuthenticationForm
from django.contrib.auth.mixins import LoginRequiredMixin, PermissionRequiredMixin
from django.contrib.auth.views import LoginView
from django.http import HttpResponse
from django.template import loader
# Create your views here.
from django.urls import reverse_lazy
from django.views import generic
from django.views.generic import ListView, DetailView, FormView
from django.views.generic.edit import FormMixin

from apps.main.Forms import SignUpForm
from apps.customer.models import Customer


class IsLoggedInView(LoginRequiredMixin):
    def get_redirect_field_name(self):
        return 'main/login.html'

    def get_permission_denied_message(self):
        return 'شما هنوز وارد سیستم نشده اید.'


class WalletView(IsLoggedInView, PermissionRequiredMixin, FormMixin, ListView):
    def has_permission(self):
        return self.request.user.user_type == 'customer' or self.request.user.user_type == 'manager'

    def dispatch(self, request, *args, **kwargs):
        currency = kwargs['currency']
        user_type = request.user.user_type
        if user_type == 'customer':
            username = request.user.username
            return self.wallet(currency, username)
        else:
            username = ""
            return self.wallet(currency, username)

    # todo form
    # todo retrieve wallet credit and wallet transactions! give them as a context to render function!
    def wallet(self, currency, username):
        if currency == "dollar":
            currency = "دلار"
        elif currency == "euro":
            currency = "یورو"
        elif currency == "rial":
            currency = "ریال"
        if username != "":
            template = loader.get_template("customer/" + username + "/wallet.html")
            return HttpResponse(template.render({"currency": currency}))
        else:
            template = loader.get_template("manager/" + "/wallet.html")
            return HttpResponse(template.render({"currency": currency}))


class DetailsView(IsLoggedInView, PermissionRequiredMixin, DetailView):
    ""  # todo undone


class TransactionDetailsView(DetailsView):
    def has_permission(self):
        return self.request.user.user_type == 'manager' or self.request.user.user_type == 'employee'

    model = Transaction

    def get_object(self, queryset=None):
        return Transaction.objects.get(id=self.transaction_id)

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
    form_class = SignUpForm
    success_url = reverse_lazy('login')
    template_name = 'main/register.html'

    def post(self, request, *args, **kwargs):
        form = self.get_form()
        if form.is_valid():
            new_user = Customer(username=form.cleaned_data['username'], password=form.cleaned_data['password'],
                                persian_first_name=form.cleaned_data['persian_first_name'],
                                persian_last_name=form.cleaned_data['persian_last_name'],
                                email=form.cleaned_data['email'], phone=form.cleaned_data['phone'],
                                account=form.cleaned_data['account'])
            new_user.save()
            return self.form_valid(form)
        else:
            return self.form_invalid(form)


class Login(LoginView):
    def get_success_url(self):
        return self.request.user.user_type + "/dashboard"

    def get_form(self, form_class=None):
        self.form_class = AuthenticationForm
        form = AuthenticationForm()
        form.username.widget = forms.TextInput(attrs={'autofocus': True, 'name': 'username'})
        form.username.label = 'نام کاربری'
        form.password.widget = forms.PasswordInput(attrs={'name': 'password'})
        form.password.label = 'کلمهٔ عبور'


def index(request):
    template = loader.get_template("main/index.html")
    return HttpResponse(template.render())


def register_success(request):
    template = loader.get_template("main/success.html")
    return HttpResponse(template.render())

# def user_panel(request):  # in bayad be jaye dorost bere.
#     template = loader.get_template("user_panel.html")
#     return HttpResponse(template.render())
