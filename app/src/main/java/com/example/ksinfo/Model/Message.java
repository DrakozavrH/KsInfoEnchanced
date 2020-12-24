package com.example.ksinfo.Model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

@IgnoreExtraProperties
public class Message implements Serializable {
    public int id;
    public String head;
    public String text;
    public String time;
    public String groupBD;

    public Message() {
    }

    public Message(int id, String head, String text, String time, String groupBD) {
        this.id = id;
        this.head = head;
        this.text = text;
        this.time = time;
        this.groupBD = groupBD;
    }
}
