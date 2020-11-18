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

public class SubjectListActivity extends AppCompatActivity {

    AlertDialog menuDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_subject_list);

        ImageButton menuHeaderButton = findViewById(R.id.menuHeaderButton);
        menuHeaderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlert();
            }
        });

        Button SphilosophyButton = findViewById(R.id.SPhilosophyButton);
        Button S0103Button = findViewById(R.id.S0103Button);
        Button S0402Button = findViewById(R.id.S0402ISPButton);
        Button S1101button = findViewById(R.id.S1101Button);
        Button sBjButton = findViewById(R.id.sbjButton);
        Button s0101Button = findViewById(R.id.s0101Button);

        SphilosophyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SubjectListActivity.this,SubjectActivity.class);

                intent.putExtra("Subject","sPhil");

                startActivity(intent);

            }
        });


        S0103Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SubjectListActivity.this,SubjectActivity.class);

                intent.putExtra("Subject","s0103");

                startActivity(intent);
            }
        });

        S0402Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SubjectListActivity.this,SubjectActivity.class);

                intent.putExtra("Subject","s0402");

                startActivity(intent);
            }
        });

        S1101button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SubjectListActivity.this,SubjectActivity.class);

                intent.putExtra("Subject","s1101");

                startActivity(intent);
            }
        });

        sBjButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SubjectListActivity.this,SubjectActivity.class);

                intent.putExtra("Subject","sBj");

                startActivity(intent);
            }
        });

        s0101Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SubjectListActivity.this,SubjectActivity.class);

                intent.putExtra("Subject","s0101");

                startActivity(intent);
            }
        });


    }


    private void showAlert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final CharSequence[] options = {"Расписание","Кабинеты","Информация"};

        builder.setTitle("Перейти").setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(which==0){
                    Intent intent = new Intent(SubjectListActivity.this,ScheduleActivity.class);
                    startActivity(intent);
                }else if(which ==1 ){
                    Intent intent = new Intent(SubjectListActivity.this,CabinetListActivity.class);
                    startActivity(intent);

                }else if(which ==2){
                    Intent intent = new Intent(SubjectListActivity.this,MainActivity.class);
                    startActivity(intent);
                }



            }
        });


        menuDialog = builder.create();
        menuDialog.show();

    }

}
