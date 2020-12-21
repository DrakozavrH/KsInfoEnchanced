package com.example.ksinfo.Model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

@IgnoreExtraProperties
public class AdditionalEducation implements Serializable {

    public String id;
    public String place;
    public String registration;
    public String status;
    public String teacher;
    public String text;
    public String title;

    public AdditionalEducation() {
    }

    public AdditionalEducation(String id, String place, String registration, String status, String teacher, String text, String title) {
        this.id = id;
        this.place = place;
        this.registration = registration;
        this.status = status;
        this.teacher = teacher;
        this.text = text;
        this.title = title;
    }

}
