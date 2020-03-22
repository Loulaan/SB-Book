from flask import Flask, request, redirect, url_for, jsonify
from flask_cors import CORS
from dao.elastic_search_DAO import search,save

app = Flask(__name__)
CORS(app)

@app.route("/smart_search",methods=['POST'])
def smart_search():
    search_query = request.get_json()['query']
    return jsonify(search(search_query))

@app.route("/save",methods=['POST'])
def save_book_for_smart_search():
    book = request.get_json()['processedBookData']
    save(book)

if __name__ == '__main__':
    app.run(port='5000', host='0.0.0.0')