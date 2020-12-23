package com.example.ksinfo;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ksinfo.Adapters.MyAdapter;
import com.example.ksinfo.Model.Events;
import com.example.ksinfo.Model.Item;
import com.example.ksinfo.Model.Lesson;
import com.example.ksinfo.Model.News;
import com.example.ksinfo.Model.Schedule;
import com.example.ksinfo.Model.UserStatic;

import org.w3c.dom.Text;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ScheduleActivity extends AppCompatActivity {

    //Переменные для правого меню
    AlertDialog menuDialog;

    //Переменные для левого меню
    RecyclerView list;
    RecyclerView.LayoutManager layoutManager;
    List<Item> items = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        ImageButton menuHeaderButton = findViewById(R.id.menuHeaderButton);
        final Button changeGroupButton = findViewById(R.id.changeGroupButton);

        menuHeaderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlert();
            }
        });
        changeGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeGroupAlert();
            }
        });


        Button[] subjectButtons = new Button[17];
        for (int i = 0; i <17 ; i++) {
            int id = getResources().getIdentifier("subjectButton"+i, "id", getPackageName());
            subjectButtons[i] = (Button)findViewById(id);
        }


        //TODO добавить изменение расписание в зависимости от выбранной в профиле группы

        //Удаление кнопки со сменой группы, если пользователь зарегистрирован
        if(UserStatic.role == 0 && ((GlobalApplication)getApplication()).getGroupName() != null){
            LinearLayout layout = findViewById(R.id.changeGroupLayout);
            layout.setVisibility(View.GONE);

            ScrollView scrollView = findViewById(R.id.mainScroll);
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams)scrollView.getLayoutParams();
            lp.setMargins(0, 0, 0, 0);
            scrollView.setLayoutParams(lp);

        }

        // Заполнение левого меню
        ImageView headerImage = findViewById(R.id.menuHeaderImage);
        final DrawerLayout drawerLayout = findViewById(R.id.ScheduleDrawerLayout);
        headerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.LEFT);

                list =(RecyclerView) findViewById(R.id.recycler);

                if(list.getChildCount() == 0){
                    list.setHasFixedSize(true);
                    layoutManager = new LinearLayoutManager(getBaseContext());
                    list.setLayoutManager(layoutManager);
                    setData();
                }


            }
        });

        //Правое меню
        ImageButton imageButton = findViewById(R.id.menuHeaderButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rightMenuDialog();
            }
        });


        inflateSchedule();




    }

    private void showAlert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final CharSequence[] options = {"Информация","Кабинеты","Предметы"};

        builder.setTitle("Перейти").setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        menuDialog = builder.create();
        menuDialog.show();
    }

    private void changeGroupAlert(){

        //TODO добавить получение списка групп из бд и изменение расписание в зависимости от выбранной в этом диалоге группы


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final CharSequence[] options = {"1-АСС11-3", "1-АСС9-5ВБ", "1-ИСП11-14", "1-ИСП11-15", "1-ИСП11-16", "1-ИСП11-17", "1-ИСП9-15ВБ", "1-ИСП9-16ВБ", "1-ИСП9-17ВБ", "1-ОСАТ11-5", "1-ОСАТ9-6", "1-ОСАТ9-7ВБ", "2-АСС11-2", "2-АСС9-2", "2-ИСП11-10", "2-ИСП11-11", "2-ИСП11-12", "2-ИСП11-13", "2-ИСП9-10-ВБ", "2-ИСП9-7", "2-ИСП9-8", "2-ИСП9-9-ВБ", "2-КС11-7", "2-КС9-9", "2-ОСАТ11-3", "2-ОСАТ11-4", "2-ОСАТ9-4", "2-ОСАТ9-5", "3-АСС11-1", "3-АСС9-1", "3-ИСП11-5", "3-ИСП11-6", "3-ИСП11-7", "3-ИСП11-8", "3-ИСП9-3", "3-ИСП9-4", "3-ИСП9-5-ВБ", "3-ИСП9-6-ВБ", "3-КС11-5", "3-КС11-6", "3-КС9-7", "3-КС9-8-ВБ", "3-ОСАТ11-1", "3-ОСАТ11-2", "3-ОСАТ9-2", "3-ОСАТ9-3", "4-ИСП9-1", "4-ИСП9-2-ВБ", "4-ОСАТ9-1", "4-ПКС9-3-ВБ", "4-ПКС9-4-ВБ"};

        builder.setTitle("Выбор группы").setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Button button = findViewById(R.id.changeGroupButton);
                button.setText(options[which]);

            }
        });

        menuDialog = builder.create();
        menuDialog.show();
    }


    private void rightMenuDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final CharSequence[] options = {"Настройки","Выход из аккаунта"};

        builder.setTitle("Меню").setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                switch (which) {
                    case 0: {
                        Intent intent = new Intent(ScheduleActivity.this, SettingsActivity.class);
                        startActivity(intent);
                    }break;
                    case 1:{
                        Intent intent = new Intent(ScheduleActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }break;
                    default:

                }

            }
        });

        menuDialog = builder.create();
        menuDialog.show();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void setData() {

        //Количество кноппок (-1)
        int buttonAmount = 9;

        // Добавление кнопки с сообщениями для администратора (+1)
        if(UserStatic.role == 1){
            buttonAmount = 10;
        }


        for (int i = 0; i < buttonAmount ; i++) {

            // Кнопка в левом меню
            Item item;

            //Пример заполнения
            /*if(i%2 ==0){
                Item item = new Item("Доп. образование","Child item" + (i+1),true,R.drawable.notificontest3,1);
                items.add(item);
            }else{
                Item item = new Item("Доп. образование","",false,R.drawable.notificontest4,2);
                items.add(item);
            }*/

            //С регистрацией
            if (UserStatic.role == 0) {

                switch (i) {

                    case 0: {
                        item = new Item("Личный кабинет", "", false, R.drawable.userimage, 0);
                    }
                    break;
                    case 1: {
                        item = new Item("Уведомления", "", false, R.drawable.notificontest3, 1);
                    }
                    break;
                    case 2: {
                        item = new Item("Расписание", "", false, R.drawable.scheduleicon1, 2);
                    }
                    break;
                    case 3: {
                        item = new Item("Замены", "", false, R.drawable.changesicon1, 3);
                    }
                    break;
                    case 4: {
                        item = new Item("Доп. образование", "Кружки", true, R.drawable.educationicon1, 4);
                    }
                    break;
                    case 5: {
                        item = new Item("Мои документы", "Мои документы", true, R.drawable.documentsicon1, 5);
                    }
                    break;
                    case 6: {
                        item = new Item("Задать вопрос", "", false, R.drawable.questionsicon1, 6);
                    }
                    break;
                    case 7: {
                        item = new Item("Студсовет", "", false, R.drawable.studentcouncilicon1, 7);
                    }
                    break;
                    case 8: {
                        item = new Item("Психолог", "", false, R.drawable.psychologisticon1, 8);
                    }
                    break;
                    default: {
                        item = new Item("Error", "", false, R.drawable.notificontest4, 1);
                    }
                    break;


                }

                items.add(item);

                // Без регистрации
            }else if(UserStatic.role == 2){

                switch (i) {

                    case 0: {
                        item = new Item("Личный кабинет", "", false, R.drawable.userimage, 0);
                    }
                    break;
                    case 1: {
                        item = new Item("Уведомления", "", false, R.drawable.notificontest3, 1);
                    }
                    break;
                    case 2: {
                        item = new Item("Расписание", "", false, R.drawable.scheduleicon1, 2);
                    }
                    break;
                    case 3: {
                        item = new Item("Замены", "", false, R.drawable.changesicon1, 10);
                    }
                    break;
                    case 4: {
                        item = new Item("Доп. образование", "Кружки", true, R.drawable.educationicon1, 10);
                    }
                    break;
                    case 5: {
                        item = new Item("Мои документы", "Мои документы", true, R.drawable.documentsicon1, 10);
                    }
                    break;
                    case 6: {
                        item = new Item("Задать вопрос", "", false, R.drawable.questionsicon1, 10);
                    }
                    break;
                    case 7: {
                        item = new Item("Студсовет", "", false, R.drawable.studentcouncilicon1, 10);
                    }
                    break;
                    case 8: {
                        item = new Item("Психолог", "", false, R.drawable.psychologisticon1, 10);
                    }
                    break;
                    default: {
                        item = new Item("Error", "", false, R.drawable.notificontest4, 1);
                    }
                    break;


                }

                items.add(item);

                //Администратор
            }else if(UserStatic.role == 1){

                switch (i) {

                    case 0: {
                        item = new Item("Личный кабинет", "", false, R.drawable.userimage, 0);
                    }
                    break;
                    case 1: {
                        item = new Item("Уведомления", "", false, R.drawable.notificontest3, 1);
                    }
                    break;
                    case 2: {
                        item = new Item("Расписание", "", false, R.drawable.scheduleicon1, 2);
                    }
                    break;
                    case 3: {
                        item = new Item("Замены", "", false, R.drawable.changesicon1, 3);
                    }
                    break;
                    case 4: {
                        item = new Item("Доп. образование", "Кружки", true, R.drawable.educationicon1, 4);
                    }
                    break;
                    case 5: {
                        item = new Item("Мои документы", "Мои документы", true, R.drawable.documentsicon1, 5);
                    }
                    break;
                    case 6: {
                        item = new Item("Задать вопрос", "", false, R.drawable.questionsicon1, 6);
                    }
                    break;
                    case 7: {
                        item = new Item("Студсовет", "", false, R.drawable.studentcouncilicon1, 7);
                    }
                    break;
                    case 8: {
                        item = new Item("Психолог", "", false, R.drawable.psychologisticon1, 8);
                    }
                    break;
                    case 9:{
                        item = new Item("Сообщения", "", false, R.drawable.messagesicon1, 9);
                    }
                    break;
                    default: {
                        item = new Item("Error", "", false, R.drawable.notificontest4, 1);
                    }
                    break;


                }

                items.add(item);
            }

        }


        MyAdapter adapter = new MyAdapter(items);
        list.setAdapter(adapter);


    }

    private void inflateSchedule(){


        if(Schedule.listScheduleLesson.isEmpty()){
            Log.d("Empty list","Lessons is empty");
        }else {

            LinearLayout mainLinearLayout = findViewById(R.id.mainLinearLayout);
            LayoutInflater inflater = getLayoutInflater();


            List<List<Lesson>> lessonList = Schedule.listScheduleLesson;
            Collections.sort(lessonList, new Comparator<List<Lesson>>() {
                @Override
                public int compare(List<Lesson> o1, List<Lesson> o2) {
                    return o1.getClass().getName().compareTo(o2.getClass().getName());
                }
            });



            for (int i = 0; i < Schedule.listScheduleLesson.get(3).size(); i++) {
                View myLayout = inflater.inflate(R.layout.scedule_day_layout,mainLinearLayout,false);

                Button lesson1Button =(Button)myLayout.findViewById(R.id.CardSubject1);
                Button lesson2Button =(Button)myLayout.findViewById(R.id.CardSubject2);
                Button lesson3Button =(Button)myLayout.findViewById(R.id.CardSubject3);
                Button lesson4Button =(Button)myLayout.findViewById(R.id.CardSubject4);

                for (int j = 0; j < 4; j++) {

                    switch(j){

                        case 0:{

                            if(Schedule.listScheduleLesson.get(3).get(i).lesson_1.equals("-")){
                                TableRow tableRow1 = (TableRow)myLayout.findViewById(R.id.TableRow1);
                                tableRow1.setVisibility(View.GONE);

                            }else if (Schedule.listScheduleLesson.get(3).get(i).lesson_1.equals(Schedule.listScheduleLesson.get(3).get(i).lesson_2)){
                                lesson1Button.setText(Schedule.listScheduleLesson.get(3).get(i).lesson_1);
                            }else{
                                String string = new String(Schedule.listScheduleLesson.get(3).get(i).lesson_1 + "/" +Schedule.listScheduleLesson.get(3).get(i).lesson_2);
                                lesson1Button.setText(string);
                            }

                        }break;
                        case 1:{
                            if(Schedule.listScheduleLesson.get(3).get(i).lesson_3.equals("-")){
                                TableRow tableRow2 = (TableRow)myLayout.findViewById(R.id.TableRow2);
                                tableRow2.setVisibility(View.GONE);

                            }else if (Schedule.listScheduleLesson.get(3).get(i).lesson_3.equals(Schedule.listScheduleLesson.get(3).get(i).lesson_4)){
                                lesson2Button.setText(Schedule.listScheduleLesson.get(3).get(i).lesson_3);
                            }else{
                                String string = new String(Schedule.listScheduleLesson.get(3).get(i).lesson_3 + "/" +Schedule.listScheduleLesson.get(3).get(i).lesson_4);
                                lesson2Button.setText(string);
                            }
                        }break;
                        case 2:{
                            if(Schedule.listScheduleLesson.get(3).get(i).lesson_5.equals("-")){
                                TableRow tableRow3 = (TableRow)myLayout.findViewById(R.id.TableRow3);
                                tableRow3.setVisibility(View.GONE);

                            }else if (Schedule.listScheduleLesson.get(3).get(i).lesson_5.equals(Schedule.listScheduleLesson.get(3).get(i).lesson_6)){
                                lesson3Button.setText(Schedule.listScheduleLesson.get(3).get(i).lesson_5);
                            }else{
                                String string = new String(Schedule.listScheduleLesson.get(3).get(i).lesson_5 + "/" +Schedule.listScheduleLesson.get(3).get(i).lesson_6);
                                lesson3Button.setText(string);
                            }
                        }break;
                        case 3:{
                            if(Schedule.listScheduleLesson.get(3).get(i).lesson_7.equals("-")){
                                TableRow tableRow4 = (TableRow)myLayout.findViewById(R.id.TableRow4);
                                tableRow4.setVisibility(View.GONE);

                            }else if (Schedule.listScheduleLesson.get(3).get(i).lesson_7.equals(Schedule.listScheduleLesson.get(3).get(i).lesson_8)){
                                lesson4Button.setText(Schedule.listScheduleLesson.get(3).get(i).lesson_7);
                            }else{
                                String string = new String(Schedule.listScheduleLesson.get(3).get(i).lesson_7 + "/" +Schedule.listScheduleLesson.get(3).get(i).lesson_8);
                                lesson4Button.setText(string);
                            }
                        }break;
                        default:{
                            Log.d("idu","idu");
                        }

                    }


                }

                TextView header =(TextView)myLayout.findViewById(R.id.ScheduleTableHeaderTexView);
                switch (i){
                    case 0:{
                        header.setText("Понедельник");
                    }break;
                    case 1:{
                        header.setText("Вторник");
                    }break;
                    case 2:{
                        header.setText("Среда");
                    }break;
                    case 3:{
                        header.setText("Четверг");
                    }break;
                    case 4:{
                        header.setText("Пятница");
                    }break;
                    case 5:{
                        header.setText("Суббота");
                    }break;
                    default:{
                        header.setText("Ошибка");
                    }
                }








                mainLinearLayout.addView(myLayout);
            }

        }


    }


}
