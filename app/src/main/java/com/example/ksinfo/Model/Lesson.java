package com.example.ksinfo.Model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
@IgnoreExtraProperties
public class Lesson implements Serializable {
    public String lesson_1;
    public String lesson_2;
    public String lesson_3;
    public String lesson_4;
    public String lesson_5;
    public String lesson_6;
    public String lesson_7;
    public String lesson_8;

    public Lesson() {
    }

    public Lesson(String lesson_1, String lesson_2, String lesson_3, String lesson_4, String lesson_5, String lesson_6, String lesson_7, String lesson_8) {
        this.lesson_1 = lesson_1;
        this.lesson_2 = lesson_2;
        this.lesson_3 = lesson_3;
        this.lesson_4 = lesson_4;
        this.lesson_5 = lesson_5;
        this.lesson_6 = lesson_6;
        this.lesson_7 = lesson_7;
        this.lesson_8 = lesson_8;
    }
}
