package com.example.ksinfo.Model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

@IgnoreExtraProperties
public class Events implements Serializable {
    public String date;
    public String link;
    public String text;
    public String title;

    public Events() {
    }

    public Events(String date, String link, String text, String title) {
        this.date = date;
        this.link = link;
        this.text = text;
        this.title = title;
    }
}
