package com.example.ksinfo;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class ClubDescriptionActivity extends AppCompatActivity {

    //Переменные для правого меню
    AlertDialog menuDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_description);

        Intent intent = getIntent();
        int clubID = intent.getIntExtra("clubID",0);

        TextView placeTextView = findViewById(R.id.ClubDescriptionPlace);
        TextView teacherTextView = findViewById(R.id.ClubDescriptionTeacher);
        TextView conditionTextView = findViewById(R.id.ClubCondition);
        TextView descriptionTextView = findViewById(R.id.ClubDescription);


        placeTextView.setText(GlobalApplication.listAdd.get(clubID).place);
        teacherTextView.setText(GlobalApplication.listAdd.get(clubID).teacher);
        conditionTextView.setText(GlobalApplication.listAdd.get(clubID).status);
        descriptionTextView.setText(GlobalApplication.listAdd.get(clubID).text);

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

    }


    private void rightMenuDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final CharSequence[] options = {"Настройки","Выход из аккаунта"};

        builder.setTitle("Меню").setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                switch (which) {
                    case 0: {
                        Intent intent = new Intent(ClubDescriptionActivity.this, SettingsActivity.class);
                        startActivity(intent);
                    }break;
                    case 1:{
                        Intent intent = new Intent(ClubDescriptionActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }break;
                    default:

                }

            }
        });

        menuDialog = builder.create();
        menuDialog.show();
    }

}
