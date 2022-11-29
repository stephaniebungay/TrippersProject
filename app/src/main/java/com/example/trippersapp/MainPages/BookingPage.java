package com.example.trippersapp.MainPages;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.trippersapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BookingPage extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    Button bottun;
    RelativeLayout relativemoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_page);

        bottomNavigationView = findViewById(R.id.bottomnav);
        bottomNavigationView.setSelectedItemId(R.id.booking);
        bottun = findViewById(R.id.buttonmoto);
        relativemoto = findViewById(R.id.relativemoto);

        bottun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                relativemoto.setVisibility(View.GONE);
            }
        });




        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.booking:
                        return true;

                    case R.id.homepage:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.notification:
                        startActivity(new Intent(getApplicationContext(), NotifPage.class));
                        overridePendingTransition(0,0);
                        return true;


                    case R.id.user:
                        startActivity(new Intent(getApplicationContext(), UserPage.class));
                        overridePendingTransition(0,0);
                        return true;

                }
                return false;
            }
        });

    }
}