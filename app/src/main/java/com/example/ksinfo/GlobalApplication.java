package com.example.ksinfo;

import android.app.Application;

import com.example.ksinfo.Model.AdditionalEducation;

import com.example.ksinfo.Model.AdditionalEducation;
import com.example.ksinfo.Model.Days;
import com.example.ksinfo.Model.Events;
import com.example.ksinfo.Model.News;

import java.util.ArrayList;
import java.util.List;

public  class GlobalApplication extends Application {

    // Статус пользователя(Гость/Студент/Админ)
    private String loginStatus;
    //Имя пользователя(для отображения при переходе в личный кабинет с любой страницы)
    private String userName;
    //Выбранная пользователем группа
    private String groupName = "testGroupName";

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getLoginStatus(){
        return loginStatus;
    }


    public void setLoginStatus(String loginVariable){
        this.loginStatus = loginVariable;
    }

    //хранение кружков
    public static List<AdditionalEducation> listAdd = new ArrayList<>();
    //хранение мероприятий
    public static List<Events> listEvents = new ArrayList<>();
    //хранение Новостей
    public static List<News> listNews = new ArrayList<>();
    //хранение расписания(название урока)





}
