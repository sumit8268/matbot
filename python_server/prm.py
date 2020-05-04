from flask import Flask, request
from flask_sqlalchemy import SQLAlchemy
import json
from concurrent.futures import ThreadPoolExecutor
from motor_driver import *


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


# api to add new point or junction to the database
@app.route("/add_point_api", methods=['POST', 'GET'])
def add_point_api():
    try:
        point_label = request.form['label']
        point_type = request.form['type']
        connected_to = request.form['connection']
        with_color = request.form['color']
        
        p1 = Point(point_label = point_label, point_type = point_type, connected_to = connected_to, with_color = with_color)
        db.session.add(p1)
        db.session.commit()

        result = json.dumps({'success':'Point added to database'})
    except:
        result = json.dumps({'success':'Error occurred !!!'})
    finally:
        return result


# api to send all the points to android spinner
@app.route("/get_points_api", methods=['POST', 'GET'])
def get_points_api():
    try:
        points = Point.query.filter_by(point_type='point').all()
        a = ['Select The Points To Transport']
        for i in points:
            a.append(str(i))
        result = json.dumps(a)
    except:
        result = json.dumps(['Error occurred !!! try again'])
    finally:
        return result


# api to call bot to the source
@app.route("/call_bot_api", methods=['POST', 'GET'])
def call_bot_api():
    result = json.dumps({'success':'error occurred !!data not inserted', 'free_bot':False})
    try:
        source = request.form['source']
        destination = request.form['destination']
        if Work.query.one_or_none() == None:
            w = Work(source=source, destination=destination, work_done=False)
            db.session.add(w)
            db.session.commit()
            
            with ThreadPoolExecutor() as executor:
                future = executor.submit(call_bot, source, destination)
            
            result = json.dumps({'success':'data inserted Bot is arriving', 'free_bot':True})
        else:
            result = json.dumps({'success':'bot is not free!! try later', 'free_bot':False})
    except:
        result = json.dumps({'success':'error occurred !!data not inserted', 'free_bot':False})
    finally:
        return result


# api to send bot to the destination
@app.route("/send_bot_api", methods=['POST', 'GET'])
def send_bot_api():
    try:
        source = request.form['source']
        destination = request.form['destination']
        
            
        with ThreadPoolExecutor() as executor:
            future = executor.submit(send_bot, source, destination)
            
        result = json.dumps({'success':'Bot is sent'})    
    except:
        result = json.dumps({'success':'error occurred !! try again'})
    finally:
        return result


if __name__ == "__main__":
    app.run(host='192.168.43.170', port=5000, debug=False)
    # app.run(debug=True)