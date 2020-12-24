package com.example.ksinfo.Model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
@IgnoreExtraProperties
public class Changes implements Serializable {
    public String canceled;
    public String couple;
    public String group;
    public String replacement;

    public Changes() {
    }

    public Changes(String canceled, String couple, String group, String replacement) {
        this.canceled = canceled;
        this.couple = couple;
        this.group = group;
        this.replacement = replacement;
    }
}
