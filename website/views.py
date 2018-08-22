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
            context['euro_credit'] = customer.euro_cent_credit / 100
            context['dollar_credit'] = customer.dollar_cent_credit / 100
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
            context['euro_credit'] = manager.euro_cent_credit / 100
            context['dollar_credit'] = manager.dollar_cent_credit / 100
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
        customer = Customer.objects.get(username=user_name)
        if (wallet == -1):
            context['requests'] = Request.objects.filter(source_user=customer) | Request.objects.filter(dest_user=customer)
        else:
            context['requests'] = Request.objects.filter(source_user=customer, source_wallet=wallet) | Request.objects.filter(dest_user=customer, dest_wallet=wallet)
        return context

    def get_all_requests(context):
        context['requests'] = Request.objects.all()
        return context
