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
    words = [morph.parse(word)[0].normal_form for word in df['Words']]
    df['Words'] = words
    return df

def create_dataframe(preds):
    df = pd.DataFrame([np.array(preds[0][0]), np.array(preds[1][0])]).T
    df.rename(columns={0: 'Words', 1: 'Classes'}, inplace=True)
    return df

def save_dataframe(df):
    df.to_csv('books/NER/NER_BednayaLizaDemo.csv', index=False, encoding='utf-8')

def generate_ner_table():
    model = build_model(configs.ner.ner_rus, download=True)

    with open(path, encoding='utf-8') as file:
        without_stopWords_file = tokenize_text(file.read())
        preds = model([without_stopWords_file])
    df = lemmanization(bounding_classes(create_dataframe(preds)))

    save_dataframe(df)

def summarizer(path=path):
    """
    input: text (str)
    ratio: Number between 0 and 1 that determines the proportion of the number of sentences of the original text to be chosen for the summary.
    split: (bool) – If True, list of sentences will be returned. Otherwise joined strings will be returned.
    output: str or list
    """
    with open(path, 'r', encoding='utf-8') as textfile:
        summary = summarize(textfile.read(), ratio=0.2, split=False)
        with open('books/summDemo.txt', 'w', encoding='utf-8') as file:
            file.write(summary)



