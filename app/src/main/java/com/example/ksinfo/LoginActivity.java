package com.example.ksinfo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ksinfo.Model.AdditionalEducation;
import com.example.ksinfo.Model.Classrooms;
import com.example.ksinfo.Model.Events;
import com.example.ksinfo.Model.Lesson;
import com.example.ksinfo.Model.News;
import com.example.ksinfo.Model.Schedule;
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
    private List<News> listNews;
    private List<Lesson> listLesson;
    private List<Classrooms> listClassrooms;
    final Schedule schedule = new Schedule();


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
        listNews = new ArrayList<>();
        listLesson = new ArrayList<>();
        listClassrooms = new ArrayList<>();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn(LoginText.getText().toString(), PasswordText.getText().toString());

            }
        });

        guestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                    GlobalApplication.listAdd = listAdd;
                    EventMet();
                    GlobalApplication.listEvents = listEvents;
                    NewMet();
                    GlobalApplication.listNews = listNews;
                    LessonMet();
                    ClassroomsMet();


                    Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
                    String name = UserStatic.Name;
                    intent.putExtra("name", name);

                    startActivity(intent);
                } else
                    Toast.makeText(LoginActivity.this, "Aвторизация провалена", Toast.LENGTH_SHORT).show();

            }
        });
    }
    //Прогрузка пользователя
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
    //Прогрузка кружков
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
    //Прогрузка мероприятий
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
    //Прогрузка новостей
    public void NewMet(){
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference commandsRef = rootRef.child("News");
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    News news = ds.getValue(News.class);
                    assert news != null;
                    listNews.add(news);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        commandsRef.addListenerForSingleValueEvent(eventListener);
    }
    //Прогрузка уроков
    public void LessonMet(){
        String[] groups = new String[]{"Schedule/1-ISP11-14","Schedule/2-ISP11-11","Schedule/3-ISP9-4","Schedule/4-ISP9-1"};
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        for (int i = 0; i < 4; i++) {
            DatabaseReference commandsRef = rootRef.child(groups[i]);
            ValueEventListener eventListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    listLesson.clear();
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        Lesson lesson = ds.getValue(Lesson.class);
                        assert lesson != null;
                        listLesson.add(lesson);
                    }
                    schedule.listScheduleLesson.add(listLesson);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            };
            commandsRef.addListenerForSingleValueEvent(eventListener);
        }
    }
    //Прогрузка кабинетов
    public void ClassroomsMet(){
        String[] groups = new String[]{"Classrooms/1-ISP11-14","Classrooms/2-ISP11-11","Classrooms/3-ISP9-4","Classrooms/4-ISP9-1"};
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        for (int i = 0; i < 4; i++) {
            DatabaseReference commandsRef = rootRef.child(groups[i]);
            ValueEventListener eventListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    listClassrooms.clear();
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        Classrooms classrooms = ds.getValue(Classrooms.class);
                        assert classrooms != null;
                        listClassrooms.add(classrooms);
                    }
                    schedule.listScheduleClassrooms.add(listClassrooms);
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            };
            commandsRef.addListenerForSingleValueEvent(eventListener);
        }
    }

}
