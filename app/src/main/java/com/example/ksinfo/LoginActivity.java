package com.example.ksinfo;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.ksinfo.Model.AdditionalEducation;
import com.example.ksinfo.Model.Changes;
import com.example.ksinfo.Model.Classrooms;
import com.example.ksinfo.Model.Events;
import com.example.ksinfo.Model.Lesson;
import com.example.ksinfo.Model.Message;
import com.example.ksinfo.Model.News;
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
    public List<List<Lesson>> listScheduleLesson;
    public List<List<Classrooms>> listScheduleClassrooms;
    private static List<Message> listMessage;
    private List<Changes> listChanges;

    private NotificationManager notificationManager;
    private static final int NOTIFY_ID = 1;
    private static String CHANNEL_ID = "CHANNEL_ID";

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
        listScheduleLesson = new ArrayList<>();
        listScheduleClassrooms = new ArrayList<>();
        listMessage = new ArrayList<>();
        listChanges = new ArrayList<>();

        notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn(LoginText.getText().toString(), PasswordText.getText().toString());

            }
        });

        guestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventMet();
                GlobalApplication.listEvents = listEvents;
                NewMet();
                GlobalApplication.listNews = listNews;
                LessonMet();
                ClassroomsMet();



                final Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
                UserStatic.role = 2;

                Runnable r = new Runnable() {
                    @Override
                    public void run(){
                        startActivity(intent);
                    }
                };
                Handler h = new Handler();
                h.postDelayed(r, 4000);
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
                    MessageMet();
                    GlobalApplication.listMes = listMessage;
                    ChangesMet();
                    GlobalApplication.listChanges = listChanges;


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
                UserStatic.groupBD = user.groupBD;
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
                    List<Lesson> listLesson = new ArrayList<>();
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        Lesson lesson = ds.getValue(Lesson.class);
                        listLesson.add(lesson);
                    }
                    listScheduleLesson.add(listLesson);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            };
            commandsRef.addListenerForSingleValueEvent(eventListener);
        }
        GlobalApplication.listScheduleLesson = listScheduleLesson;
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
                    List<Classrooms> listClassrooms = new ArrayList<>();
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        Classrooms classrooms = ds.getValue(Classrooms.class);
                        assert classrooms != null;
                        listClassrooms.add(classrooms);
                    }
                    listScheduleClassrooms.add(listClassrooms);
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            };
            commandsRef.addListenerForSingleValueEvent(eventListener);
        }
        GlobalApplication.listScheduleClassrooms = listScheduleClassrooms;
    }
    //Прогрузка сообщений
    public static void MessageMet(){
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference commandsRef = rootRef.child("Message");
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    Message message = ds.getValue(Message.class);
                    assert message != null;
                    listMessage.add(message);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        commandsRef.addListenerForSingleValueEvent(eventListener);
    }
    //Прогрузка замен
    public void ChangesMet(){
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference commandsRef = rootRef.child("changes");
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    Changes changes = ds.getValue(Changes.class);
                    assert changes != null;
                    listChanges.add(changes);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        commandsRef.addListenerForSingleValueEvent(eventListener);
    }

    public void notifications(){
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivities(getApplicationContext(), 0, new Intent[]{intent}, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                        .setAutoCancel(false)
                        .setSmallIcon(R.drawable.ks54logo2)
                        .setWhen(System.currentTimeMillis())
                        .setContentIntent(pendingIntent)
                        .setContentTitle("Напоминание")
                        .setContentText("Пора покормить кота")
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setAutoCancel(true);
        createChannelIfNeeded(notificationManager);
        notificationManager.notify(NOTIFY_ID, builder.build());

    }

    private static void createChannelIfNeeded(NotificationManager manager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_ID, NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(notificationChannel);
        }
    }


}
