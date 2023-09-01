package com.devpk.note_app.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.devpk.note_app.LockScreenActivity;
import com.devpk.note_app.R;
import com.devpk.note_app.SharedPref.Setting;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;
    private Animation animation;
    private TextView logo_text;
    private CardView logo;
    //private SharedPref sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        logo = findViewById(R.id.logo);
        logo_text = findViewById(R.id.logo_text);
        animation = AnimationUtils.loadAnimation(this, R.anim.smalltobig);
        logo.startAnimation(animation);
        logo_text.startAnimation(animation);
        loadSettings();
    }

    private void loadSettings() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (Setting.in_code) {
                    Intent main = new Intent(SplashActivity.this, LockScreenActivity.class);
                    startActivity(main);
                    finish();
                } else {
                    Intent main = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(main);
                    finish();
                }
            }
        }, SPLASH_TIME_OUT);
    }
}