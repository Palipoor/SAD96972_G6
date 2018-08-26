# from apps.customer.models import Customer
from apps.main.models import Notification
from django.apps import apps
from django.core.mail import send_mail, BadHeaderError
from kavenegar import *


def send_notification(username, message):
    notification = Notification(message=message, username=username)
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
        api = KavenegarAPI('656A434D654A78314964374C64645272683064576D54794861774E2F48495473')
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
