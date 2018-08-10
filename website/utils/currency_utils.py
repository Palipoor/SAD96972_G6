""" add utility functions related to currencies here. """


class Currencies:
    # tables for convertig currencies
    currency_to_num_json = {"rial": 0,
                            "dollar": 1,
                            "euro": 2,
                            }
    currency_to_persian_json = {"rial": "ریال",
                                "dollar": "دلار",
                                "euro": "یورو",
                                }
    num_to_currency_json = {0: "rial",
                            1: "dollar",
                            2: "euro",
                            }

    @staticmethod
    def currency_to_num(currency):
        return Currencies.currency_to_num_json[currency]

    @staticmethod
    def currency_to_persian(currency):
        return Currencies.currency_to_persian_json[currency]

    @staticmethod
    def num_to_currency(currency):
        return Currencies.num_to_currency_json[currency]
