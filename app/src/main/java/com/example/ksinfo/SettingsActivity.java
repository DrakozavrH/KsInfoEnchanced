package com.example.ksinfo;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ksinfo.Adapters.MyAdapter;
import com.example.ksinfo.Model.Item;

import java.util.ArrayList;
import java.util.List;

public class SettingsActivity extends AppCompatActivity {

    //Переменные для левого меню
    RecyclerView list;
    RecyclerView.LayoutManager layoutManager;
    List<Item> items = new ArrayList<>();

    //Переменные для правого меню
    AlertDialog menuDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Заполнение левого меню
        ImageView headerImage = findViewById(R.id.menuHeaderImage);
        final DrawerLayout drawerLayout = findViewById(R.id.SettingsDrawerLayout);
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

    }

    private void rightMenuDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final CharSequence[] options = {"Настройки","Выход из аккаунта"};

        builder.setTitle("Меню").setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                switch (which) {
                    case 0: {
                        Intent intent = new Intent(SettingsActivity.this, SettingsActivity.class);
                        startActivity(intent);
                    }break;
                    case 1:{
                        Intent intent = new Intent(SettingsActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }break;
                    default:

                }

            }
        });

        menuDialog = builder.create();
        menuDialog.show();
    }

    private void setData() {

        //Количество кноппок (-1)
        int buttonAmount = 9;

        // Добавление кнопки с сообщениями для администратора (+1)
        if(((GlobalApplication) getApplication()).getLoginStatus().equals("Admin")){
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
            if (((GlobalApplication) getApplication()).getLoginStatus().equals("User")) {

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
            }else if(((GlobalApplication) getApplication()).getLoginStatus().equals("Guest")){

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
                        item = new Item("Доп. образование", "Кружки", true, R.drawable.educationicon1, 4);
                    }
                    break;
                    case 5: {
                        item = new Item("Мои документы", "Мои документы", true, R.drawable.documentsicon1, 5);
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
            }else if(((GlobalApplication) getApplication()).getLoginStatus().equals("Admin")){

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


}
