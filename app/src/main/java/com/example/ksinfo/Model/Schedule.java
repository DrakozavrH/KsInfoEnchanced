package com.example.ksinfo.Model;

import java.util.ArrayList;
import java.util.List;

public class Schedule {
    //хранение расписания(название урока)
    public static List<List<Lesson>> listScheduleLesson = new ArrayList<>();
    //хранение расписания(класс)
    public static List<List<Classrooms>> listScheduleClassrooms = new ArrayList<>();
}
