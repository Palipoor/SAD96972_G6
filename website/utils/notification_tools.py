from apps.main.models import Notification


def send_notification(username, message):
    notification = Notification.create(message=message, username=username)
    notification.save()


def send_email(email, message,  subject):
    try:
        send_mail(subject, message, 'info@sad.com' , email)
        return true
    except BadHeaderError:
        return false