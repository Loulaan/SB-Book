import pickle

def load_models(nb_path = "../weights/naivebayes.pkl", tfidf_path = "../weights/tfidf.pkl"):
    with open(nb_path, 'rb') as f:
        clf = pickle.load(f)
    with open(tfidf_path, 'rb') as f:
        tfidf = pickle.load(f)
    return clf, tfidf

TAGS = ["экшен", "взрослое", "анимация", "биография", "черный", "дети", 
 "комедия", "преступление", "документальный фильм ", "драма", "фантазия", "история", 
 "мюзикл", "мистерия", "политика", "романтика", "сериал", "короткометражка", 
 "спорт", "триллер"]

def predict(text, model, tfidf,  tags = TAGS):
    print(model)
    print(tfidf)
    preds = model.predict(tfidf.transform(text))
    answers = []
    for pred in preds:
        answer = []
        for i, p in enumerate(pred):
            if p == 1 and i != 4:
                answer.append(tags[i])
        answers.append(answer)
    
    return answers