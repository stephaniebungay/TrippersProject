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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TravelBooking extends AppCompatActivity {
    String TAG = TravelBooking.class.getSimpleName();
    private Toolbar topBar;
    private AppBarLayout appBarLayout;
    private EditText customer, phone, email, date, note, endDate;
    private TextView total, destination, description;
    private MaterialButton submit;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    private String desID, desAttractions, desAvailability, desCountry, desAbout, desName, desRating, desRegion, desReviewer, desVideo;
    private Integer desPrice, desDays, desNights;
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
        endDate = findViewById(R.id.bookingdateend);
        pax = findViewById(R.id.bookingpax);
        note = findViewById(R.id.bookingnote);
        total = findViewById(R.id.bookingtotal);
        submit = findViewById(R.id.bookingbtn);
        destination = findViewById(R.id.bookdestination);
        description = findViewById(R.id.pricedesc);

        pax.setMaxValue(99);
        pax.setMinValue(1);


        Intent intent = getIntent();
        desDays = Math.toIntExact(intent.getIntExtra("days", 0));
        desNights = Math.toIntExact(intent.getIntExtra("nights", 99));
        desPrice = Math.toIntExact(intent.getIntExtra("price", 99));
        desName = intent.getStringExtra("name");
        desRegion = intent.getStringExtra("region");
        desCountry = intent.getStringExtra("country");
        desID = intent.getStringExtra("id");
        destination.setText(desName);
        total.setText(String.valueOf(desPrice));

        description.setText("Price per pax: \nPHP " + desPrice);
        pax.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                int num = pax.getValue();
                int prices = desPrice;
                int totalprice = num * prices;
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
                endDate.setText("");
                Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR );
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DATE);
                String format = "dd/MM/yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(format);
                sdf = new SimpleDateFormat("dd/MM/yyyy");

                    datePickerDialog = new DatePickerDialog(TravelBooking.this,
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {
                                    datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
                                    String dateStart = String.valueOf(dayOfMonth) + "/"+ String.valueOf(monthOfYear+1) + "/" + String.valueOf(year);
                                    String date1 = dateStart;
                                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                                    datePickerDialog.getDatePicker().setMaxDate(c.getTimeInMillis());

                                    Calendar calendar = Calendar.getInstance();

                                    try{
                                        calendar.setTime(sdf.parse(date1));
                                    }catch (ParseException e){
                                        e.printStackTrace();
                                    }
                                    calendar.add(c.DAY_OF_MONTH, desDays);
                                    String date2 = sdf.format(calendar.getTime());
                                    date.setText(date1);
                                    endDate.setText(date2);

                                }
                            }, year, month, day);
                    datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
                    datePickerDialog.show();
                }
            });
                }


    private void submitData() {
        String customertxt = customer.getText().toString().trim();
        String phonetxt = phone.getText().toString().trim();
        String emailtxt = email.getText().toString().trim();
        String datetxt = date.getText().toString().trim();
        String endtxt = endDate.getText().toString().trim();
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
            i.putExtra("enddate", endtxt);
            i.putExtra("pax", paxtxt);
            i.putExtra("note", notetxt);
            i.putExtra("total", totalpayment);
            i.putExtra("destination", desName);
            i.putExtra("region", desRegion);
            i.putExtra("country", desCountry);
            i.putExtra("id",desID);
            startActivity(i);

        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}