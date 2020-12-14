package com.example.ksinfo;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class ScheduleActivity extends AppCompatActivity {

    AlertDialog menuDialog;

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


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }



}
