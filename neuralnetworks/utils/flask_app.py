from flask import Flask, request, redirect, url_for, jsonify
from flask_cors import CORS
from utils import get_all_statistics

app = Flask(__name__)
CORS(app)

@app.route("/process",methods=['POST'])
def smart_search():
    search_query = request.get_json()['path']
    return get_all_statistics(search_query)


if __name__ == '__main__':
    app.run(port='5050', host='0.0.0.0')