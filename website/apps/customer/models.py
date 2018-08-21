from apps.main.models import GenUser, Wallet_User
from django.db import models
from polymorphic.models import PolymorphicModel
from django.contrib.auth.models import Group
from utils.currency_utils import Transactions
from apps.employee.models import EmployeeReview
from apps.manager.models import Manager
import traceback


# Create your models here.


class Customer(Wallet_User):

    contact_way = models.IntegerField(choices = ((0,'ایمیل'), (1,'پیامک')) , default = 0)

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
    # all users can be Nonewhich means the dont exists and therefore can be ignored.
    # who pays
    source_user = models.ForeignKey('main.Wallet_User', on_delete=models.DO_NOTHING, related_name='source_user', null=True, blank=True)
    source_wallet = models.IntegerField(choices=currencies, default=0, blank=True)
    # who recieves and possibly redirects
    dest_user = models.ForeignKey('main.Wallet_User', on_delete=models.DO_NOTHING, related_name='dest_user', null=True, blank=True)
    dest_wallet = models.IntegerField(choices=currencies, default=0, blank=True)
    # who recieves
    final_user = models.ForeignKey('main.Wallet_User', on_delete=models.DO_NOTHING, related_name='final_user', null=True, blank=True)
    fianl_wallet = models.IntegerField(choices=currencies, default=0, blank=True)
    # amount of payment in rial or cents.
    amount = models.FloatField(null=False, blank=True)
    request_time = models.DateTimeField(auto_now_add=True, blank=True)
    description = models.CharField(max_length=500, blank=True)
    # defaul status varies between children. some may be accepted since creation.
    status = models.IntegerField(choices=statuses, default=2, blank=True)
    profitRate = models.FloatField(default=0, blank=True)
    # if not specified will be determind using utils.
    exchange_rate = models.FloatField(null=True, blank=True)

    def __init__(self, *args, **kwargs):
        super().__init__(*args, **kwargs)
        self.excps = []
        if (not self.exchange_rate):
            self.exchange_rate = Transactions.get_exchange_rate(self.source_wallet, self.dest_wallet)
        # print('in iniit')
        # print(self.dest_user.dollar_cent_credit)
        self.pay()
        self.recieve()
        # print(self.dest_user.dollar_cent_credit)
        # print('end')

    def save(self, *args, **kwargs):
        # if it is being saved for the first time does the payment and sets status to default. If exchange rate is nul computes it.
        if not self.pk:
            self.set_status()
            self.set_profitRate()
            try:
                self.dest_user.save()
            except Exception:
                print(traceback.format_exc())
            try:
                self.source_user.save()
            except Exception:
                print(traceback.format_exc())
        super(Request, self).save(*args, **kwargs)

    def reject(self):
        # only works if the current status is pending or reported
        if (self.status == 2 or self.status == 4):
            reject = self.create_reverse_request()
            reject.save()
            # sets the status to rejected
            self.status = 1
        else:
            self.excps += ['تراکنش در شرایطی که بتواند رد شود نیست.']
        return None

    def accept(self):
        # only works if the current status is pending or reported
        if (not self.status == 2 and not self.status == 4):
            self.excps += ['تراکنش در شرایطی که بتواند تایید شود نیست.']
        self.status = 0
        return None

    def report(self):
        # only works if the current status is pending
        if (not self.status == 2):
            self.excps += ['تراکنش در شرایطی که بتواند گزارش شود نیست.']
        self.status = 4
        return None

    def fail(self):
        # only works if the current status is pending
        if (self.status == 2):
            reject = self.create_reverse_request()
            reject.save()
            self.status = 3
        else:
            self.excps += ['تراکنش در شرایطی که بتواند فیل شود نیست.']
        return None

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
            # self.source_user.save()
        except Exception:
            print(traceback.format_exc())

    def recieve(self):
        # what dest user revieves. we assume that dest user always recieves the profit.
        try:
            self.dest_user
            # print("goodbye")
            # print(self.dest_wallet == 1)
            if (self.dest_wallet == 0):
                # print('zero wallet')
                self.dest_user.rial_credit += self.amount*(1+self.profitRate)*self.exchange_rate
            elif (self.dest_wallet == 1):
                # print('one wallet')
                # print(self.amount * (1 + self.profitRate) * self.exchange_rate)
                # print(self.dest_user.dollar_cent_credit)
                self.dest_user.dollar_cent_credit += self.amount * (1 + self.profitRate) * self.exchange_rate
                # print(self.dest_user.dollar_cent_credit)
            elif (self.dest_wallet == 2):
                self.dest_user.euro_cent_credit += self.amount * (1 + self.profitRate) * self.exchange_rate
            # self.dest_user.save()
        except Exception:
            print(traceback.format_exc())

    def exception_texts(self):
        try:
            self.excps += self.source_user.exception_texts()
            print('source user')
        except Exception:
            print(traceback.format_exc())
        try:
            self.excps += self.dest_user.exception_texts()
            print('source user')
        except Exception:
            print(traceback.format_exc())
        try:
            self.excps += self.final_user.exception_texts()
            print('source user')
        except Exception:
            print(traceback.format_exc())
        return self.excps

    def create_reverse_request(self):
        # created the transaction for rejecting current transaction.
        # reject = Reverse_Request(source_user=self.dest_user,
        #                          source_wallet=dest_wallet,
        #                          dest_user=self.source_user,
        #                          dest_wallet=self.source_wallet,
        #                          amount=self.amount*(1 + self.profitRate)*exchange_rate,
        #                          profitRate=0,
        #                          exchange_rate=1./exchange_rate,
        #                          related_request=self
        #                          )
        reject = Reverse_Request(source_wallet=self.dest_wallet,
                                 dest_wallet=self.source_wallet,
                                 amount=self.amount*(1 + self.profitRate)*self.exchange_rate,
                                 profitRate=0,
                                 exchange_rate=1./self.exchange_rate,
                                 reference=self
                                 )
        try:
            reject.dest_user = self.source_user
        except:
            pass

        try:
            reject.source_user = self.dest_user
        except:
            pass

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

    def __init__(self, *args, **kwargs):
        temp = super().__init__(*args, **kwargs)
        if not self.pk:
            manager = Manager.get_manager()
            self.dest_user = manager
            self.dest_wallet = kwargs['source_wallet']
        return temp

    def save(self, *args, **kwargs):
        super(ForeignTrans, self).save(*args, **kwargs)

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
