from flask import Flask, request, redirect, url_for, jsonify
from flask_cors import CORS
from utils import get_all_statistics
import requests

app = Flask(__name__)
CORS(app)

@app.route("/process",methods=['POST'])
def smart_search():
    result=[]
    query = request.get_json()['query']
    
    resp = requests.post("http://localhost:5000/model",json={
                "context_raw": [query],
                "question_raw": ["О чём?"]}).json()

    elastic_data = requests.post("http://localhost:5000/smart_search" , json={ "query" : resp[0][0]})
    book_info = request.get("http://localhost:8080/api/v1/books/"+ elastic_data["id"]).json()
    result.append(elastic_data,book_info["author"],book_info["price"],book_info["imageUrl"],book_info["title"])
    return jsonify(result)

@app.route("/chat",methods = ['POST'])
def chat():
    book_id = request.get_json()['id']
    question = request.get_json()['qestion']

    elastic_data = requests.post("http://localhost:5000/get_by_id" , json={ "query" : book_id}).json()
    resp = request.post("http://localhost:5000/model",json={
                "context_raw": [elastic_data['summirize']],
                "question_raw": question}).json()
    return resp[0][0]

@app.route("/dump",methods=['POST'])
def dump():
    paths = request.get_json()['paths']
    print
    for i, path in enumerate(paths):
        book_info = requests.get("http://localhost:8080/api/v1/books/"+ str(i+1)).json()
        to_elastic = [i,book_info['author'],book_info['price'],book_info['imageUrl'],book_info['title']]
        temp_res = get_all_statistics(path) # ganre, pers, loc, summurize
        for processed_data in temp_res:
            to_elastic.append(processed_data);
        request.post("http://localhost:5000/save", json={"data": to_elastic })

if __name__ == '__main__':
    app.run(port='5700', host='0.0.0.0')