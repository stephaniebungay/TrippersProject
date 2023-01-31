package com.example.trippersapp.DetailPage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.trippersapp.MainPages.MainActivity;
import com.example.trippersapp.Models.Bookings;
import com.example.trippersapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

public class BookingConfirmation extends AppCompatActivity {

    private FirebaseFirestore firebaseFirestore;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private TextView customer, phone, email, date, pax, note, price;
    private String scustomer, sphone, semail, sdate, spax, snote, sprice, sdestination, sprocess, scountry, sregion, sid, end;
    private MaterialButton submit, cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_confirmation);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        init();
    }

    private void init(){
        customer = findViewById(R.id.mname);
        phone = findViewById(R.id.mphone);
        email = findViewById(R.id.memail);
        date = findViewById(R.id.mdate);
        pax = findViewById(R.id.mpax);
        note = findViewById(R.id.mnote);
        price = findViewById(R.id.pricey);
        submit = findViewById(R.id.confirmbooking);
        cancel = findViewById(R.id.backbooking);

        Intent i = getIntent();
        scustomer = i.getStringExtra("name");
        sphone = i.getStringExtra("phone");
        semail = i.getStringExtra("email");
        sdate = i.getStringExtra("date");
        end = i.getStringExtra("enddate");
        spax = i.getStringExtra("pax");
        snote = i.getStringExtra("note");
        sprice = i.getStringExtra("total");
        sdestination = i.getStringExtra("destination");
        scountry = i.getStringExtra("country");
        sregion = i.getStringExtra("region");
        sid = i.getStringExtra("id");

        customer.setText(scustomer);
        phone.setText("0"+sphone);
        email.setText(semail);
        date.setText(sdate + " to " + end);
        pax.setText(spax);
        note.setText(snote);
        price.setText(sprice);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitData();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });




    }

    private void submitData() {
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        String uid = currentUser.getUid();
        sprocess = "PENDING";
        DatabaseReference reviewRef = firebaseDatabase.getReference("Bookings").child("ListOfBookings").push();
        Bookings bookings = new Bookings(scountry, scustomer, sdate, end, sdestination, semail, sid, snote, spax, sprice, sphone, sprocess, sregion, uid);

        reviewRef.setValue(bookings).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(BookingConfirmation.this, "Booking Added!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(BookingConfirmation.this, MainActivity.class));
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(BookingConfirmation.this, "Failed to add booking!", Toast.LENGTH_SHORT).show();
                return;
            }
        });

    }
}