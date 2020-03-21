import warnings
from collections import Counter

from deeppavlov import build_model, configs
from nltk.tokenize import word_tokenize
import numpy as np
import pandas as pd
from gensim.summarization import summarize
import pymorphy2


warnings.filterwarnings("ignore")
morph = pymorphy2.MorphAnalyzer()
path = 'books/BednayaLizaDemo.txt'


def tokenize_text(text):
    return word_tokenize(text)


def bounding_classes(df):
    """
    Объединяет смкежные классы.
    На вход: dataframe, полученный в результате работы сети. Состоит из 2х столбцов.
    В 0 содержатся слова или символы - "Words". В 1 теги - "Classes'.
    На выход: Очищенные от B-LOC, B-PER, B-ORG и O тегов.
    """
    df_cleaned = df[df["Classes"] != 'O']
    tags_b = ['I-PER', 'I-LOC', 'I-ORG']
    for i in range(len(df_cleaned)):
        text, tag = df_cleaned.iloc[i]
        if tag in tags_b:
            df_cleaned.iloc[ii][0] += ' ' + text
        else:
            ii = i
    df_cleaned = df_cleaned[df_cleaned["Classes"] != 'I-PER'][df_cleaned["Classes"] != 'I-LOC'][df_cleaned["Classes"] != 'I-ORG'].reindex()
    return df_cleaned


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
    statistic_dict = Counter(df_related2cls['Words']).most_common(5)
    list_of_top.append([pair[0] for pair in statistic_dict])

    return list_of_top


def summarizer(path=path):
    """
    input: text (str)
    ratio: Number between 0 and 1 that determines the proportion of the number of sentences of the original text to be chosen for the summary.
    split: (bool) – If True, list of sentences will be returned. Otherwise joined strings will be returned.
    output: str or list
    """

    with open(path, 'r', encoding='utf-8') as textfile:
        summary = summarize(textfile.read(), ratio=0.2, split=False)
    return summary


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

    with open(path, encoding='utf-8') as file:
        text = file.read()
        preds = model([tokenize_text(text)])
        df = lemmanization(bounding_classes(create_dataframe(preds)))
        statistics.append(get_top_names_and_locations(df))
        statistics.append(summarizer(text))

    return statistics