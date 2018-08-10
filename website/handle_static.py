import sys
import os
import re

root = "."
static_to_root = '.'
print(sys.argv)
if(len(sys.argv) > 1):
    root = sys.argv[1] + "/"
if (len(sys.argv) > 2):
    static_to_root = sys.argv[2] + "/"
address_input_for_function = ""


def replace(match):
    print('harrrrr')
    print(address_input_for_function)
    print(match.group('address'))
    print(os.path.normpath(address_input_for_function+match.group('address')))
    print('harrrrr')
    result = match.group("tag") + r''' = '{% static "''' + os.path.normpath(address_input_for_function+match.group('address')) + r'''" %}' '''
    return result


link_reg = re.compile(r"""(?P<tag>href|src)\s*=\s*['"](?!http)(?!{%)(?P<address>.*?)[\'\"]""", re.DOTALL)
for dirpath, dnames, fnames in os.walk(root):
    for file_name in fnames:
        root_to_html = "." + "/" + dirpath[len(root):] + '/'
        file_address = dirpath + "/" + file_name
        print(dirpath)
        if (file_name.endswith('.html')):
            with open(file_address) as html:
                code = html.read()
                if(not code.startswith("{%")):
                    code = r"{% load static staticfiles %}" + "\n" + code
                address_input_for_function = static_to_root + root_to_html
                code = link_reg.sub(replace, code)
                # print(link_reg.findall(code))
            with open(file_address, 'wt') as html:
                html.write(code)
