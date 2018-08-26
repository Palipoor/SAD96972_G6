from django import forms
from django.contrib import messages
from django.contrib.auth.forms import AuthenticationForm
from django.contrib.auth.mixins import LoginRequiredMixin, PermissionRequiredMixin
from django.contrib.auth.views import LoginView
from django.core.mail import send_mail, BadHeaderError
from django.http import HttpResponse, HttpResponseForbidden, HttpResponseRedirect
from django.template import loader
# Create your views here.
from django.urls import reverse_lazy
from django.views import generic
from django.views.generic import ListView, DetailView, FormView, RedirectView
from django.views.generic.edit import FormMixin, ProcessFormView
from django.shortcuts import redirect, render
from braces.views import GroupRequiredMixin
from django.contrib.auth import logout
from django.views.generic import TemplateView

from apps.main.models import Wallet_User, Notification, GenUser
from apps.main.MultiForm import MultiFormsView
from views import Compilation
from apps.main.Forms import WalletChargeForm, SignUpForm, ContactForm, ConvertForm
from django.utils.decorators import method_decorator

from django.views.decorators.csrf import csrf_exempt
from apps.customer.models import Customer, Request
from apps.manager.models import Manager
from apps.manager.Forms import ReviewForm
from apps.customer.models import Transactions
import lxml.etree
import lxml.html
import requests
from django import http
from utils.currency_utils import Transactions


# returns a dictionary containing dollar and euro prices
def get_prices():
   exchange_matrix = Transactions.exhchange_matrix
   prices = {}
   prices.update({'euro': exchange_matrix[2][0]/10000})
   prices.update({'dollar': exchange_matrix[1][0]/10000})

   return prices

class IsLoggedInView(LoginRequiredMixin):
    login_url = '/login'
    permission_denied_message = 'شما هنوز وارد سیستم نشده اید.'


class IsCustomer(GroupRequiredMixin):
    group_required = u"customer"
    raise_exception = True


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
            context, user = Compilation.get_manager_context_data(context, self.request.user.username)
        context = Compilation.get_wallet_requests(context, self.request.user.username, Transactions.currency_to_num(self.kwargs['currency']))
        context['credit'] = context[self.kwargs['currency'] + "_credit"]
        context.update({'dollar': 100000, 'euro': 10000})
        context = Compilation.get_last_request_and_transaction_id(context)
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

    def form_valid(self, form):
        form.update_db()
        return super().form_valid(form)


class DetailsView(IsLoggedInView, DetailView):
    ""  # todo undone


class NotificationsView(IsLoggedInView, TemplateView):
    def get_context_data(self, **kwargs):
        context = super(NotificationsView, self).get_context_data(**kwargs)
        context['object_list'] = Notification.objects.filter(user__username=self.user.username).order_by('-sent_date')
        for notif in context['object_list']:
            notif.seen = True
            notif.save()
        context = Compilation.get_last_request_and_transaction_id(context)
        return context

    def dispatch(self, request, *args, **kwargs):
        self.user = request.user
        isManager = self.user.groups.filter(name='manager').exists()
        isCustomer = self.user.groups.filter(name='customer').exists()

        if isManager:
            self.template_name = 'manager/notifications.html'
        elif isCustomer:
            self.template_name = 'customer/notifications.html'
        else:
            self.template_name = 'manager/notifications.html'

        return render(request, self.template_name, self.get_context_data(**kwargs))


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


class LandingPageView(FormView):
    template_name = 'main/index.html'
    contact_form_class = ContactForm
    convert_form_class = ConvertForm
    form_class = contact_form_class

    def get_context_data(self, **kwargs):
        context = super(LandingPageView, self).get_context_data(**kwargs)
        if 'contact_form' not in context:
            context['contact_form'] = self.contact_form_class()
        if 'convert_form' not in context:
            context['convert_form'] = self.convert_form_class()
        price = get_prices()
        context.update(price)
        requests = Compilation.get_all_requests({})
        context['how_many_requests'] = len(requests['requests'])
        users = GenUser.objects.all()
        context['how_many_users'] = len(users)
        context = Compilation.get_last_request_and_transaction_id(context)
        return context

    def post(self, request, *args, **kwargs):
        if 'contact_form' in request.POST:
            form_class = self.contact_form_class
            form_name = 'contact_form'
        else:
            form_class = self.convert_form_class
            form_name = 'convert_form'

        form = self.get_form(form_class)

        if form.is_valid():
            return self.form_valid(form)
        else:
            return self.form_invalid(**{form_name: form})

    def form_valid(self, form):
        if isinstance(form, ContactForm):
            subject = form.cleaned_data['subject']
            from_email = form.cleaned_data['email']
            message = form.cleaned_data['message']
            success = False
            try:
                send_mail(subject, message, from_email, ['palipoor976@gmail.com'])
                success = True
            except BadHeaderError:
                print('ay baba ay baba ay baba')
                return HttpResponse('Invalid header found.')
            context = self.get_context_data()  # todo man balad nistam ino. dorost bayad beshe.
            if success:
                print('succeesssssssssssssssss')
                context.update({'success': 'پیام شما با موفقیت ارسال شد.'})
            return render(self.request, 'main/index.html', context)

        else:
            prices = get_prices()
            conversion_type = form.cleaned_data['conversion_type']
            amount = form.cleaned_data['amount']
            dollar_price = prices['dollar']
            euro_price = prices['euro']
            dest_currency = ''
            if conversion_type == 'dollar2rial':
                converted = dollar_price * amount
                dest_currency = 'ده هزار ریالی'
            elif conversion_type == 'rial2dollar':
                converted = amount / dollar_price
                dest_currency = 'دلار'
            elif conversion_type == 'euro2rial':
                converted = euro_price * amount
                dest_currency = 'ده هزار ریالی'
            elif conversion_type == 'rial2euro':
                converted = amount / euro_price
                dest_currency = 'یورو'
            elif conversion_type == 'euro2dollar':
                converted = amount * euro_price / dollar_price
                dest_currency = 'دلار'
            else:
                converted = amount * dollar_price / euro_price
                dest_currency = 'یورو'

            context = self.get_context_data()
            context['result'] = converted
            context.update({'currency': dest_currency})
            return render(self.request, 'main/index.html', context)  # todo bere bakhshe finance!

    def form_invalid(self, **kwargs):
        return self.render_to_response(self.get_context_data(**kwargs))


def register_success(request):
    template = loader.get_template("main/success.html")
    return HttpResponse(template.render())


def log_out(request):
    logout(request)
    return HttpResponseRedirect(reverse_lazy('main:home'))

# def user_panel(request):  # in bayad be jaye dorost bere.
#     template = loader.get_template("user_panel.html")
#     return HttpResponse(template.render())


class TransactionDetailsView(DetailView):

    def get_context_data(self, **kwargs):
        context = super(TransactionDetailsView, self).get_context_data(**kwargs)
        context = Compilation.get_last_request_and_transaction_id(context)
        all_fields = context["object"]._meta.get_fields()
        labels = context["object"].labels
        selected_fields = []
        selected_fields.append(("درخواست دهنده", getattr(context["object"], "creator").username))
        selected_fields.append(("نوع", getattr(context["object"], "type")))
        selected_fields.append(("وضعیت", Transactions.get_persian_status(getattr(context["object"], "status"))))
        selected_fields.append(("مبلغ", str(getattr(context["object"], "amount")) + Transactions.get_currency_symbol(getattr(context["object"], "source_wallet"))))
        selected_fields.append(("مبلغ به همراه هزینه تراکنش", str(getattr(context["object"], "amount")*(1+getattr(context["object"], "profitRate")) ) + Transactions.get_currency_symbol(getattr(context["object"], "source_wallet"))))
        for field in all_fields:
            if (field.name in labels):
                selected_fields.append((labels[field.name], getattr(context["object"], field.name)))
        context["fields"] = selected_fields
        return context

    def get_queryset(self):
        return Request.objects.filter(id=self.kwargs['pk'])


class TransactionDetailsViewForStaff(FormMixin, TransactionDetailsView):

    form_class = ReviewForm

    def get_context_data(self, **kwargs):
        context = super(TransactionDetailsViewForStaff, self).get_context_data(**kwargs)
        context.update({'notifications': Notification.objects.filter(user__username=self.request.user.username, seen=False).order_by('-sent_date')})
        context = Compilation.get_last_request_and_transaction_id(context)
        context['form'] = self.get_form()
        return context

    def get_form_kwargs(self):
        kwargs = super(TransactionDetailsViewForStaff, self).get_form_kwargs()
        kwargs['user'] = GenUser.objects.get(username=self.request.user)
        kwargs['transaction_id'] = self.kwargs['pk']
        return kwargs


    def post(self, request, *args, **kwargs):
        self.object = self.get_object()
        form = self.get_form()
        if form.is_valid():
            messages.add_message(
            self.request, messages.SUCCESS, "بررسی تراکنش با موفقیت انجام شد.")
            return self.form_valid(form)
        else:
            print(form.errors)
            return self.form_invalid(form)



    def form_valid(self, form):
        form.update_db()
        messages.add_message(
            self.request, messages.SUCCESS, 'بررسی تراکنش با موفقیت انجام شد.')
        return http.HttpResponseRedirect(self.get_success_url())
