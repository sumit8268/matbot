from flask import Flask, request
from flask_sqlalchemy import SQLAlchemy
import json


# adding object object to database
# p1 = Point(point_label = "junction1", point_type = "junction", connected_to = None, with_color = None)
# db.session.add(p1)
# db.session.commit()



# accessing data from android
# request.form['source1']
# request.form['dest1']


# sending data to android
# result = json.dumps({'success':'fafafafaf'})




# flask object
app = Flask(__name__)

# configuring sqlalchemy
app.config['SQLALCHEMY_DATABASE_URI'] = 'mysql://root@localhost/matbot'
db = SQLAlchemy(app)


# Point table
class Point(db.Model):
    point_label =db.Column(db.String(100), primary_key=True)
    point_type = db.Column(db.String(100))
    connected_to = db.Column(db.String(100))
    with_color = db.Column(db.String(100))

    def __repr__(self):
        return self.point_label


# User table
class User(db.Model):
    name =db.Column(db.String(100))
    email = db.Column(db.String(100), primary_key=True)
    password = db.Column(db.String(100))

    def __repr__(self):
        return self.email


# Work table
class Work(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    source =db.Column(db.String(100))
    destination = db.Column(db.String(100))
    work_done = db.Column(db.String(100))

    def __repr__(self):
        return f"{self.source} -> {self.destination}"



@app.route("/")
def index():
    return "hello"


if __name__ == "__main__":
    app.run(host='192.168.43.170', port=5000, debug=False)
    # app.run(debug=True)