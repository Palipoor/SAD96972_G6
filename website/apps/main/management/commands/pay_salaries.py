from django.core.management.base import BaseCommand, CommandError
from apps.employee.models import Employee
from apps.manager.models import Manager

class Command(BaseCommand):
    def add_arguments(self, parser):
	    pass

    def handle(self, *args, **options):
        manager = Manager.get_manager();
        employees = Employee.objects.filter(is_active = True)
        for employee in employees:
            manager.rial_credit -= employee.current_salary