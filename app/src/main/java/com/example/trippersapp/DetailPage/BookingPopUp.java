package com.example.trippersapp.DetailPage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trippersapp.R;

public class BookingPopUp extends AppCompatActivity {
    TextView destination, place, name, email, phone, pax, date, total, process;
    String sdestination, sregion, scountry, sname, semail, sphone, spax, sdate, stotal, sprocess;
    ImageView close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_pop_up);

        Intent i = getIntent();
        sdestination = i.getStringExtra("destination");
        sregion = i.getStringExtra("region");
        scountry = i.getStringExtra("country");
        sname = i.getStringExtra("customer");
        semail = i.getStringExtra("email");
        sphone = i.getStringExtra("phone");
        spax = i.getStringExtra("pax");
        sdate = i.getStringExtra("date");
        stotal = i.getStringExtra("payment");
        sprocess = i.getStringExtra("process");

        destination = findViewById(R.id.popdes);
        place = findViewById(R.id.popregion);
        name = findViewById(R.id.popname);
        email = findViewById(R.id.popemail);
        phone = findViewById(R.id.pophone);
        pax = findViewById(R.id.popax);
        date = findViewById(R.id.popdate);
        total = findViewById(R.id.poptotal);
        process = findViewById(R.id.poprocess);
        close = findViewById(R.id.exitpage);

        destination.setText(sdestination);
        place.setText(sregion + ", " + scountry);
        name.setText(sname);
        email.setText(semail);
        phone.setText(sphone);
        pax.setText("For " + spax+ " People");
        total.setText("PHP " + stotal);
        process.setText(sprocess);
        date.setText("Date of Trip: " + sdate);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}