import warnings

from deeppavlov import build_model, configs
from nltk.tokenize import word_tokenize
import numpy as np
import pandas as pd


warnings.filterwarnings("ignore")


def tokenize_text(text):
    # text = re.sub("\s\s+", ' ', re.sub('[0-9]', ' ', re.sub('[—–?!)(«»":„“]', '', text)))
    return word_tokenize(text)

def bounding_classes(df):
    """
    На вход: данные из сетки "ner_rus"
    На выход: pandas dataframe с двумя колонками 0 и 1 (int) \
    В 0 содержится текст. В 1 теги - B-LOC, B-PER, B-ORG.
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

def create_dataframe(preds):
    df = pd.DataFrame([np.array(preds[0][0]), np.array(preds[1][0])]).T
    df.rename(columns={0: 'Words', 1: 'Classes'}, inplace=True)
    return df

def save_dataframe(df):
    df.to_csv('books/NER/NER_BednayaLizaDemo.csv', index=False, encoding='utf-8')

def generate_ner_table():
    path = 'books/BednayaLizaDemo.txt'
    model = build_model(configs.ner.ner_rus, download=True)

    with open(path, encoding='utf-8') as file:
        without_stopWords_file = tokenize_text(file.read())
        preds = model([without_stopWords_file])
    df = bounding_classes(create_dataframe(preds))
    save_dataframe(df)





