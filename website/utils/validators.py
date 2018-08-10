from django.core.exceptions import ValidationError
import unicodedata
from utils.strings import *

def persian_name_validator(name):
    for c in name:
        cat = unicodedata.category(c)
        if cat not in ('Lo', 'Zs'):
            raise ValidationError(INVALID_PERSIAN_NAME)

def english_name_validator(name):
    for c in name:
        cat = unicodedata.category(c)
        if cat not in ('Ll', 'Zs', 'Lu'):
            raise ValidationError(INVALID_ENGLISH_NAME)
