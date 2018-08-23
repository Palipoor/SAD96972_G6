""" add utility functions related to currencies here. """


class Transactions:
    # tables for convertig currencies, supportung transactions
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
    request_types_json = (
        ("0", 'accepted'),
        ("1", 'rejected'),
        ("2", 'pending'),
        ("3", 'failed'),
        ("4", 'reported'),
    )

    request_types_for_review_json = (
        ("0", 'accepted'),
        ("1", 'rejected'),
        ("4", 'reported'),
    )

    request_types_for_manager_json = (
        ("0", 'accepted'),
        ("1", 'rejected'),
    )

    exhchange_matrix = [[1, 0.01, 0.01],
                        [100, 1, 1],
                        [100, 1, 1, ]]

    profitRates = {"tofel": 0.05,
                   "gre": 0.05
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
        return Transactions.profitRates[type]

    @staticmethod
    def get_transaction_amount(type):
        return Transactions.transaction_amounts[type]
