package com.example.trippersapp.MainPages;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.trippersapp.R;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BookingPage extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    Button bottun;
    RelativeLayout relativemoto;
    private Toolbar topBar;
    private AppBarLayout appBarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_page);

        bottomNavigationView = findViewById(R.id.bottomnav);
        bottomNavigationView.setSelectedItemId(R.id.booking);
/*
        bottun = findViewById(R.id.buttonmoto);
*/

        /** APPBAR */
        topBar = findViewById(R.id.bookingsAppBar);
        appBarLayout = findViewById(R.id.bookingsAppBarLayout);
        setSupportActionBar(topBar);
        getSupportActionBar().setTitle("My Upcoming Travels!");
        getSupportActionBar().setElevation(3f);
        getSupportActionBar().setIcon(R.drawable.plane);

        /** END OF APPBAR */

      /*  bottun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BookingPage.this, TravelBooking.class));
*//*
                relativemoto.setVisibility(View.GONE);
*//*
            }
        });
*/



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