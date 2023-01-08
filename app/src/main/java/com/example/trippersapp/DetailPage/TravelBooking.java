package com.example.trippersapp.DetailPage;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.trippersapp.R;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;

public class TravelBooking extends AppCompatActivity {
    String TAG = TravelBooking.class.getSimpleName();


    private Toolbar topBar;
    private AppBarLayout appBarLayout;
    private EditText customer, phone, email, date, note;
    private TextView total, destination, description;
    private MaterialButton submit;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    private String desID, desAttractions, desAvailability, desCountry, desAbout, desName, desPrice, desRating, desRegion, desReviewer, desVideo;
    private DatePickerDialog datePickerDialog;
    private ImageView add, minus;
    private NumberPicker pax;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_booking);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        init();




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
        description = findViewById(R.id.pricedesc);

        pax.setMaxValue(99);
        pax.setMinValue(1);


        Intent intent = getIntent();
        desPrice = intent.getStringExtra("price");
        desName = intent.getStringExtra("name");
        desRegion = intent.getStringExtra("region");
        desCountry = intent.getStringExtra("country");
        destination.setText(desName);
        total.setText(desPrice);

        description.setText("Price per pax: \nPHP " + desPrice);
        pax.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                int num = pax.getValue();
                int prices = Integer.parseInt(desPrice);
                int totalprice = Integer.valueOf((num * prices));
                total.setText(Integer.toString(totalprice));
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitData();
            }
        });







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
        String customertxt = customer.getText().toString().trim();
        String phonetxt = phone.getText().toString().trim();
        String emailtxt = email.getText().toString().trim();
        String datetxt = date.getText().toString().trim();
        String paxtxt = String.valueOf(pax.getValue());
        String notetxt = note.getText().toString().trim();
        String totalpayment = total.getText().toString().trim();



        if (customertxt.isEmpty()) {
            customer.setError("Must fill name");
            customer.requestFocus();
            return;
        }
        if (phonetxt.isEmpty()) {
            phone.setError("Must fill contact number");
            phone.requestFocus();
            return;
        }

        if (emailtxt.isEmpty()) {
            email.setError("Must fill email");
            email.requestFocus();
            return;
        }

        if (datetxt.isEmpty()) {
            date.setError("Must fill date");
            date.requestFocus();
            return;
        }

        else{
            Intent i = new Intent(TravelBooking.this, BookingConfirmation.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.putExtra("name", customertxt);
            i.putExtra("phone", phonetxt);
            i.putExtra("email", emailtxt);
            i.putExtra("date", datetxt);
            i.putExtra("pax", paxtxt);
            i.putExtra("note", notetxt);
            i.putExtra("total", totalpayment);
            i.putExtra("destination", desName);
            i.putExtra("region", desRegion);
            i.putExtra("country", desCountry);
            startActivity(i);
            /*reviewRef.setValue(bookings).addOnSuccessListener(new OnSuccessListener<Void>() {
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
            });*/
        }


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}