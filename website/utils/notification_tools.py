# from apps.customer.models import Customer
from django.apps import apps
from django.core.mail import send_mail, BadHeaderError
from kavenegar import *


def send_notification(username, message):
    notification = apps.get_model("main", "Notification").create(message=message, username=username)
    notification.save()


def send_critical_credit_notification(username):
    notification = apps.get_model("main", "Critical_Credit_Notification").get_or_create(username=username)
    print(notification)
    notification.save()


def send_email(email, message,  subject):
    try:
        send_mail(subject, message, 'info@sad.com', [email])
        print('sent!!')
        return True
    except BadHeaderError as e:
        print(e)
        return False


def send_text(number, message, subject):
    try:
        api = KavenegarAPI('634346565A42563850416D5A6D41517631317959563377437048375258394449')
        params = {
            'sender': '',
            'receptor': number,
            'message': message,
        }
        response = api.sms_send(params)
        print(response)
    except APIException as e:
        print(e)
    except HTTPException as e:
        print(e)


def notify(customer_username, message, subject):
    customer = apps.get_model('customer', "Customer").objects.get(username=username)
    if customer.contact_way == 0:
        send_email(customer.email, subject)
    else:
        send_text(str(customer.phone_number), subject)
