import requests

URL = "https://translate.yandex.net/api/v1.5/tr.json/translate"
KEY = "trnsl.1.1.20200322T074224Z.be5d57745701aaea.c17628b89a314310764d96347e1355ed4e441410"

def translater(text):
    params = {
        "key": KEY,     
        "text": text,
        "lang": 'ru-en'
    }
    response = requests.get(URL ,params=params)
    return response.json()

def wrapper_translater(text):
    return ''.join(translater(text)['text'])