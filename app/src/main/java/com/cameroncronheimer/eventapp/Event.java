package com.cameroncronheimer.eventapp;

import io.realm.RealmObject;

public class Event extends RealmObject{
    String title;
    String date;
    String time;
    String location;
    long datetime;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setDateTime(long datetime){ this.datetime = datetime; }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}

