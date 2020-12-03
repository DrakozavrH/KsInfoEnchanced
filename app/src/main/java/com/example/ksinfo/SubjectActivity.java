package com.example.ksinfo;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class SubjectActivity extends AppCompatActivity {

    AlertDialog menuDialog;

    private ImageView subjectImage;
    private TextView subjectDescription;
    private String Subject;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_subject);

        subjectImage = findViewById(R.id.subjectImage);
        subjectDescription = findViewById(R.id.subjectDescription);

        ImageButton headerButton = findViewById(R.id.menuHeaderButton);
        headerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlert();
            }
        });


        SetContent();

    }

    private void showAlert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final CharSequence[] options = {"Расписание","Кабинеты","Информация"};

        builder.setTitle("Перейти").setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(which==0){
                    Intent intent = new Intent(SubjectActivity.this,ScheduleActivity.class);
                    startActivity(intent);
                }else if(which ==1 ){
                    Intent intent = new Intent(SubjectActivity.this,CabinetListActivity.class);
                    startActivity(intent);

                }else if(which ==2){
                    Intent intent = new Intent(SubjectActivity.this,MainActivity.class);
                    startActivity(intent);
                }



            }
        });


        menuDialog = builder.create();
        menuDialog.show();

    }


    //Test


    private void SetContent(){
        Intent intent = getIntent();

        if(intent != null){
            Subject = intent.getStringExtra("Subject");
        }

        switch(Subject){
            case "sPhil":
                subjectImage.setImageResource(R.drawable.philosophyimg);
                subjectDescription.setText(R.string.PhilosophyInfo);
                break;
            case "s0103":
                subjectImage.setImageResource(R.drawable.s0103);
                subjectDescription.setText(R.string.s0103Info);
                break;
            case "s0402":
                subjectImage.setImageResource(R.drawable.s0402);
                subjectDescription.setText(R.string.S0402Info);
                break;
            case "s1101":
                subjectImage.setImageResource(R.drawable.s1101);
                subjectDescription.setText(R.string.s1101Info);
                break;
            case "sBj":
                subjectImage.setImageResource(R.drawable.sbj);
                subjectDescription.setText(R.string.sbjInfo);
                break;
            case "s0101":
                subjectImage.setImageResource(R.drawable.s0101);
                subjectDescription.setText(R.string.s0101Info);
                break;
            case "default":
                subjectImage.setImageResource(R.drawable.ks54biglogo);
                subjectDescription.setText("Описание предмета");

        }


    }

}
