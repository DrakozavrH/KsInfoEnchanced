package com.example.ksinfo.Model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

@IgnoreExtraProperties
public class User implements Serializable {

    public String email;
    public String Name;
    public String Patronymic;
    public String Surname;
    public String group;
    public String groupBD;
    public int role;

    public User(){
    }

    public  User(String Name, String Patronymic, String Surname, String email, String group, String groupBD, int role){
        this.Name = Name;
        this.Patronymic = Patronymic;
        this.Surname = Surname;
        this.email = email;
        this.group = group;
        this.groupBD = groupBD;
        this.role = role;
    }

}
