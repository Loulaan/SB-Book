import warnings
from collections import Counter
import re
from datetime import datetime


from deeppavlov import build_model, configs
from nltk.tokenize import word_tokenize
import numpy as np
import pandas as pd
from gensim.summarization import summarize
import pymorphy2


warnings.filterwarnings("ignore")
morph = pymorphy2.MorphAnalyzer()
PATH = 'books/NadPropastyuVoRzhi.txt'
PATHSUMM = 'books/summDemo.txt'
# path = 'books/Demo.txt'


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
    return list_of_top


def summarizer(text):
    """
    input: text (str)
    ratio: Number between 0 and 1 that determines the proportion of the number of sentences of the original text to be chosen for the summary.
    split: (bool) – If True, list of sentences will be returned. Otherwise joined strings will be returned.
    output: str or list
    """

    return summarize(text, word_count=400, split=False)


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


def get_all_statistics(model_name="NER"):
    """
    Returns list of top 10 names, top 10 places and summary of text
    """

    model = buildmodel(model_name)
    statistics = []
    df = pd.DataFrame()
    start_time = datetime.now()
    with open(PATH, encoding='utf-8') as file:
        text = file.read()
        split_regex = re.compile(r'[.|!|?|…]')
        sentences = filter(lambda t: t, [t.strip() for t in split_regex.split(text)])
        for s in sentences:
            preds = model([tokenize_text(s)])
            df = pd.concat([df, lemmanization(throw_O_class(create_dataframe(preds)))], ignore_index=True)
        print(datetime.now() - start_time, " : Обработка предложений (Лемманизация и избавление от О).")
        df = bounding_classes(df)
        print(datetime.now() - start_time, " : Обработка датафрейма (Объединение классов).")
        statistics.append(get_top_names_and_locations(df))
        statistics.append(summarizer(text))
        print(datetime.now() - start_time, " : Генерация summary.")
        save_summary(statistics[-1])
        print("Statistic[0][0]:\n", statistics[0][0])
        print("\n\nStatistic[0][1]:\n", statistics[0][1])

    return statistics