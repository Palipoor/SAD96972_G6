from apps.main.models import GenUser, Wallet_User
from django.db import models
from polymorphic.models import PolymorphicModel
from django.contrib.auth.models import Group
from utils.currency_utils import Transactions
from apps.employee.models import EmployeeReview
from apps.manager.models import Manager

# Create your models here.


class Customer(Wallet_User):

    def __init__(self, *args, **kwargs):
        super(Customer, self).__init__(*args, **kwargs)
        self.user_type = 0

    def save(self, *args, **kwargs):
        super(Customer, self).save(*args, **kwargs)
        customer_group = Group.objects.get(name='customer')
        customer_group.user_set.add(self)
        customer_group = Group.objects.get(name='wallet_user')
        customer_group.user_set.add(self)


class Request(PolymorphicModel):
    # all financial transactions are children of this.
    statuses = Transactions.request_types_json
    currencies = Transactions.num_to_currency_json.items()
    print(currencies)
    # all users can be null which means the dont exists and therefore can be ignored.
    # who pays
    source_user = models.ForeignKey('main.Wallet_User', on_delete=models.DO_NOTHING, related_name='source_user', null=True)
    source_wallet = models.IntegerField(choices=currencies, default=0)
    # who recieves and possibly redirects
    dest_user = models.ForeignKey('main.Wallet_User', on_delete=models.DO_NOTHING, related_name='dest_user', null=True)
    dest_wallet = models.IntegerField(choices=currencies, default=0)
    # who recieves
    final_user = models.ForeignKey('main.Wallet_User', on_delete=models.DO_NOTHING, related_name='final_user', null=True)
    fianl_wallet = models.IntegerField(choices=currencies, default=0)
    # amount of payment in rial or cents.
    amount = models.FloatField(null=False)
    request_time = models.DateTimeField(auto_now_add=True)
    description = models.CharField(max_length=500)
    # defaul status varies between children. some may be accepted since creation.
    status = models.IntegerField(choices=statuses, default=2)
    profitRate = models.FloatField(default=0)
    # if not specified will be determind using utils.
    exchange_rate = models.FloatField(null=True)

    def save(self, *args, **kwargs):
        # if it is being saved for the first time does the payment and sets status to default. If exchange rate is nul computes it.
        if not self.pk:
            if (not self.exchange_rate):
                self.exchange_rate = Transactions.get_exchange_rate(self.source_wallet, self.dest_wallet)
            self.pay()
            self.recieve()
            self.set_status()
            self.set_profitRate()
        super(Request, self).save(*args, **kwargs)

    def reject(self):
        # only works if the current status is pending or reported
        if (self.status == 2 or self.status == 4):
            reject = self.create_reverse_request()
            reject.save()
            # sets the status to rejected
            self.status = 1
        return null

    def accept(self):
        # only works if the current status is pending or reported
        if (self.status == 2 or self.status == 4):
            self.status = 0
        return null

    def report(self):
        # only works if the current status is pending
        if (self.status == 2):
            self.status = 4
        return null

    def fail(self):
        # only works if the current status is pending
        if (self.status == 2):
            reject = self.create_reverse_request()
            reject.save()
            self.status = 3
        return null

    def set_status(self):
        # for determining default status of request. Default status is pending.
        self.status = 2

    def set_profitRate(self):
        # for determining default profitRate of request. Default status is pending.
        self.profitRate = 0.05

    def pay(self):
        # what source user has to pay
        try:
            self.source_user
            if (self.source_wallet == 0):
                self.source_user.rial_credit -= self.amount*(1+self.profitRate)
            elif (self.source_wallet == 1):
                self.source_user.dollar_cent_credit -= self.amount*(1+self.profitRate)
            elif (self.source_wallet == 2):
                self.source_user.dollar_cent_credit -= self.amount * (1 + self.profitRate)
            self.source_user.save()
        except:
            pass

    def recieve(self):
        # what dest user revieves. we assume that dest user always recieves the profit.
        try:
            self.dest_user
            print("goodbye")
            print(self.dest_wallet)
            if (self.dest_wallet == 0):
                self.dest_user.rial_credit += self.amount*(1+self.profitRate)*self.exchange_rate
            elif (self.dest_wallet == 1):
                print(self.dest_user.dollar_cent_credit)
                self.dest_user.dollar_cent_credit += self.amount * (1 + self.profitRate) * self.exchange_rate
                print(self.dest_user.dollar_cent_credit)
            elif (self.dest_wallet == 2):
                self.dest_user.euro_cent_credit += self.amount * (1 + self.profitRate) * self.exchange_rate
            self.dest_user.save()
        except:
            pass

    def create_reverse_request(self):
        # created the transaction for rejecting current transaction.
        reject = Reverse_Request(source_user=self.dest_user,
                                 source_wallet=dest_wallet,
                                 dest_user=self.source_user,
                                 dest_wallet=self.source_wallet,
                                 amount=self.amount*(1 + self.profitRate)*exchange_rate,
                                 profitRate=0,
                                 exchange_rate=1./exchange_rate,
                                 related_request=self
                                 )
        return reject


class Reverse_Request(Request):
    # type of request when rejecting or failing a transatcion. Arguments similar to request.
    # reference to rejected transaction.
    # it is unique foreign key not one to one
    reference = models.ForeignKey(Request,  on_delete=models.DO_NOTHING, unique=True, related_name='related_request')

    def set_status(self):
        self.status = 0

    def set_profitRate(self):
        self.profitRate = 0


class Charge(Request):
    # Only needs destination user and wallet and amount as argument.
    def set_status(self):
        self.status = 0

    def set_profitRate(self):
        self.profitRate = 0


class Exchange(Request):
    # Only needs source user and wallet, amount and destination wallet as argument.
    # def __init__(self, *args, **kwargs):
    #     kwargs['dest_user'] = kwargs["source_user"]
    #     super(Exchange, self).__init__(self, *args, **kwargs)

    def set_status(self):
        self.status = 0

    def set_profitRate(self):
        self.profitRate = 0


class LangTest(Request):
    username = models.CharField(max_length=100, null=False)
    password = models.CharField(max_length=50, null=False)


class IBT(LangTest):
    test_center_name = models.CharField(max_length=100, null=False)
    test_center_code = models.CharField(max_length=50, null=False)
    city = models.CharField(max_length=100, null=False)
    country = models.CharField(max_length=100, null=False)
    date = models.DateTimeField(null=False)


class TOFEL(IBT):
    id_types = (
        (0, 'Passport'),
        (1, 'National ID'),

    )
    reasons = (
        (0, 'blah'),
        (1, 'blah'),
        (2, 'blah'),
        (3, 'blah'),
        (4, 'blah'),
        (5, 'blah'),
        (6, 'blah'),
        (7, 'blah'),
        (8, 'blah'),
        (9, 'blah'),

    )
    countries_for_studying = (
        (0, 'blah'),
        (1, 'blah'),
        (2, 'blah'),
        (3, 'blah'),
        (4, 'blah'),
        (5, 'blah'),
        (6, 'blah'),
        (7, 'blah'),
        (8, 'blah'),
        (9, 'blah'),
    )
    reason = models.IntegerField(choices=reasons, null=False)  # handle several reasons
    country_for_study = models.IntegerField(choices=countries_for_studying, null=False)  # handle several country
    id_type = models.IntegerField(choices=id_types, null=False)
    id_number = models.CharField(max_length=20)


class GRE(IBT):
    citizenships = (
        (0, 'blah'),
        (1, 'blah'),
        (2, 'blah'),
    )
    statuses = (
        (0, 'blah'),
        (1, 'blah'),
        (2, 'blah'),
        (3, 'blah'),
        (4, 'blah'),
        (5, 'blah'),
        (6, 'blah'),
        (7, 'blah'),

    )
    major_filed_code = models.CharField(max_length=50, null=False)
    major_filed_name = models.CharField(max_length=50, null=False)
    citizenship = models.IntegerField(choices=citizenships, null=False)
    educational_status = models.IntegerField(choices=statuses, null=False)


class UniversityTrans(Request):
    types = (
        (0, 'application fee'),
        (1, 'deposit fee')
    )
    type = models.IntegerField(choices=types, null=False)
    university_name = models.CharField(max_length=50, null=False)
    link = models.URLField(null=False)
    username = models.CharField(max_length=100, null=False)
    password = models.CharField(max_length=50, null=False)
    guide = models.CharField(max_length=1000, null=False)


class ForeignTrans(Request):
    # Only needs source user and wallet, amount, account number and bank name as arguments.
    account_number = models.CharField(max_length=20, null=False)
    bank_name = models.CharField(max_length=50, null=False)

    def save(self, *args, **kwargs):
        manager = Manager.get_manager()
        super(ForeignTrans, self).save(*args, dest_user=manager, dest_wallet=kwargs['source_wallet'], **kwargs)

    def set_status(self):
        self.status = 2

    def set_profitRate(self):
        self.profitRate = 0.05


class InternalTrans(Request):
    account_number = models.CharField(max_length=20)
    bank_name = models.CharField(max_length=50)


class UnknownTrans(Request):
    account_number = models.CharField(max_length=20, null=False)
    bank_name = models.CharField(max_length=50, null=False)
    email = models.EmailField()
    phone_number = models.CharField(max_length=20)

#
# class CustomerWalletTransfer(models.Model):
#     wallets = (
#         (0, 'rial wallet'),
#         (1, 'dollar wallet'),
#         (2, 'euro wallet'),
#     )
#     source = models.IntegerField(choices=wallets, null=False)
#     destination = models.IntegerField(choices=wallets, null=False)
#     customer = models.ForeignKey('Customer', on_delete=models.DO_NOTHING, null=False)
#     transfer_time = models.DateTimeField(null=False)
#     profitًRate = models.FloatField(null=False)
#     source_deposit_before = models.FloatField(null=False)
#     source_deposit_after = models.FloatField(null=False)
#     destination_deposit_before = models.FloatField(null=False)
#     destination_deposit_after = models.FloatField(null=False)
#     rial_deposit_before_profit = models.FloatField(null=False)
#     rial_deposit_after_profit = models.FloatField(null=False)
#
#
# class CustomerWalletCharge(models.Model):
#     customer = models.ForeignKey('Customer', on_delete=models.DO_NOTHING, null=False)
#     charge_time = models.DateTimeField(null=False)
#     deposit_before = models.FloatField(null=False)
#     deposit_after = models.FloatField(null=False)
#
#
# class CostumerWalletChanges(models.Model):
#         types = (
#             (1, 'request submit'),
#             (2, 'request profit'),
#             (3, 'request failure'),  # rejected or failed
#             (4, 'request failure profit'),
#         )
#         wallets = (
#             (0, 'rial'),
#             (1, 'dollar'),
#             (2, 'euro'),
#         )
#         type = models.IntegerField(choices=types, null=False)
#         wallet = models.IntegerField(choices=wallets, null=False)
#         request = models.ForeignKey('Request', on_delete=models.CASCADE, null=False)
#         change_time = models.DateTimeField(null=False)
#         deposit_before = models.FloatField(null=False)
#         deposit_after = models.FloatField(null=False)
#         rial_deposit_before_profit = models.FloatField(null=False)
#         rial_deposit_after_profit = models.FloatField(null=False)
