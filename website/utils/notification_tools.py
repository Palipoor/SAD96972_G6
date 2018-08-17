from apps.main.models import Notification
from django.core.mail import send_mail, BadHeaderError


def send_notification(username, message):
    notification = Notification.create(message=message, username=username)
    notification.save()


def send_email(email, message,  subject):
    try:
        send_mail(subject, message, 'info@sad.com' , [email])
        print('sent!!')
        return True
    except BadHeaderError as e:
        print(e)
        return False