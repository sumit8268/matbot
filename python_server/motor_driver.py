from time import sleep
from database import *

from prm import db



def detect_color_from_camera(color1):
    return False

def move_bot(params1):
    pass


def call_bot(source, destination):
    flag = 0
    print(f"Bot moving from {source} -----> {destination}")
    path = []
    color = []
    source_point = Point.query.filter_by(point_label=source).first()
    dest_point = Point.query.filter_by(point_label=destination).first()
    source_juction = source_point.connected_to
    dest_juction = dest_point.connected_to
    if source != destination:
        if source_juction == dest_juction:
            flag = 0
            path.append(source_point.point_label)
            path.append(source_juction)
            path.append(dest_point.point_label)
            color.append(source_point.with_color)
            color.append(dest_point.with_color)
        else:
            if dest_juction == 'point1':
                flag = 1
                dest_juction, source_juction = source_juction, dest_juction
                source_point, dest_point = dest_point, source_point
            path.append(dest_juction)
            while source_juction not in path:
                point = Point.query.filter_by(point_label=path[-1]).first()
                path.append(point.connected_to)
                color.append(point.with_color)
            else:
                path.insert(0, dest_point.point_label)
                color.insert(0, dest_point.with_color)
                path.append(source_point.point_label)
                color.append(source_point.with_color)
        if flag == 0:
            print(path[::-1])
            print(color[::-1])
        else:
            print(path)
            print(color)

        for i in range(len(color)):
            params = detect_color_from_camera(color[i])
            while params:
                print("following ",color[i])
                move_bot(params)
                params = detect_color_from_camera(color[i])
        else:
            print("reached Source")
    else:
        print("Bot is already at the source")
    return

    
    
    



def send_bot(source, destination):
    flag = 0
    print(f"Bot moving from {source} -----> {destination}")
    path = []
    color = []
    source_point = Point.query.filter_by(point_label=source).first()
    dest_point = Point.query.filter_by(point_label=destination).first()
    source_juction = source_point.connected_to
    dest_juction = dest_point.connected_to
    if source != destination:
        if source_juction == dest_juction:
            flag = 1
            path.append(source_point.point_label)
            path.append(source_juction)
            path.append(dest_point.point_label)
            color.append(source_point.with_color)
            color.append(dest_point.with_color)
        else:
            if dest_juction == 'point1':
                flag = 1
                dest_juction, source_juction = source_juction, dest_juction
                source_point, dest_point = dest_point, source_point
            path.append(dest_juction)
            while source_juction not in path:
                point = Point.query.filter_by(point_label=path[-1]).first()
                path.append(point.connected_to)
                color.append(point.with_color)
            else:
                path.insert(0, dest_point.point_label)
                color.insert(0, dest_point.with_color)
                path.append(source_point.point_label)
                color.append(source_point.with_color)
        if flag == 0:
            print(path[::-1])
            print(color[::-1])
        else:
            print(path)
            print(color)

        for i in range(len(color)):
            params = detect_color_from_camera(color[i])
            while params:
                print("following ",color[i])
                move_bot(params)
                params = detect_color_from_camera(color[i])
        else:
            print("reached Destination")
            w = Work.query.filter_by(source=source, destination=destination).first()
            db.session.delete(w)
            db.session.commit()
    return
