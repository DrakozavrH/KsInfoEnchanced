package com.example.ksinfo.Model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

@IgnoreExtraProperties
public class AdditionalEducation implements Serializable {
<<<<<<< HEAD
    public String id;
=======
>>>>>>> origin/master
    public String place;
    public String registration;
    public String status;
    public String teacher;
    public String text;
    public String title;

    public AdditionalEducation() {
    }

<<<<<<< HEAD
    public AdditionalEducation(String id, String place, String registration, String status, String teacher, String text, String title) {
        this.id = id;
=======
    public AdditionalEducation(String place, String registration, String status, String teacher, String text, String title) {
>>>>>>> origin/master
        this.place = place;
        this.registration = registration;
        this.status = status;
        this.teacher = teacher;
        this.text = text;
        this.title = title;
    }

}
