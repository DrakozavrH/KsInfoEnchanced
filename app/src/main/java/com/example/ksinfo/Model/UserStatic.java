package com.example.ksinfo.Model;

public class UserStatic {
    public static String email;
    public static String Name;
    public static String Patronymic;
    public static String Surname;
    public static String group;
    public static int role = 2;

    public UserStatic(){

    }

    public  UserStatic(String Name, String Patronymic, String Surname, String email, String group, int role){
        this.Name = Name;
        this.Patronymic = Patronymic;
        this.Surname = Surname;
        this.email = email;
        this.group = group;
        this.role = role;
    }
}
