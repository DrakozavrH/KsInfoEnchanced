package com.example.ksinfo;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class SendMessageActivity extends AppCompatActivity {

    //Переменные для правого меню
    AlertDialog menuDialog;

    String MessageFor = "All";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);

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
        final CharSequence[] options = {"1-АСС11-3", "1-АСС9-5ВБ", "1-ИСП11-14", "1-ИСП11-15", "1-ИСП11-16", "1-ИСП11-17", "1-ИСП9-15ВБ", "1-ИСП9-16ВБ", "1-ИСП9-17ВБ", "1-ОСАТ11-5", "1-ОСАТ9-6", "1-ОСАТ9-7ВБ", "2-АСС11-2", "2-АСС9-2", "2-ИСП11-10", "2-ИСП11-11", "2-ИСП11-12", "2-ИСП11-13", "2-ИСП9-10-ВБ", "2-ИСП9-7", "2-ИСП9-8", "2-ИСП9-9-ВБ", "2-КС11-7", "2-КС9-9", "2-ОСАТ11-3", "2-ОСАТ11-4", "2-ОСАТ9-4", "2-ОСАТ9-5", "3-АСС11-1", "3-АСС9-1", "3-ИСП11-5", "3-ИСП11-6", "3-ИСП11-7", "3-ИСП11-8", "3-ИСП9-3", "3-ИСП9-4", "3-ИСП9-5-ВБ", "3-ИСП9-6-ВБ", "3-КС11-5", "3-КС11-6", "3-КС9-7", "3-КС9-8-ВБ", "3-ОСАТ11-1", "3-ОСАТ11-2", "3-ОСАТ9-2", "3-ОСАТ9-3", "4-ИСП9-1", "4-ИСП9-2-ВБ", "4-ОСАТ9-1", "4-ПКС9-3-ВБ", "4-ПКС9-4-ВБ"};

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

}

