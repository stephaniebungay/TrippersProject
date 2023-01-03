package com.example.trippersapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.trippersapp.Models.Bookings;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;

public class TravelBooking extends AppCompatActivity {
    String TAG = TravelBooking.class.getSimpleName();


    private Toolbar topBar;
    private AppBarLayout appBarLayout;
    private EditText customer, phone, email, date, pax, note;
    private TextView total, destination;
    private MaterialButton submit;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    private String desPrice, name;
    private DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_booking);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        init();
        String s = "999";
        /*total.setText(s);*/


        Intent intent = getIntent();
        desPrice = intent.getStringExtra("prices");
        name = intent.getStringExtra("name");
        destination.setText(name);
        Toast.makeText(this, "hello "+name, Toast.LENGTH_SHORT).show();
        Log.d(TAG, "=>" + desPrice);
        total.setText(desPrice);



        pax.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String paxtxt = pax.getText().toString().trim();
                int temp = 999;
                int paxx = Integer.valueOf((pax.getText().toString()));
                int totalprice = Integer.valueOf((temp * paxx));
                if (pax != null && !paxtxt.equals("") && !paxtxt.equals(0)) {
                    total.setText(Integer.toString(totalprice));
                }
                else{
                    total.setText(temp);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String paxtxt = pax.getText().toString().trim();
                int temp = 999;
                int paxx = Integer.valueOf((pax.getText().toString()));
                int totalprice = Integer.valueOf((temp * paxx));
                if (pax != null && !paxtxt.equals("") && !paxtxt.equals(0)) {
                    total.setText(Integer.toString(totalprice));
                }
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitData();
            }
        });






    }


    private void init() {

        /** APPBAR */
        topBar = findViewById(R.id.appBarBack);
        appBarLayout = findViewById(R.id.appBarBackLayout);
        setSupportActionBar(topBar);
        getSupportActionBar().setTitle("Booking");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        /** END OF APPBAR */

        customer = findViewById(R.id.bookingcustomer);
        phone = findViewById(R.id.bookingphone);
        email = findViewById(R.id.bookingemail);
        date = findViewById(R.id.bookingdate);
        pax = findViewById(R.id.bookingpax);
        note = findViewById(R.id.bookingnote);
        total = findViewById(R.id.bookingtotal);
        submit = findViewById(R.id.bookingbtn);
        destination = findViewById(R.id.bookdestination);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR  );
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(TravelBooking.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                date.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);

                            }
                        }, year, month, day);
                datePickerDialog.show();
            }
        });





    }

    private void submitData() {
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        String uid = currentUser.getUid();
        DatabaseReference reviewRef = firebaseDatabase.getReference("Bookings").child(uid).push();

        String destination = "Palawan";
        String customertxt = customer.getText().toString().trim();
        String phonetxt = phone.getText().toString().trim();
        String emailtxt = email.getText().toString().trim();
        String datetxt = date.getText().toString().trim();
        String paxtxt = pax.getText().toString().trim();
        String notetxt = note.getText().toString().trim();
        String totalpayment = total.getText().toString().trim();
        Bookings bookings = new Bookings(customertxt,datetxt,destination, emailtxt,
                notetxt,paxtxt,phonetxt, totalpayment);


        if (customertxt.isEmpty()) {
            Toast.makeText(TravelBooking.this, "Must fill name", Toast.LENGTH_SHORT).show();
            customer.requestFocus();
            return;
        }
        if (phonetxt.isEmpty()) {
            Toast.makeText(TravelBooking.this, "Must fill contact number", Toast.LENGTH_SHORT).show();
            phone.requestFocus();
            return;
        }

        if (emailtxt.isEmpty()) {
            Toast.makeText(TravelBooking.this, "Must fill email ", Toast.LENGTH_SHORT).show();
            email.requestFocus();
            return;
        }

        if (datetxt.isEmpty()) {
            Toast.makeText(TravelBooking.this, "Must fill date ", Toast.LENGTH_SHORT).show();
            date.requestFocus();
            return;
        }
        if (paxtxt.equals("")) {
            Toast.makeText(TravelBooking.this, "Must fill pax(number or person) ", Toast.LENGTH_SHORT).show();
            pax.requestFocus();
            return;
        }
        if (paxtxt.equals("0")) {
            Toast.makeText(TravelBooking.this, "Must fill pax(number or person) ", Toast.LENGTH_SHORT).show();
            pax.requestFocus();
            return;
        }else{
            reviewRef.setValue(bookings).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(TravelBooking.this, "Booking Added!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(TravelBooking.this, "Failed to add booking!", Toast.LENGTH_SHORT).show();
                }
            });
        }


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}