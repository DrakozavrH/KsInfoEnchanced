package com.example.ksinfo;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ksinfo.Adapters.MyAdapter;
import com.example.ksinfo.Model.Item;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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


    RecyclerView list;
    RecyclerView.LayoutManager layoutManager;
    List<Item> items = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile);
        UsernameText = findViewById(R.id.userNameText);
        ProfileDescriptionText = findViewById(R.id.ProfileDescriptionText);
        ProfileDescriptionTextButton = findViewById(R.id.ProfileDescriptionTextButton);
        TodayInfoText = findViewById(R.id.TodayInfoText);
        NoNewsOrEventsText = findViewById(R.id.NoNewsOrEventsText);


        final Button EventsButton = findViewById(R.id.EventsButton);
        final Button NewsButton = findViewById(R.id.NewsButton);




        EventsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventsButton.setBackgroundColor(ContextCompat.getColor(getBaseContext(),R.color.KsBlue));
                NewsButton.setBackgroundColor(ContextCompat.getColor(getBaseContext(),R.color.KsLightBlue));

                //TODO получение мероприятий из бд в виде числа, месяца, текста ссылки, адреса ссылки и описания

                NoNewsOrEventsText.setText("Нет данных о мероприятиях");

            }
        });

        NewsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewsButton.setBackgroundColor(ContextCompat.getColor(getBaseContext(),R.color.KsBlue));
                EventsButton.setBackgroundColor(ContextCompat.getColor(getBaseContext(),R.color.KsLightBlue));

                //TODO получение новостей из бд в виде даты, текста ссылки и адреса ссылки

                NoNewsOrEventsText.setText("Нет данных о новостях");

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



    }

    private void SetContent() {

        Intent intent = getIntent();

        // Setting profile information

        if(((GlobalApplication) getApplication()).getLoginStatus().equals("User")){

            if(intent != null){
                Username = intent.getStringExtra("name");
            }

            UsernameText.setText(Username);

        }else if(((GlobalApplication) getApplication()).getLoginStatus().equals("Guest")){

            ProfileDescriptionText.setText("");
            ProfileDescriptionTextButton.setText("Зарегистрироваться");

        }else if(((GlobalApplication) getApplication()).getLoginStatus().equals("Admin")){
            UsernameText.setText("AdminName");
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

        //TODO получение мероприятий из бд в виде числа, месяца, текста ссылки, адреса ссылки и описания
        NoNewsOrEventsText.setText("Нет данных о мероприятиях");





    }




    private void setData() {

        for (int i = 0; i < 20 ; i++) {

            if(i%2 ==0){
                Item item = new Item("Доп. образование","Child item" + (i+1),true,R.drawable.notificontest3,1);
                items.add(item);
            }else{
                Item item = new Item("Доп. образование","",false,R.drawable.notificontest4,2);
                items.add(item);
            }

        }

        MyAdapter adapter = new MyAdapter(items);
        list.setAdapter(adapter);


    }







}
