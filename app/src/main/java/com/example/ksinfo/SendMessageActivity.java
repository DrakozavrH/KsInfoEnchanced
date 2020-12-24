package com.example.ksinfo;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ksinfo.Model.Message;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SendMessageActivity extends AppCompatActivity {
    TextView headView;
    TextView textView;

    //Переменные для правого меню
    AlertDialog menuDialog;

    String MessageFor = "All";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);

        headView = findViewById(R.id.PhoneNumberEditText);
        textView = findViewById(R.id.MessageTextEditText);

        //Правое меню
        ImageButton imageButton = findViewById(R.id.menuHeaderButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rightMenuDialog();
            }
        });

        // Левая кнопка
        ImageView leftButton = findViewById(R.id.menuHeaderImage);
        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button submitMessageButton = findViewById(R.id.SubmitMessageButton);
        submitMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO проверки и отправка сообщения в бд
                saveMessage();
                

                finish();
            }
        });

        final Button forAllButton = findViewById(R.id.ForAllButton);
        final Button forGroupButton = findViewById(R.id.ForGroupButton);
        forAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forGroupButton.setBackgroundColor(getResources().getColor(R.color.KsLightBlue));
                forAllButton.setBackgroundColor(getResources().getColor(R.color.KsBlue));
                MessageFor = "All";
            }
        });

        forGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forGroupButton.setBackgroundColor(getResources().getColor(R.color.KsBlue));
                forAllButton.setBackgroundColor(getResources().getColor(R.color.KsLightBlue));

                chooseGroupDialog();
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
                        Intent intent = new Intent(SendMessageActivity.this, SettingsActivity.class);
                        startActivity(intent);
                    }break;
                    case 1:{
                        Intent intent = new Intent(SendMessageActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }break;
                    default:

                }

            }
        });

        menuDialog = builder.create();
        menuDialog.show();
    }

    private void chooseGroupDialog(){

        //TODO добавить получение списка групп из бд


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final CharSequence[] options = {"1-ИСП11-14","2-ИСП11-11","3-ИСП9-4","4-ИСП9-1"};

        builder.setTitle("Выбор группы").setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Button button = findViewById(R.id.ForGroupButton);
                button.setText(options[which]);
                MessageFor = options[which].toString();

            }
        });

        menuDialog = builder.create();
        menuDialog.show();

    }

    private void saveMessage(){
        int number = GlobalApplication.listMes.size();
        int numberid = GlobalApplication.listMes.get(number-1).id;
        String[] mes = new String[]{"Mes1","Mes2","Mes3","Mes4","Mes5","Mes6","Mes7","Mes8","Mes9","Mes10"};

        String head = headView.getText().toString();
        String text = textView.getText().toString();
        String timeStamp = new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime());


        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("Message");
        Map<String, Message> messageMap = new HashMap<>();
        String mesName = "Mes" + String.valueOf(numberid);
        ref.child(mesName).setValue(new Message(numberid + 1,head, text, timeStamp, MessageFor));

        //////////////////////////
        GlobalApplication.listMes.clear();
        //////////////////////////
        LoginActivity.MessageMet();
    }
}

