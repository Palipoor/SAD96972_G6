"""backend URL Configuration

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/2.0/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  path('', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  path('', Home.as_view(), name='home')
Including another URLconf
    1. Import the include() function: from django.urls import include, path
    2. Add a URL to urlpatterns:  path('blog/', include('blog.urls'))
"""
from django.contrib import admin
from django.urls import path

from apps.main.views import WalletView, NotificationsView
from apps.manager.views import ManagerPasswordChangeView, CompanySettingsView, \
    ManagerDashboardView, EmployeeListView, CustomersListView, EmployeeDetailsForManager, \
    CustomerDetailsForManager, TransactionDetailsView

urlpatterns = [
    path('dashboard/', ManagerDashboardView.as_view(), name='dashboard'),
    path('change_password', ManagerPasswordChangeView.as_view(), name='change_password'),
    path('settings', CompanySettingsView.as_view(), name='settings'),
    path('<pk>_transaction_details', TransactionDetailsView.as_view(), name='transaction_details'),
    path('employee_users/', EmployeeListView.as_view(), name='employee_users'),
    path('customer_users/', CustomersListView.as_view(), name='customer_users'),
    path('<user_id>_customer_details/', CustomerDetailsForManager.as_view(), name='customer_details'),
    path('<employee_id>_employee_details/', EmployeeDetailsForManager.as_view(), name='employee_details'),
    path('<currency>_wallet/', WalletView.as_view(user_type="Manager"), name='wallet'),
    path('notifications/', NotificationsView.as_view(), name='notifications'),

]
