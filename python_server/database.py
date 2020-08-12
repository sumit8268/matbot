from flask_sqlalchemy import SQLAlchemy

from prm import db


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