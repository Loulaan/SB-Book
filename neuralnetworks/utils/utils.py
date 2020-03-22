import warnings
from collections import Counter
import re
import numpy as np


from deeppavlov import build_model, configs
from nltk.tokenize import word_tokenize
import numpy as np
import pandas as pd
from gensim.summarization import summarize
import pymorphy2

from YTranslater import wrapper_translater
from tags_model import *


warnings.filterwarnings("ignore")
morph = pymorphy2.MorphAnalyzer()
CLF, TFIDF = load_models()
PATHSUMM = 'books/summDemo.txt'
PATH = 'books/NadPropastyuVoRzhi.txt'
path = 'books/Demo.txt'


def tokenize_text(text):
    return word_tokenize(text)


def throw_O_class(df):
    return df[df["Classes"] != 'O']


def bounding_classes(df):
    """
    Объединяет смежные классы.
    На вход: dataframe, полученный в результате работы сети. Состоит из 2х столбцов.
    В 0 содержатся слова или символы - "Words". В 1 теги - "Classes'.
    На выход: Очищенные от B-LOC, B-PER, B-ORG.
    """

    tags_b = ['I-PER', 'I-LOC', 'I-ORG']
    for i in range(len(df)):
        text, tag = df.iloc[i]
        if tag in tags_b:
            df.iloc[ii][0] += ' ' + text
        else:
            ii = i
    df = df[df["Classes"] != 'I-PER'][df["Classes"] != 'I-LOC'][df["Classes"] != 'I-ORG'].reindex()
    return df


def lemmanization(df):
    """
    Конвертирует слова в инфинитив/И.п.
    """

    words = [morph.parse(word)[0].normal_form for word in df['Words']]
    df['Words'] = words
    return df


def get_top_names_and_locations(df):
    """
    Get the 10 most common names and locations.
    """

    list_of_top = []

    df_related2cls = df[df['Classes'] == "B-PER"]
    statistic_dict = Counter(df_related2cls['Words']).most_common(5)
    list_of_top.append([pair[0] for pair in statistic_dict])

    df_related2cls = df[df['Classes'] == "B-LOC"]

    legal_entity = 0
    top_places = []
    statistic_dict = Counter(df_related2cls['Words']).most_common(40)
    keys = [x[0] for x in statistic_dict]
    for key in keys:
        if legal_entity < 5:
            if key == '—':
                continue
            top_places.append(key)
            legal_entity += 1
        else:
            break
    list_of_top.append(top_places)
    return list_of_top[0], list_of_top[1]


def summarizer(text):
    """
    input: text (str)
    ratio: Number between 0 and 1 that determines the proportion of the number of sentences of the original text to be chosen for the summary.
    split: (bool) – If True, list of sentences will be returned. Otherwise joined strings will be returned.
    output: str or list
    """

    return summarize(text, word_count=400, split=False)

def clean_text(text):
    text = text.lower()
    text = re.sub(r"what's", "what is ", text)
    text = re.sub(r"\'s", " ", text)
    text = re.sub(r"\'ve", " have ", text)
    text = re.sub(r"can't", "can not ", text)
    text = re.sub(r"n't", " not ", text)
    text = re.sub(r"i'm", "i am ", text)
    text = re.sub(r"\'re", " are ", text)
    text = re.sub(r"\'d", " would ", text)
    text = re.sub(r"\'ll", " will ", text)
    text = re.sub(r"\'scuse", " excuse ", text)
    text = text.strip(' ')
    return text

def get_genres(text, clf=CLF, tfidf=TFIDF):
    en_text = wrapper_translater(text)
    processed_en_text = clean_text(en_text)
    return list(predict([processed_en_text], clf, tfidf))


def create_dataframe(preds):
    """
    Creates dataframe from neural networks output
    """

    df = pd.DataFrame(np.array(preds).squeeze()).T
    df.rename(columns={0: 'Words', 1: 'Classes'}, inplace=True)
    return df


def save_dataframe(df, path='books/NER/NER_BednayaLizaDemo.csv'):
    """
    Save dataframe into given path.
    """

    df.to_csv(path, index=False, encoding='utf-8')


def save_summary(text):
    with open('books/summDemo.txt', 'w', encoding='utf-8') as file:
        file.write(text)


def buildmodel(model_name):
    """
    Ключи для сетей
    'ner' - анализ и нахождения сущностей, мест и организаций.
    'sentiment' - сентиментальный анализ текста.
    'squad' - ответ на вопрос по тексту.
    """

    model = None
    if model_name == 'NER':
        model = build_model(configs.ner.ner_rus, download=True)
    elif model_name == 'SENTIMENT':
        model = build_model(configs.classifiers.rusentiment_convers_bert, download=True)
    elif model_name == 'SQUAD':
        model = build_model(configs.squad.squad_ru_bert_infer, download=True)
    return model


def get_all_statistics(path, model_name="NER"):
    """
    Returns list of top 10 names, top 10 places and summary of text
    """

    model = buildmodel(model_name)
    statistics = []
    df = pd.DataFrame()
    with open(path, encoding='utf-8') as file:
        text = file.read()
        split_regex = re.compile(r'[.|!|?|…]')
        sentences = filter(lambda t: t, [t.strip() for t in split_regex.split(text)])
        for s in sentences:
            preds = model([tokenize_text(s)])
            df = pd.concat([df, lemmanization(throw_O_class(create_dataframe(preds)))], ignore_index=True)
        df = bounding_classes(df)
        summary = summarizer(text)
        statistics.append(get_genres(summary))
        top_persons, top_places = get_top_names_and_locations(df)
        statistics.append(top_persons)
        statistics.append(top_places)
        statistics.append(summary)

    return statistics


def answers_questions(text, model_name="SQUAD"):
    model = buildmodel(model_name)
    answer = model([text])
    return answer