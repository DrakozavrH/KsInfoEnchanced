package com.example.ksinfo;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class CabinetListActivity extends AppCompatActivity {

    AlertDialog menuDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cabinet_list);

        ImageButton headerButton = findViewById(R.id.menuHeaderButton);

        headerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlert();
            }
        });

    }

    private void showAlert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final CharSequence[] options = {"Расписание","Информация","Предметы"};

        builder.setTitle("Перейти").setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(which==0){
                    Intent intent = new Intent(CabinetListActivity.this,ScheduleActivity.class);
                    startActivity(intent);
                }else if(which ==1 ){
                    Intent intent = new Intent(CabinetListActivity.this,MainActivity.class);
                    startActivity(intent);

                }else if(which ==2){
                    Intent intent = new Intent(CabinetListActivity.this,SubjectListActivity.class);
                    startActivity(intent);
                }



            }
        });


        menuDialog = builder.create();
        menuDialog.show();

        //Helloooooooooooooooooooooooooooooooooo
        //oooooooooooooooooooooooooooow


    }
}
