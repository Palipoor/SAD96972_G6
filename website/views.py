from apps.customer.models import Customer
from apps.main.models import GenUser
from apps.employee.models import Employee
from apps.manager.models import Manager
from apps.customer.models import Request


class Compilation():
    @staticmethod
    def customer_context(context, customer):
        if(customer):
            Compilation.user_context(context, customer)
            context['euro_credit'] = customer.euro_cent_credit
            context['dollar_credit'] = customer.dollar_cent_credit
            context['rial_credit'] = customer.rial_credit
        return context

    @staticmethod
    def employee_context(context, employee):
        if(employee):
            Compilation.user_context(context, employee)
        return context

    @staticmethod
    def manager_context(context, manager):
        if(manager):
            Compilation.user_context(context, manager)
            context['euro_credit'] = manager.euro_cent_credit
            context['dollar_credit'] = manager.dollar_cent_credit
            context['rial_credit'] = manager.rial_credit
        return context

    @staticmethod
    def user_context(context, user):
        if(user):
            context['first_name'] = user.persian_first_name
            context['last_name'] = user.persian_last_name
        return context

    @staticmethod
    def get_manager_context_data(context, username):
        # returns context and manager
        manager = Manager.objects.filter(username=username).first()
        return (Compilation.manager_context(context, manager), manager)

    @staticmethod
    def get_employee_context_data(context, username):
        # returns context and customer
        employee = Employee.objects.filter(username=username).first()
        return (Compilation.employee_context(context, employee), employee)

    @staticmethod
    def get_customer_context_data(context, username):
        # returns context and customer
        customer = Customer.objects.filter(username=username).first()
        return (Compilation.customer_context(context, customer), customer)

    def get_user_context_data(context, username):
        # returns context and user
        user = GenUser.objects.filter(username=username).first()
        return (Compilation.user_context(context, user), user)

    def get_wallet_requests(context, user_name, wallet):
        # get requests regarding a wallet of the user. if wallet equals -1 all wallets are used.
        user = GenUser.objects.get(username=user_name)
        if (wallet == -1):
            context['requests'] = Request.objects.filter(source_user=user).order_by('-request_time') | Request.objects.filter(dest_user=user).order_by('-request_time')
        else:
            context['requests'] = Request.objects.filter(source_user=user, source_wallet=wallet).order_by('-request_time') | Request.objects.filter(dest_user=user, dest_wallet=wallet).order_by('-request_time')
        return context

    def get_all_requests(context):
        context['requests'] = Request.objects.all().order_by('-request_time')
        return context
    
    def get_reported_requests(context):
        context['reported_requests'] = Request.objects.filter(status = 4)
        return context

    def get_last_request_and_transaction_id(context):
        context['last_request_id'] = str(Request.objects.filter(status=2).last().id)
        context['last_transaction_id'] = str(Request.objects.last().id)
        return context
