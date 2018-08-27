""" add utility functions related to currencies here. """


class Transactions:
    # tables for convertig currencies, supportung transactions

    transaction_types = [("tofel", "ثبت نام تافل", "پرداخت هزینهٔ ثبت نام آزمون تافل"),
                         ("gre", "ثبت نام جی‌ار‌ای", "پرداخت هزینهٔ ثبت نام آزمون جی‌ار‌ای"),
                         ("universitytrans", "پرداخت فرم دانشگاه", "پرداخت هزینهٔ فرم‌های اپلای"),
                         ("banktrans", "پرداخت به حساب بانکی", "برداشت پول از کیف پول موجود در موسسه و واریز به حساب خارج موسسه"),
                         ("unkowntrans", "پرداخت ناشناس", "پرداخت به شمارهٔ حسابی دیگر در درون مجموعه")
                         ]
    currency_to_num_json = {"rial": "0",
                            "dollar": "1",
                            "euro": "2",
                            }
    currency_to_persian_json = {"rial": "ریال",
                                "dollar": "دلار",
                                "euro": "یورو",
                                }
    num_to_currency_json = {"0": "rial",
                            "1": "dollar",
                            "2": "euro",
                            }
    num_to_currency_symbol = {"0": " ریال",
                              "1": " $",
                              "2": " €",
                              }

    request_types_json = (
        ("0", 'accepted'),
        ("1", 'rejected'),
        ("2", 'pending'),
        ("3", 'failed'),
        ("4", 'reported'),
    )

    persian_types_json = {
        "0": 'تایید شده',
        "1": 'رد شده',
        "2": 'در انتظار',
        "3": 'فیل',
        "4": 'گزارش شده',
    }

    request_types_for_review_json = (
        ("0", 'تایید'),
        ("1", 'رد'),
        ("4", 'گزارش'),
    )

    request_types_for_manager_json = (
        ("0", 'تایید'),
        ("1", 'رد'),
    )

    exhchange_matrix = [[1, 0.0001, 0.0001],
                        [10000, 1, 1],
                        [10000, 1, 1, ]]

    profitRates = {"tofel": 0.05,
                   "gre": 0.05,
                   "universitytrans": 0.05,
                   "banktrans": 0.05,
                   "unkowntrans": 0.05,
                   }

    transaction_amounts = {"tofel": 100,
                           "gre": 100,
                           }

    @staticmethod
    def currency_to_num(currency):
        return Transactions.currency_to_num_json[currency]

    @staticmethod
    def currency_to_persian(currency):
        return Transactions.currency_to_persian_json[currency]

    @staticmethod
    def num_to_currency(currency):
        currency = str(currency)
        return Transactions.num_to_currency_json[currency]

    @staticmethod
    def get_exchange_rate(source, destination):
        if (source == None or destination == None):
            return 1
        source = int(source)
        destination = int(destination)
        return Transactions.exhchange_matrix[source][destination]

    @staticmethod
    def get_profirRate(type):
        if(type in Transactions.profitRates):
            return Transactions.profitRates[type]
        return 0

    @staticmethod
    def get_transaction_amount(type):
        return Transactions.transaction_amounts[type]

    @staticmethod
    def get_persian_status(num):
        return Transactions.persian_types_json[num]

    @staticmethod
    def get_currency_symbol(num):
        return Transactions.num_to_currency_symbol[num]
