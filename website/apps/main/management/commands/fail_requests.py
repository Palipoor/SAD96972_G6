from django.core.management.base import BaseCommand, CommandError
from apps.customer.models import Request
import datetime


class Command(BaseCommand):
    def add_arguments(self, parser):
        pass

    def handle(self, *args, **options):
        pending_requests = Request.objects.filter(status=2)
        now = datetime.datetime.now(datetime.timezone.utc)
        for request in pending_requests:
            request_time = request.request_time
            delta = now - request_time
            one_day = datetime.timedelta(hours = 24)
            if delta >= one_day:
                request.fail()
