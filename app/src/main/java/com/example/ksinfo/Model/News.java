package com.example.ksinfo.Model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

@IgnoreExtraProperties
public class News implements Serializable {
    public String date;
    public String link;
    public String text;

    public News() {
    }

    public News(String date, String link, String text) {
        this.date = date;
        this.link = link;
        this.text = text;
    }
}
