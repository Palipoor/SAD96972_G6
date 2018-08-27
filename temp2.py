from django.core.management.base import BaseCommand, CommandError
from customer.models import Request
import datetime

class Command(BaseCommand):
    help = 'Pays salaries"

    def add_arguments(self, parser):
	pass

    def handle(self, *args, **options):
	pending_requests = Requests.filter(status = 2)
	now = datetime.datetime.now().time()
	for request in pending_requests:
		request_time = request.request_time
		hour_delta = now - request_time
		one_day = datetime.time(24,0)
		if hour_delta >= one_day:
			request.fail()
