package com.example.ksinfo;

import android.app.Application;
import android.content.DialogInterface;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ksinfo.Adapters.MyAdapter;
import com.example.ksinfo.Model.Events;
import com.example.ksinfo.Model.Item;
import com.example.ksinfo.Model.News;
import com.example.ksinfo.Model.User;
import com.example.ksinfo.Model.UserStatic;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ProfileActivity extends AppCompatActivity {

    private String Username ="";
    private TextView UsernameText;
    private TextView ProfileDescriptionText;
    private TextView ProfileDescriptionTextButton;
    private TextView TodayInfoText;

    private TextView NoNewsOrEventsText;

    //Переменные для левого меню
    RecyclerView list;
    RecyclerView.LayoutManager layoutManager;
    List<Item> items = new ArrayList<>();

    //Переменные для правого меню
    AlertDialog menuDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile);
        UsernameText = findViewById(R.id.userNameText);
        ProfileDescriptionText = findViewById(R.id.ProfileDescriptionText);
        ProfileDescriptionTextButton = findViewById(R.id.ProfileDescriptionTextButton);
        TodayInfoText = findViewById(R.id.TodayInfoText);
        NoNewsOrEventsText = findViewById(R.id.NoNewsOrEventsText);
        final LinearLayout header = findViewById(R.id.menuHeader);
        final ScrollView mainScroll = findViewById(R.id.mainScroll);
        final Button EventsButton = findViewById(R.id.EventsButton);
        final Button NewsButton = findViewById(R.id.NewsButton);

        if(GlobalApplication.loadingCount == 0) {
            header.setVisibility(View.GONE);
            mainScroll.setVisibility(View.GONE);
            Runnable r = new Runnable() {
                @Override
                public void run() {
                    header.setVisibility(View.VISIBLE);
                    mainScroll.setVisibility(View.VISIBLE);
                }
            };
            Handler h = new Handler();
            h.postDelayed(r, 3000);
            GlobalApplication.loadingCount = 1;
        }




        EventsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventsButton.setBackgroundColor(ContextCompat.getColor(getBaseContext(),R.color.KsBlue));
                NewsButton.setBackgroundColor(ContextCompat.getColor(getBaseContext(),R.color.KsLightBlue));
                inflateEvents();
            }
        });

        NewsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewsButton.setBackgroundColor(ContextCompat.getColor(getBaseContext(),R.color.KsBlue));
                EventsButton.setBackgroundColor(ContextCompat.getColor(getBaseContext(),R.color.KsLightBlue));
                inflateNews();
            }
        });


        ImageView headerImage = findViewById(R.id.menuHeaderImage);
        final DrawerLayout drawerLayout = findViewById(R.id.ProfileDrawerLayout);

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

        SetContent();

        //Правое меню
        ImageButton imageButton = findViewById(R.id.menuHeaderButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rightMenuDialog();
            }
        });




        Runnable r2 = new Runnable() {
            @Override
            public void run(){
                inflateEvents(); //<-- put your code in here.
            }
        };

        Handler h2 = new Handler();
        h2.postDelayed(r2, 2000);


    }

    //Заполнение правого меню
    private void rightMenuDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final CharSequence[] options = {"Настройки","Выход из аккаунта"};

        builder.setTitle("Меню").setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                switch (which) {
                    case 0: {
                        Intent intent = new Intent(ProfileActivity.this, SettingsActivity.class);
                        startActivity(intent);
                    }break;
                    case 1:{
                        Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }break;
                    default:

                }

            }
        });

        menuDialog = builder.create();
        menuDialog.show();
    }

    // Заполнение основной страницы
    private void SetContent() {

        Intent intent = getIntent();

        // Setting profile information

        if(UserStatic.role == 0){


            final TextView profileDescriptionButton = findViewById(R.id.ProfileDescriptionTextButton);
            if (profileDescriptionButton.getText().equals("Зарегистрироваться")){


                profileDescriptionButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent1 = new Intent(ProfileActivity.this,LoginActivity.class);
                        startActivity(intent1);
                    }
                });


            }else{

                profileDescriptionButton.setText(UserStatic.group);

                profileDescriptionButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
                        final CharSequence[] options = {"1-ИСП11-14","2-ИСП11-11","3-ИСП9-4","4-ИСП9-1"};

                        builder.setTitle("Выбор группы").setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                profileDescriptionButton.setText(options[which]);
                                UserStatic.group = options[which].toString();

                            }
                        });

                        menuDialog = builder.create();
                        menuDialog.show();

                    }
                });

            }



            if(intent != null){
                Runnable r = new Runnable() {
                    @Override
                    public void run(){
                        Username = UserStatic.Name;
                        UsernameText.setText(Username);
                    }
                };
                Handler h = new Handler();
                h.postDelayed(r, 2000);


            }

        }else if(UserStatic.role == 2){

            ProfileDescriptionText.setText("Гость");
            ProfileDescriptionTextButton.setText("Зарегистрироваться");

        }else if(UserStatic.role == 1){
            UsernameText.setText("Admin");
            ProfileDescriptionText.setText("Администратор");
            ProfileDescriptionTextButton.setText("");
        }


        // Setting current date
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);
        TodayInfoText.setText(formattedDate);


        //Setting default events
        NoNewsOrEventsText.setText("Нет данных о мероприятиях");


        //TODO убрать задержку после добавления загрузочного экрана
        Runnable r = new Runnable() {
            @Override
            public void run(){
                inflateEvents();
            }
        };

        Handler h = new Handler();
        h.postDelayed(r, 10);

    }


    // Заполнение левого меню
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

    


    //Заполнение мероприятий
    private void inflateEvents(){

        if(GlobalApplication.listEvents.isEmpty()){
            NoNewsOrEventsText.setText("Нет данных о мероприятиях");
        }else {
            NoNewsOrEventsText.setVisibility(View.GONE);
            LinearLayout newsOrEventsList = findViewById(R.id.NewsOrEventsList);
            LayoutInflater inflater = getLayoutInflater();


            List<Events> eventList = GlobalApplication.listEvents;
            Collections.sort(eventList, new Comparator<Events>() {
                @Override
                public int compare(Events object1, Events object2) {
                    String[] arr1 = object1.date.split(" ");
                    String[] arr2 = object2.date.split(" ");

                    return arr1[0].compareTo(arr2[0]);
                }
            });

            newsOrEventsList.removeAllViews();

            for (int i = 0; i < eventList.size(); i++) {
                View myLayout = inflater.inflate(R.layout.event_layout,newsOrEventsList,false);

                TextView eventDateNumber =(TextView)myLayout.findViewById(R.id.EventDateNumberTextView);
                TextView eventDateMonth = (TextView)myLayout.findViewById(R.id.EventDateMonthTextView);
                TextView eventTitle =(TextView)myLayout.findViewById(R.id.EventTitle);
                TextView eventDescription =(TextView)myLayout.findViewById(R.id.EventDescription);

                //eventTitle.setText(GlobalApplication.listEvents.get(i).title.toString());
                eventTitle.setClickable(true);
                eventTitle.setMovementMethod(LinkMovementMethod.getInstance());
                String title = MessageFormat.format("<a href={0}> {1} </a>",eventList.get(i).link,eventList.get(i).title);
                eventTitle.setText(Html.fromHtml(title));
                eventTitle.setLinkTextColor(getResources().getColor(R.color.KsOrange));


                eventDescription.setText(eventList.get(i).text.toString());


                String[] dateArray = eventList.get(i).date.split(" ");
                eventDateMonth.setText(dateArray[1].toString());
                eventDateNumber.setText(dateArray[0].toString());


                newsOrEventsList.addView(myLayout);
            }

        }



    }


    private void inflateNews(){

        if(GlobalApplication.listNews.isEmpty()){
            NoNewsOrEventsText.setText("Нет данных о новостях");
        }else {
            NoNewsOrEventsText.setVisibility(View.GONE);
            LinearLayout newsOrEventsList = findViewById(R.id.NewsOrEventsList);
            LayoutInflater inflater = getLayoutInflater();


            List<News> newsList = GlobalApplication.listNews;
            newsOrEventsList.removeAllViews();



            for (int i = 0; i < newsList.size(); i++) {
                View myLayout = inflater.inflate(R.layout.news_layout,newsOrEventsList,false);

                TextView newsTitle = (TextView)myLayout.findViewById(R.id.NewsTitleTextView);
                TextView newsDate = (TextView)myLayout.findViewById(R.id.NewsDateTextView);

                //eventTitle.setText(GlobalApplication.listEvents.get(i).title.toString());
                newsTitle.setClickable(true);
                newsTitle.setMovementMethod(LinkMovementMethod.getInstance());
                String title = MessageFormat.format("<a href={0}> {1} </a>",newsList.get(i).link,newsList.get(i).text);
                newsTitle.setText(Html.fromHtml(title));
                newsTitle.setLinkTextColor(getResources().getColor(R.color.KsOrange));


                newsDate.setText(newsList.get(i).date.toString());


                newsOrEventsList.addView(myLayout);
            }

        }

    }

}
