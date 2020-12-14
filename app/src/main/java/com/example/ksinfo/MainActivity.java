package com.example.ksinfo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    AlertDialog menuDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton menuHeaderButton = findViewById(R.id.menuHeaderButton);

        menuHeaderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlert();
            }
        });
    }


    private void showAlert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final CharSequence[] options = {"Расписание","Кабинеты","Предметы"};

        builder.setTitle("Перейти").setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {



            }
        });


        menuDialog = builder.create();
        menuDialog.show();

    }


}