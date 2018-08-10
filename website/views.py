from apps.customer.models import Customer
from apps.main.models import GenUser
from apps.manager.models import Manager


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
    def manager_context(context, manager):
        if(manager):
            Compilation.user_context(context, manager)
            context['euro_credit'] = manager.company_euro_cent_credit / 100
            context['dollar_credit'] = manager.company_dollar_cent_credit / 100
            context['rial_credit'] = manager.company_rial_credit

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
    def get_customer_context_data(context, username):
        # returns context and customer
        customer = Customer.objects.filter(username=username).first()
        return (Compilation.customer_context(context, customer), customer)

    def get_user_context_data(context, username):
        # returns context and user
        user = GenUser.objects.filter(username=username).first()
        return (Compilation.user_context(context, user), user)
