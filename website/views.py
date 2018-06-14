from apps.customer.models import Customer
from apps.main.models import GenUser
class Compilation():
    @staticmethod
    def customer_context(context, customer):
        if(customer):
            Compilation.user_context(context,customer)
            context['euro_credit'] = customer.rial_credit
            context['dollar_credit'] = customer.dollar_cent_credit / 100
            context['rial_credit'] = customer.rial_credit / 100
        return context

    @staticmethod
    def user_context(context, user):
        if(user):
            context['first_name'] = user.persian_first_name
            context['last_name'] = user.persian_last_name
        return context

    @staticmethod
    def get_customer_context_data(context, username):
        #returns context and customer
        customer = Customer.objects.filter(username = username).first()
        return (Compilation.customer_context(context,customer), customer)

    def get_user_context_data(context, username):
        #returns context and user
        user = GenUser.objects.filter(username = username).first()
        return (Compilation.user_context(context,user), user)