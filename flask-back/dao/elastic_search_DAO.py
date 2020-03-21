from elasticsearch import Elasticsearch
from elasticsearch_dsl import Search, Index, Document, analyzer, Integer, Text, Keyword
import json
    
simple = analyzer('simple',filter=["russian_stop", "lowercase", "russian_stemmer"])
elastic_client = Elasticsearch([{'host':'localhost','port':9200}])
index = Index('books', using=elastic_client )
index.delete(ignore=404)
index.create()

L1 = [
        "Война и мир",
        ["тег","классика","эпопея"],
        {
            "first_name":"Лев",
            "middle_name":"Николаевич",
            "last_name": "Толстой"
                                } ,
        ["Балконский","Ростова"] ,
        ["Москва","Россия"] , 
        "Роман-эпоппе о событиях 1812 года и месте человека в ней" ,
        ["Отличный прописанный мир","Много персонажей","Любовные истории","Баталии"]
    ]
L2 =   [
        "Тихий Дон",
        ["тег","классика","эпопея"],
        {
           "first_name":"Михаил",
            "middle_name":"Александрович",
            "last_name": "Шолохов"
                                } ,
        ["Григорий","Наташа"] ,
        ["Дон","Вешенская"] , 
        "Роман-эпоппея о событиях 1917-1922 годов",
        ["Отличный прописанный мир","Жизнь","Казаки","Баталии"]
    ]
       
@index.document
class BookToStore(Document):
    title = Text(analyzer = simple)
    category =[]
    author = {}
    actors = []
    places = []
    summurize = Text(analyzer = simple)
    features = []

def search(query):
    query2body={
            "query": {
                "simple_query_string": {
                "query": query,
                "fields": []
                    }
                }
            }
    elastic_client.indices.refresh(index="books")
    return elastic_client.search(index = "books", body= query2body)['hits']['hits']

def save(processedBookData):
    BookToStore(title = processedBookData[0],
                            category =  processedBookData[1]  ,
                            author = processedBookData[2]  ,
                            actors =  processedBookData[3]  ,
                            places =  processedBookData[4]  ,
                            summirize = processedBookData[5] ,
                            features =  processedBookData[6] 
                        ).save()
save(L1)
save(L2)

        