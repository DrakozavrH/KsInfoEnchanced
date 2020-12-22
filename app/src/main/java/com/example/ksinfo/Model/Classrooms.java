package com.example.ksinfo.Model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

@IgnoreExtraProperties
public class Classrooms implements Serializable {
    public String classroom_1;
    public String classroom_2;
    public String classroom_3;
    public String classroom_4;
    public String classroom_5;
    public String classroom_6;
    public String classroom_7;
    public String classroom_8;

    public Classrooms() {
    }

    public Classrooms(String classroom_1, String classroom_2, String classroom_3, String classroom_4, String classroom_5, String classroom_6, String classroom_7, String classroom_8) {
        this.classroom_1 = classroom_1;
        this.classroom_2 = classroom_2;
        this.classroom_3 = classroom_3;
        this.classroom_4 = classroom_4;
        this.classroom_5 = classroom_5;
        this.classroom_6 = classroom_6;
        this.classroom_7 = classroom_7;
        this.classroom_8 = classroom_8;
    }
}
