package com.example.ksinfo;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LoadingActivity extends AppCompatActivity {

    private ImageView logoImage;
    private Animation logoAnimation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        logoImage = findViewById(R.id.imageView);
        logoAnimation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.anim);
        logoImage.startAnimation(logoAnimation);

        int colorFrom = Color.RED;
        int colorTo = Color.GREEN;
        int duration = 1000;
        ObjectAnimator.ofObject(R.id.loadingBackground, "backgroundColor", new ArgbEvaluator(), colorFrom, colorTo)
                .setDuration(duration)
                .start();


        AnimateLogo();

    }

    private void AnimateLogo(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(3000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                Intent intent = new Intent(LoadingActivity.this,MainActivity.class);
                startActivity(intent);
            }
        }).start();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }


}
