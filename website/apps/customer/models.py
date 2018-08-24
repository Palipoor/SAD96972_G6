from apps.main.models import GenUser, Wallet_User
from django.db import models
from polymorphic.models import PolymorphicModel
from django.contrib.auth.models import Group
from utils.currency_utils import Transactions
from apps.employee.models import EmployeeReview
from apps.manager.models import Manager
from django.forms import ValidationError
import traceback


# Create your models here.


class Customer(Wallet_User):

    contact_way = models.IntegerField(choices=((0, 'ایمیل'), (1, 'پیامک')), default=0)

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
    creator = models.ForeignKey('main.Wallet_User', on_delete=models.DO_NOTHING, related_name='creator', null=False, blank=True)
    source_user = models.ForeignKey('main.Wallet_User', on_delete=models.DO_NOTHING, related_name='source_user', null=True, blank=True)
    source_wallet = models.CharField(choices=currencies, max_length=1, default="0", blank=True)
    # who recieves and possibly redirects
    dest_user = models.ForeignKey('main.Wallet_User', on_delete=models.DO_NOTHING, related_name='dest_user', null=True, blank=True)
    dest_wallet = models.CharField(choices=currencies, max_length=1, default="0", blank=True)
    # who recieves
    final_user = models.ForeignKey('main.Wallet_User', on_delete=models.DO_NOTHING, related_name='final_user', null=True, blank=True)
    fianl_wallet = models.CharField(choices=currencies, max_length=1, default="0", blank=True)
    # amount of payment in rial or cents.
    amount = models.FloatField(null=False, blank=True)
    request_time = models.DateTimeField(auto_now_add=True, blank=True)
    description = models.CharField(max_length=500, blank=True)
    # defaul status varies between children. some may be accepted since creation.
    status = models.CharField(choices=statuses, max_length=1, default="2", blank=True)
    profitRate = models.FloatField(default=0, blank=True)
    # if not specified will be determind using utils.
    exchange_rate = models.FloatField(null=True, blank=True)
    # request type
    type = models.CharField(max_length=100, null=False)
    labels = {}

    def __init__(self, *args, **kwargs):
        super().__init__(*args, **kwargs)
        try:
            self.set_initials(*args, **kwargs)
        except Exception as identifier:
            print("problems in calling set_initials in init ")
            print(traceback.format_exc())
        self.excps = []
        if (not self.exchange_rate):
            self.exchange_rate = Transactions.get_exchange_rate(self.source_wallet, self.dest_wallet)
        # print('in iniit')
        # print(self.dest_user.dollar_cent_credit)
        # print(self.dest_user.dollar_cent_credit)
        # print('end')

    def set_initials(self, *args, **kwargs):
        # things to do after assigning creator
        pass

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
        if (self.status == "2" or self.status == "4"):
            reject = self.create_reverse_request()
            reject.save()
            # sets the status to rejected
            self.status = "1"
        else:
            self.excps += ['تراکنش در شرایطی که بتواند رد شود نیست.']
        return None

    def accept(self):
        # only works if the current status is pending or reported
        if (not self.status == "2" and not self.status == "4"):
            self.excps += ['تراکنش در شرایطی که بتواند تایید شود نیست.']
        self.status = 0
        return None

    def report(self):
        # only works if the current status is pending
        if (not self.status == "2"):
            self.excps += ['تراکنش در شرایطی که بتواند گزارش شود نیست.']
        self.status = "4"
        return None

    def fail(self):
        # only works if the current status is pending
        if (self.status == "2"):
            reject = self.create_reverse_request()
            reject.save()
            self.status = "3"
        else:
            self.excps += ['تراکنش در شرایطی که بتواند فیل شود نیست.']
        return None

    def set_status(self):
        # for determining default status of request. Default status is pending.
        self.status = "2"

    def set_profitRate(self):
        # for determining default profitRate of request. Default status is pending.
        self.profitRate = 0.05

    def pay(self):
        # what source user has to pay
        try:
            self.source_user
            if (self.source_wallet == "0"):
                self.source_user.rial_credit -= self.amount*(1+self.profitRate)
            elif (self.source_wallet == "1"):
                self.source_user.dollar_cent_credit -= self.amount*(1+self.profitRate)
            elif (self.source_wallet == "2"):
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
            if (self.dest_wallet == "0"):
                print('zero wallet')
                print(self.amount * (1 + self.profitRate) * self.exchange_rate)
                print(self.dest_user.rial_credit)
                self.dest_user.rial_credit += self.amount * (1 + self.profitRate) * self.exchange_rate
            elif (self.dest_wallet == "1"):
                # print('one wallet')
                # print(self.amount * (1 + self.profitRate) * self.exchange_rate)
                # print(self.dest_user.dollar_cent_credit)
                self.dest_user.dollar_cent_credit += self.amount * (1 + self.profitRate) * self.exchange_rate
                # print(self.dest_user.dollar_cent_credit)
            elif (self.dest_wallet == "2"):
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
            if(self.source_user != self.dest_user):
                self.excps += self.dest_user.exception_texts()
                print('source user')
        except Exception:
            print(traceback.format_exc())
        try:
            if(self.source_user != self.final_user and self.source_user != self.dest_user):
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
                                 creator=creator,
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

    def take_action(self):
        self.pay()
        self.recieve()

    def clean(self):
        flag = False
        if not self.pk:
            print("not saved")
            flag = True
        temp = super().clean()
        # raise ValidationError(message = "ss")
        if flag:
            self.take_action()
            # print(self.source_user.dollar_cent_credit)
        errors = self.exception_texts()
        if(errors):
            raise ValidationError([ValidationError(text) for text in errors])
            # raise ValidationError("d")
            # pass
        return temp


class Reverse_Request(Request):
    # type of request when rejecting or failing a transatcion. Arguments similar to request.
    # reference to rejected transaction.
    # it is unique foreign key not one to one
    reference = models.ForeignKey(Request,  on_delete=models.DO_NOTHING, unique=True, related_name='related_request')

    def set_status(self):
        self.status = "0"

    def set_profitRate(self):
        self.profitRate = 0


class Charge(Request):
    # Only needs destination user and wallet and amount as argument.

    def set_initials(self, *args, **kwargs):
        self.source_user = self.creator

    def __init__(self, *args, **kwargs):
        super(Charge, self).__init__(*args, type="Charge", **kwargs)
        self.source_user = self.creator

    def set_status(self):
        self.status = "0"

    def set_profitRate(self):
        self.profitRate = 0


class Exchange(Request):
    # Only needs source user and wallet, amount and destination wallet as argument.
    # def __init__(self, *args, **kwargs):
    #     kwargs['dest_user'] = kwargs["source_user"]
    #     super(Exchange, self).__init__(self, *args, **kwargs)

    def set_initials(self, *args, **kwargs):
        self.source_user = self.creator
        self.source_user = self.creator

    def __init__(self, *args, **kwargs):
        super(Exchange, self).__init__(*args, type="Exchange", **kwargs)

    def set_status(self):
        self.status = "0"

    def set_profitRate(self):
        self.profitRate = 0


class Account_Request(Request):
    username = models.CharField(max_length=100, null=False)
    password = models.CharField(max_length=50, null=False)
    labels = {"password": "رمز عبور",
              "username": "نام کاربری",
              }

    def set_initials(self, *args, **kwargs):
        self.source_user = self.creator
        self.dest_wallet = self.source_wallet

    def set_status(self):
        self.status = "2"

    def set_profitRate(self):
        self.profitRate = 0.005

    def __init__(self, *args, **kwargs):
        type = kwargs["type"]
        kwargs["profitRate"] = Transactions.get_profirRate(type)
        kwargs["dest_user"] = Manager.get_manager()
        super(Account_Request, self).__init__(*args, **kwargs)

class IBT(Account_Request):
    test_center_name = models.CharField(max_length=100, null=False)
    test_center_code = models.CharField(max_length=50, null=False)
    city = models.CharField(max_length=100, null=False)
    country = models.CharField(max_length=100, null=False)
    date = models.DateTimeField(null=False)
    labels = {"test_center_name": "نام مرکز آزمون",
              "test_center_code": "کد مرکز آزمون",
              "city": "شهر مرکز آزمون",
              "country": "کشور",
              "date": "تاریخ آزمون",
              }
    labels.update(Account_Request.labels)
    
    def __init__(self, *args, **kwargs):
        kwargs["amount"] = Transactions.get_transaction_amount(type)
        super(Account_Request, self).__init__(*args, **kwargs)
    


class TOFEL(IBT):
    id_types = (
        ("0", 'پاسپورت'),
        ("1", 'کارت ملی'),

    )
    reason = models.TextField(null=False)
    country_for_study = models.TextField(null=False)
    id_type = models.CharField(choices=id_types, max_length=1, null=False)
    id_number = models.CharField(max_length=20)
    labels = {"reason": "دلایل",
              "country_for_study": "کشور مقصد",
              "id_type": "شیوهٔ احراز هویت",
              "id_number": "کد شناسایی",
              }
    labels.update(IBT.labels)
    # print("TOFEL")

    # print(labels)

    def __init__(self, *args, **kwargs):
        kwargs["source_wallet"] = "1"
        kwargs["type"] = "tofel"
        super(TOFEL, self).__init__(*args, ** kwargs)


class GRE(IBT):
    citizenships = (
        ("0", 'blah'),
        ("1", 'blah'),
        ("2", 'blah'),
    )
    statuses = (
        ("0", 'سال دوم کارشناسی'),
        ("1", 'سال سوم کارشناسی'),
        ("2", 'سال چهارم و یا آخر کارشناسی'),
        ("3", 'سال اول تحصیلات تکمیلی'),
        ("4", 'سال دوم تحصیلات تکمیلی'),
        ("5", 'فارغ التحصیل تحصیلات تکمیلی'),
        ("6", 'فارغ التحصیل کارشناسی'),
        ("7", 'دیگر'),

    )
    major_filed_code = models.CharField(max_length=50, null=False)
    major_filed_name = models.CharField(max_length=50, null=False)
    citizenship = models.CharField(choices=citizenships, max_length=1, null=False)
    educational_status = models.CharField(choices=statuses, max_length=1, null=False)
    file = models.FileField(null=True, blank=True)
    labels = {
        "file": "پیوست",
        "major_filed_code": "کد رشتهٔ مورد نظر",
        "major_filed_name": "نام رشتهٔ مورد نظر",
        "citizenship": "شهروندی",
        "educational_status": "تحصیلات",
    }
    labels.update(IBT.labels)

    def __init__(self, *args, **kwargs):
        kwargs["source_wallet"] = "1"
        kwargs["type"] = "gre"
        super(GRE, self).__init__(*args, **kwargs)


class UniversityTrans(Account_Request):
    types = (
        ("0", 'application fee'),
        ("1", 'deposit fee')
    )
    university_transـtype = models.CharField(choices=types, max_length = 1,null=False)
    university_name = models.CharField(max_length=50, null=False)
    link = models.URLField(null=False)
    guide = models.TextField(max_length=1000, null=False)
    other_details = models.TextField(max_length=1000, null=False)
    file = models.FileField(null=True, blank=True)
    labels = {
        "file": "پیوست (‌در صورت نیاز)",
        "other_details": "سایر ملاحظات",
        "guide": "دستورالعمل پرداخت",
        "link": "آدرس ورود",
        "university_name": "نام دانشگاه",
        "source_wallet": "ارز مبدا",
        "university_transـtype": "نوع تراکنش",
        "amount": "مبلغ",
    }
    labels.update(Account_Request.labels)

    def __init__(self, *args, **kwargs):
        kwargs["type"] = "universitytrans"
        super(UniversityTrans, self).__init__(*args, **kwargs)


class BankTrans(Request):
    # Only needs source user and wallet, amount, account number and bank name as arguments.
    account_number = models.CharField(max_length=20, null=False)
    bank_name = models.CharField(max_length=50, null=False)
    labels = {
        "account_number": "شماره حساب",
        "bank_name": "نام بانک",
        "source_wallet": "ارز مبدا",
        "amount": "مبلغ",
    }
    labels.update(Request.labels)


    def __init__(self, *args, **kwargs):
        kwargs["type"] = "banktrans"
        kwargs["dest_user"] = Manager.get_manager()
        temp = super().__init__(*args, ** kwargs)
        
    def set_initials(self, *args, **kwargs):
        self.source_user = self.creator
        self.dest_wallet = self.source_wallet

    def save(self, *args, **kwargs):
        super(BankTrans, self).save(*args, **kwargs)

    def set_status(self):
        self.status = "2"

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


class CustomTransactionInstance(Request):
    # each instance of custom transaction is an instance of this
    email1_exists = models.BooleanField(null=False, default=False, blank=True)
    email2_exists = models.BooleanField(null=False, default=False, blank=True)
    email3_exists = models.BooleanField(null=False, default=False, blank=True)
    short_text1 = models.CharField(max_length=100, null=True)
    short_text2 = models.CharField(max_length=100, null=True)
    short_text3 = models.CharField(max_length=100, null=True)
    short_text4 = models.CharField(max_length=100, null=True)
    short_text5 = models.CharField(max_length=100, null=True)
    short_text6 = models.CharField(max_length=100, null=True)
    short_text7 = models.CharField(max_length=100, null=True)
    short_text8 = models.CharField(max_length=100, null=True)
    long_text1 = models.TextField(null=True)
    long_text2 = models.TextField(null=True)
    long_text3 = models.TextField(null=True)
    long_text4 = models.TextField(null=True)
    long_text5 = models.TextField(null=True)
    int1 = models.IntegerField(null=True)
    int2 = models.IntegerField(null=True)
    int3 = models.IntegerField(null=True)
    int4 = models.IntegerField(null=True)
    int5 = models.IntegerField(null=True)
    date1 = models.DateTimeField(null=True)
    date2 = models.DateTimeField(null=True)
    date3 = models.DateTimeField(null=True)
    date4 = models.DateTimeField(null=True)
    float1 = models.FloatField(null=True)
    float2 = models.FloatField(null=True)
    float3 = models.FloatField(null=True)
    float4 = models.FloatField(null=True)
    boolean1 = models.NullBooleanField()
    boolean2 = models.NullBooleanField()
    boolean3 = models.NullBooleanField()
    boolean4 = models.NullBooleanField()
    boolean5 = models.NullBooleanField()
    file1 = models.FileField(null=True)
    file2 = models.FileField(null=True)
    file3 = models.FileField(null=True)
    image1 = models.ImageField(null=True)
    image2 = models.ImageField(null=True)
    url1 = models.URLField(null=True)
    url2 = models.URLField(null=True)
    user1 = models.ForeignKey('customer.Customer', on_delete=models.DO_NOTHING, null=True, related_name="first_user")
    user2 = models.ForeignKey('customer.Customer', on_delete=models.DO_NOTHING, null=True, related_name="second_user")
    request1 = models.ForeignKey('customer.Request', on_delete=models.DO_NOTHING, null=True, related_name="first_request")
    request2 = models.ForeignKey('customer.Request', on_delete=models.DO_NOTHING, null=True, related_name="second_request")


class CustomTransactionType(models.Model):
    # each type of custom transaction is an instance of this
    # TODO add fields of request to this, add default amount
    type = models.CharField(max_length=100, null=False, primary_key=True)
    label = models.CharField(max_length=100, null=False)
    description = models.TextField(null=False)

    source_user_exists = models.BooleanField(null=False, default=False, blank=True)
    source_wallet_exists = models.BooleanField(null=False, default=False, blank=True)
    dest_user_exists = models.BooleanField(null=False, default=False, blank=True)
    dest_wallet_exists = models.BooleanField(null=False, default=False, blank=True)
    final_user_exists = models.BooleanField(null=False, default=False, blank=True)
    fianl_wallet_exists = models.BooleanField(null=False, default=False, blank=True)
    amount_exists = models.BooleanField(null=False, default=False, blank=True)
    request_time_exists = models.BooleanField(null=False, default=False, blank=True)
    description_exists = models.BooleanField(null=False, default=False, blank=True)
    status_exists = models.BooleanField(null=False, default=False, blank=True)
    profitRate_exists = models.BooleanField(null=False, default=False, blank=True)
    exchange_rate_exists = models.BooleanField(null=False, default=False, blank=True)
    type_exists = models.BooleanField(null=False, default=False, blank=True)
    email1_exists = models.BooleanField(null=False, default=False, blank=True)
    email2_exists = models.BooleanField(null=False, default=False, blank=True)
    email3_exists = models.BooleanField(null=False, default=False, blank=True)
    short_text1_exists = models.BooleanField(null=False, default=False, blank=True)
    short_text2_exists = models.BooleanField(null=False, default=False, blank=True)
    short_text3_exists = models.BooleanField(null=False, default=False, blank=True)
    short_text4_exists = models.BooleanField(null=False, default=False, blank=True)
    short_text5_exists = models.BooleanField(null=False, default=False, blank=True)
    short_text6_exists = models.BooleanField(null=False, default=False, blank=True)
    short_text7_exists = models.BooleanField(null=False, default=False, blank=True)
    short_text8_exists = models.BooleanField(null=False, default=False, blank=True)
    long_text1_exists = models.BooleanField(null=False, default=False, blank=True)
    long_text2_exists = models.BooleanField(null=False, default=False, blank=True)
    long_text3_exists = models.BooleanField(null=False, default=False, blank=True)
    long_text4_exists = models.BooleanField(null=False, default=False, blank=True)
    long_text5_exists = models.BooleanField(null=False, default=False, blank=True)
    int1_exists = models.BooleanField(null=False, default=False, blank=True)
    int2_exists = models.BooleanField(null=False, default=False, blank=True)
    int3_exists = models.BooleanField(null=False, default=False, blank=True)
    int4_exists = models.BooleanField(null=False, default=False, blank=True)
    int5_exists = models.BooleanField(null=False, default=False, blank=True)
    date1_exists = models.BooleanField(null=False, default=False, blank=True)
    date2_exists = models.BooleanField(null=False, default=False, blank=True)
    date3_exists = models.BooleanField(null=False, default=False, blank=True)
    date4_exists = models.BooleanField(null=False, default=False, blank=True)
    float1_exists = models.BooleanField(null=False, default=False, blank=True)
    float2_exists = models.BooleanField(null=False, default=False, blank=True)
    float3_exists = models.BooleanField(null=False, default=False, blank=True)
    float4_exists = models.BooleanField(null=False, default=False, blank=True)
    boolean1_exists = models.BooleanField(null=False, default=False, blank=True)
    boolean2_exists = models.BooleanField(null=False, default=False, blank=True)
    boolean3_exists = models.BooleanField(null=False, default=False, blank=True)
    boolean4_exists = models.BooleanField(null=False, default=False, blank=True)
    boolean5_exists = models.BooleanField(null=False, default=False, blank=True)
    file1_exists = models.BooleanField(null=False, default=False, blank=True)
    file2_exists = models.BooleanField(null=False, default=False, blank=True)
    file3_exists = models.BooleanField(null=False, default=False, blank=True)
    image1_exists = models.BooleanField(null=False, default=False, blank=True)
    image2_exists = models.BooleanField(null=False, default=False, blank=True)
    url1_exists = models.BooleanField(null=False, default=False, blank=True)
    url2_exists = models.BooleanField(null=False, default=False, blank=True)
    user1_exists = models.BooleanField(null=False, default=False, blank=True)
    user2_exists = models.BooleanField(null=False, default=False, blank=True)
    request1_exists = models.BooleanField(null=False, default=False, blank=True)
    request2_exists = models.BooleanField(null=False, default=False, blank=True)

    source_label = models.CharField(max_length=100, null=True, blank=True)
    source_label = models.CharField(max_length=100, null=True, blank=True)
    dest_label = models.CharField(max_length=100, null=True, blank=True)
    dest_label = models.CharField(max_length=100, null=True, blank=True)
    final_label = models.CharField(max_length=100, null=True, blank=True)
    fianl_label = models.CharField(max_length=100, null=True, blank=True)
    amount_label = models.CharField(max_length=100, null=True, blank=True)
    request_label = models.CharField(max_length=100, null=True, blank=True)
    description_label = models.CharField(max_length=100, null=True, blank=True)
    status_label = models.CharField(max_length=100, null=True, blank=True)
    profitRate_label = models.CharField(max_length=100, null=True, blank=True)
    exchange_label = models.CharField(max_length=100, null=True, blank=True)
    type_label = models.CharField(max_length=100, null=True, blank=True)
    email1_label = models.CharField(max_length=100, null=True, blank=True)
    email2_label = models.CharField(max_length=100, null=True, blank=True)
    email3_label = models.CharField(max_length=100, null=True, blank=True)
    short_text1_label = models.CharField(max_length=100, null=True, blank=True)
    short_text2_label = models.CharField(max_length=100, null=True, blank=True)
    short_text3_label = models.CharField(max_length=100, null=True, blank=True)
    short_text4_label = models.CharField(max_length=100, null=True, blank=True)
    short_text5_label = models.CharField(max_length=100, null=True, blank=True)
    short_text6_label = models.CharField(max_length=100, null=True, blank=True)
    short_text7_label = models.CharField(max_length=100, null=True, blank=True)
    short_text8_label = models.CharField(max_length=100, null=True, blank=True)
    long_text1_label = models.CharField(max_length=100, null=True, blank=True)
    long_text2_label = models.CharField(max_length=100, null=True, blank=True)
    long_text3_label = models.CharField(max_length=100, null=True, blank=True)
    long_text4_label = models.CharField(max_length=100, null=True, blank=True)
    long_text5_label = models.CharField(max_length=100, null=True, blank=True)
    int1_label = models.CharField(max_length=100, null=True, blank=True)
    int2_label = models.CharField(max_length=100, null=True, blank=True)
    int3_label = models.CharField(max_length=100, null=True, blank=True)
    int4_label = models.CharField(max_length=100, null=True, blank=True)
    int5_label = models.CharField(max_length=100, null=True, blank=True)
    date1_label = models.CharField(max_length=100, null=True, blank=True)
    date2_label = models.CharField(max_length=100, null=True, blank=True)
    date3_label = models.CharField(max_length=100, null=True, blank=True)
    date4_label = models.CharField(max_length=100, null=True, blank=True)
    float1_label = models.CharField(max_length=100, null=True, blank=True)
    float2_label = models.CharField(max_length=100, null=True, blank=True)
    float3_label = models.CharField(max_length=100, null=True, blank=True)
    float4_label = models.CharField(max_length=100, null=True, blank=True)
    boolean1_label = models.CharField(max_length=100, null=True, blank=True)
    boolean2_label = models.CharField(max_length=100, null=True, blank=True)
    boolean3_label = models.CharField(max_length=100, null=True, blank=True)
    boolean4_label = models.CharField(max_length=100, null=True, blank=True)
    boolean5_label = models.CharField(max_length=100, null=True, blank=True)
    file1_label = models.CharField(max_length=100, null=True, blank=True)
    file2_label = models.CharField(max_length=100, null=True, blank=True)
    file3_label = models.CharField(max_length=100, null=True, blank=True)
    image1_label = models.CharField(max_length=100, null=True, blank=True)
    image2_label = models.CharField(max_length=100, null=True, blank=True)
    url1_label = models.CharField(max_length=100, null=True, blank=True)
    url2_label = models.CharField(max_length=100, null=True, blank=True)
    user1_label = models.CharField(max_length=100, null=True, blank=True)
    user2_label = models.CharField(max_length=100, null=True, blank=True)
    request1_label = models.CharField(max_length=100, null=True, blank=True)
    request2_label = models.CharField(max_length=100, null=True, blank=True)
    # TODO add default values
