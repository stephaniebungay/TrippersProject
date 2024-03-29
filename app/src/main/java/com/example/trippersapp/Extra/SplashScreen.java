package com.example.trippersapp.Extra;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trippersapp.LoginRegistration.login;
import com.example.trippersapp.R;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable(){

            @Override
            public void run(){
                startActivity(new Intent(SplashScreen.this, login.class));
            }
        }, 2000);
    }
}