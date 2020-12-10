package com.example.ksinfo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {


    TextView LoginText;
    TextView PasswordText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        LoginText = findViewById(R.id.LoginText);
        PasswordText = findViewById(R.id.PasswordText);

        Button loginButton = findViewById(R.id.LoginButton);
        Button guestButton = findViewById(R.id.NoRegistrationButton);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO логин с данными из бд и проверками

                ((GlobalApplication) getApplication()).setLoginStatus("User");
                Intent intent = new Intent(LoginActivity.this,ProfileActivity.class);

                String name = LoginText.getText().toString();

                intent.putExtra("name", name);

                if(LoginText.getText().toString().equals("admin") && PasswordText.getText().toString().equals("admin")){
                    ((GlobalApplication) getApplication()).setLoginStatus("Admin");
                }


                startActivity(intent);

            }
        });



        guestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((GlobalApplication) getApplication()).setLoginStatus("Guest");
                Intent intent = new Intent(LoginActivity.this,ProfileActivity.class);
                startActivity(intent);
            }
        });




    }

}
