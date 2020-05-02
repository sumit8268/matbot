from flask import Flask, request
from flask_sqlalchemy import SQLAlchemy
import json

app = Flask(__name__)
app.config['SQLALCHEMY_DATABASE_URI'] = 'mysql://root@192.168.43.170/matbot'
db = SQLAlchemy(app)

class Point(db.Model):
    point_label =db.Column(db.String(100), primary_key=True)
    point_type = db.Column(db.String(100))
    connected_to = db.Column(db.String(100))
    with_color = db.Column(db.String(100))

    def __repr__(self):
        return self.point_label
    


# p1 = Point(point_label = "junction1", point_type = "junction", connected_to = None, with_color = None)
# db.session.add(p1)
# db.session.commit()


@app.route("/")
def index():
    return "hello"

@app.route("/addroute", methods=['POST', 'GET'])
def object():
    print(request.form['source1'],request.form['dest1'],request.form['color1'])
    
    j = json.dumps({'success':'fafafafaf'})
    return j

if __name__ == "__main__":
    app.run(host='192.168.43.170', port=5000, debug=False)
    # app.run(debug=True)