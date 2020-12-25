package com.example.ksinfo;

import android.app.Application;

import com.example.ksinfo.Model.AdditionalEducation;

import com.example.ksinfo.Model.AdditionalEducation;
import com.example.ksinfo.Model.Changes;
import com.example.ksinfo.Model.Classrooms;
import com.example.ksinfo.Model.Events;
import com.example.ksinfo.Model.Lesson;
import com.example.ksinfo.Model.Message;
import com.example.ksinfo.Model.News;

import java.util.ArrayList;
import java.util.List;

public  class GlobalApplication extends Application {

    //Выбранная пользователем группа
    private String groupName = "testGroupName";

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    //хранение кружков
    public static List<AdditionalEducation> listAdd = new ArrayList<>();
    //хранение мероприятий
    public static List<Events> listEvents = new ArrayList<>();
    //хранение Новостей
    public static List<News> listNews = new ArrayList<>();
    //хранение расписания(название урока)
    public static List<List<Lesson>> listScheduleLesson = new ArrayList<>();
    //хранение расписания(класс)
    public static List<List<Classrooms>> listScheduleClassrooms = new ArrayList<>();
    //хранение сообщений
    public static List<Message> listMes = new ArrayList<>();
    //хранение изменений
    public static List<Changes> listChanges = new ArrayList<>();

    public static int loadingCount = 0;

}
