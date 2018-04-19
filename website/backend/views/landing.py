from django.http import HttpResponse
from django.template import Context, loader
import os
def index(request):
    # now =3
    # html = "<html><body>It is now %s.</body></html>" % now
    # return HttpResponse(html)

    template = loader.get_template("index.html")
    return HttpResponse(template.render())
