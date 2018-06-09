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
from apps.customer.views import CustomerPasswordChangeView, CustomerSettingsView, \
    ReverseChargeCreationView, ForeignPaymentCreationView, \
    ApplicationFeeCreationView, AnonymousPaymentCreationView, TransactionsListView, CustomerDashboardView
from apps.main.views import WalletView, TransactionDetailsView, NotificationsView

urlpatterns = [
    path('dashboard', CustomerDashboardView.as_view(), name='dashboard'),
    path('change_password', CustomerPasswordChangeView.as_view(), name='change_password'),
    path('settings', CustomerSettingsView.as_view(), name='settings'),
    path('mytransactions', TransactionsListView.as_view(), name='mytransactions'),
    path('<id>_transaction_details', TransactionDetailsView.as_view, name='transaction_details'),
    path('settings/', CustomerSettingsView.as_view, name='settings'),
    path('reverse_charge/', ReverseChargeCreationView.as_view, name='reverse_charge'),
    path('foreign_payment/', ForeignPaymentCreationView.as_view, name='foreign_payment'),
    path('application_fee/', ApplicationFeeCreationView.as_view, name='application_fee'),
    path('anonymous_payment/', AnonymousPaymentCreationView.as_view, name='anonymous_payment'),
    path('<currency>_wallet/', WalletView.as_view, name='wallet'),
    path('notifications/', NotificationsView.as_view, name='notifications'),
]
