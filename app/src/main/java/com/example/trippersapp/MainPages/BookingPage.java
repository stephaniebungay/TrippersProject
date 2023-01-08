package com.example.trippersapp.MainPages;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trippersapp.Adapters.BookedAdapter;
import com.example.trippersapp.Models.Bookings;
import com.example.trippersapp.R;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class BookingPage extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    Button bottun;
    RelativeLayout relativemoto;
    private Toolbar topBar;
    private AppBarLayout appBarLayout;

    RecyclerView recyclerView;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseFirestore firebaseFirestore;

    BookedAdapter adapter;
    ArrayList<Bookings> bookingList;
    LinearLayout bg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_page);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        bg = findViewById(R.id.nobg);

        recyclerView = findViewById(R.id.bookingrv);
        firebaseFirestore = firebaseFirestore.getInstance();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        bookingList = new ArrayList<>();
        adapter = new BookedAdapter(this, bookingList);
        recyclerView.setAdapter(adapter);

        addData();



        bottomNavigationView = findViewById(R.id.bottomnav);
        bottomNavigationView.setSelectedItemId(R.id.booking);

        /** APPBAR */
        topBar = findViewById(R.id.bookingsAppBar);
        appBarLayout = findViewById(R.id.bookingsAppBarLayout);
        setSupportActionBar(topBar);
        getSupportActionBar().setTitle("My Upcoming Travels!");
        getSupportActionBar().setElevation(3f);
        getSupportActionBar().setIcon(R.drawable.plane);

        /** END OF APPBAR */




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



                    case R.id.user:
                        startActivity(new Intent(getApplicationContext(), UserPage.class));
                        overridePendingTransition(0,0);
                        return true;

                }
                return false;
            }
        });

    }

    private void addData(){
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        String uid = currentUser.getUid();


        DatabaseReference reference = firebaseDatabase.getReference("Bookings").child(uid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bookingList = new ArrayList<>();
                for (DataSnapshot snap : snapshot.getChildren()) {
                    Bookings bookings = snap.getValue(Bookings.class);
                    bookingList.add(bookings);

                }
                adapter = new BookedAdapter(getApplicationContext(), bookingList);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                if(adapter.getItemCount()==0){
                    Toast.makeText(BookingPage.this, "HENLO", Toast.LENGTH_SHORT).show();
                    bg.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.INVISIBLE);}
                else{
                    recyclerView.setVisibility(View.VISIBLE);
                    bg.setVisibility(View.INVISIBLE);

                }

               /* adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                    @Override
                    public void onChanged() {
                        super.onChanged();
                        if(adapter.getItemCount()==0){
                            Toast.makeText(BookingPage.this, "HENLO", Toast.LENGTH_SHORT).show();
                            bg.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.INVISIBLE);
                        }
                    }
                });*/

                LinearLayoutManager lin = new LinearLayoutManager(getApplicationContext());
                lin.setStackFromEnd(true);
                lin.setReverseLayout(true);
                recyclerView.setLayoutManager(lin);
                recyclerView.setHasFixedSize(true);

                firebaseDatabase = FirebaseDatabase.getInstance();
                databaseReference = firebaseDatabase.getReference("Reviews");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


}