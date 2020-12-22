package com.example.ksinfo;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ksinfo.Model.AdditionalEducation;
import com.example.ksinfo.Model.Events;
import com.example.ksinfo.Model.User;
import com.example.ksinfo.Model.UserStatic;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {


    TextView LoginText;
    TextView PasswordText;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDataBase;
    private DatabaseReference dataBase;

    private List<AdditionalEducation> listAdd;
    private List<Events> listEvents;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LoginText = findViewById(R.id.LoginText);
        PasswordText = findViewById(R.id.PasswordText);
        Button loginButton = findViewById(R.id.LoginButton);
        Button guestButton = findViewById(R.id.NoRegistrationButton);

        mAuth = FirebaseAuth.getInstance();
        mDataBase = FirebaseDatabase.getInstance();
        dataBase = FirebaseDatabase.getInstance().getReference("additional_education");

        listAdd = new ArrayList<>();
        listEvents = new ArrayList<>();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn(LoginText.getText().toString(), PasswordText.getText().toString());

//                Snackbar.make(activity_login, "Пользователь не найден", Snackbar.LENGTH_LONG).show();

                ((GlobalApplication) getApplication()).setLoginStatus("User");
                //Intent intent = new Intent(LoginActivity.this,ProfileActivity.class);

                String name = LoginText.getText().toString();

                //intent.putExtra("name", name);

                if (LoginText.getText().toString().equals("admin") && PasswordText.getText().toString().equals("admin")) {
                    ((GlobalApplication) getApplication()).setLoginStatus("Admin");
                }

                //startActivity(intent);


            }
        });

        guestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((GlobalApplication) getApplication()).setLoginStatus("Guest");
                Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
    }

    public void signIn(final String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    Toast.makeText(LoginActivity.this, "Aвторизация успешна", Toast.LENGTH_SHORT).show();
                    UserMet();
                    AdditionalEducationMet();
                    GlobalApplication.MasAdd = listAdd;
                    EventMet();
                    GlobalApplication.MasEvents = listEvents;

                    ((GlobalApplication) getApplication()).setLoginStatus("User");
                    Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
                    String name = LoginText.getText().toString();
                    intent.putExtra("name", name);

                    if (LoginText.getText().toString().equals("admin") && PasswordText.getText().toString().equals("admin")) {
                        ((GlobalApplication) getApplication()).setLoginStatus("Admin");
                    }

                    startActivity(intent);
                } else
                    Toast.makeText(LoginActivity.this, "Aвторизация провалена", Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void UserMet() {
        final DatabaseReference UserRef = mDataBase.getReference("Users");
        UserRef.orderByChild("email").equalTo(LoginText.getText().toString()).addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
                User user = snapshot.getValue(User.class);
                UserStatic.email = user.email;
                UserStatic.group = user.group;
                UserStatic.Name = user.Name;
                UserStatic.Patronymic = user.Patronymic;
                UserStatic.role = user.role;
                UserStatic.Surname = user.Surname;

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }

    public void AdditionalEducationMet(){
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference commandsRef = rootRef.child("additional_education");
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    AdditionalEducation additionalEducation = ds.getValue(AdditionalEducation.class);
                    assert additionalEducation != null;
                    listAdd.add(additionalEducation);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        commandsRef.addListenerForSingleValueEvent(eventListener);
    }

    public void EventMet(){
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference commandsRef = rootRef.child("Events");
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    Events events = ds.getValue(Events.class);
                    assert events != null;
                    listEvents.add(events);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        commandsRef.addListenerForSingleValueEvent(eventListener);
    }
}
