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

import com.example.ksinfo.Model.Message;

import java.util.List;

public class MessageDescriptionActivity extends AppCompatActivity {

    //Переменные для правого меню
    AlertDialog menuDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_description);

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


        //Заполнение информации
        Intent intent = getIntent();
        int messageNumber = intent.getIntExtra("messageNumber",0);
        List<Message> messageList = GlobalApplication.listMes;

        TextView messageHeader = findViewById(R.id.MessageDescriptionHeader);
        messageHeader.setText(messageList.get(messageNumber).head);

        TextView messageText = findViewById(R.id.MessageDescriptionText);
        messageText.setText(messageList.get(messageNumber).text);


    }

    private void rightMenuDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final CharSequence[] options = {"Настройки","Выход из аккаунта"};

        builder.setTitle("Меню").setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                switch (which) {
                    case 0: {
                        Intent intent = new Intent(MessageDescriptionActivity.this, SettingsActivity.class);
                        startActivity(intent);
                    }break;
                    case 1:{
                        Intent intent = new Intent(MessageDescriptionActivity.this, LoginActivity.class);
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
