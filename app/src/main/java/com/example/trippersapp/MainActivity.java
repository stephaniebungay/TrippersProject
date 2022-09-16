package com.example.trippersapp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telecom.RemoteConference;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.example.trippersapp.databinding.ActivityMainBinding;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private ActivityMainBinding binding;

    private BottomNavigationView bottomNavigationView;
    private Toolbar topBar;
    private AppBarLayout appBarLayout;

    private ActionBar actionBar;

    ViewPager2 recommendViewPager;
    RecommendAdapter adapter;
    DatabaseReference recommendb;
    ArrayList<Recommendation> recommendationList;
    IFirebaseLoadDone iFirebaseLoadDone;

    // private Handler handler = new Handler();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        firebaseAuth = FirebaseAuth.getInstance();
        topBar = findViewById(R.id.topAppBar);
        setSupportActionBar(topBar);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        appBarLayout = findViewById(R.id.appBarLayout);
        appBarLayout.setOutlineProvider(null);


        recommendViewPager = findViewById(R.id.recommendViewPager);
        setupRecommendViewPager();
        recommendb = FirebaseDatabase.getInstance().getReference("Packages");
        recommendationList = new ArrayList<>();
        adapter = new RecommendAdapter(this, recommendationList, recommendViewPager);
        recommendViewPager.setAdapter(adapter);

        recommendb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Recommendation recommendation = dataSnapshot.getValue(Recommendation.class);
                        recommendationList.add(recommendation);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        bottomNavigationView = findViewById(R.id.bottomnav);
        bottomNavigationView.setSelectedItemId(R.id.homepage);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.homepage:
                        return true;


                    case R.id.notification:
                        startActivity(new Intent(getApplicationContext(), NotifPage.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.map:
                        startActivity(new Intent(getApplicationContext(), MapPage.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.booking:
                        startActivity(new Intent(getApplicationContext(), BookingPage.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.user:
                        startActivity(new Intent(getApplicationContext(), UserPage.class));
                        overridePendingTransition(0, 0);
                        return true;

                }
                return false;
            }
        });

        /*AUTOMATIC SCROLLER
        recommendViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                handler.removeCallbacks(sliderRunnable);
                handler.postDelayed(sliderRunnable, 3000);
            }
        });*/

    }//end of oncreate


    private void setupRecommendViewPager() {
        ViewPager2 recommendViewPager = findViewById(R.id.recommendViewPager);
        recommendViewPager.setClipToPadding(false);
        recommendViewPager.setClipChildren(false);
        recommendViewPager.setOffscreenPageLimit(3);
        recommendViewPager.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(10));
        /**compositePageTransformer.addTransformer((page, position) -> {
         float r = 1 - Math.abs(position);
         page.setScaleY(0.85f+ r * 0.15f);
         });*/
        recommendViewPager.setPageTransformer(compositePageTransformer);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.top_menu, menu);
        return true;
    }


    private Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            recommendViewPager.setCurrentItem(recommendViewPager.getCurrentItem() + 1);
        }
    };



   /* @Override
    public void onFirebaseLoadSuccess(ArrayList<Recommendation> recommendationList) {
        adapter = new RecommendAdapter(this, recommendationList);
        recommendViewPager.setAdapter(adapter);
    }

    @Override
    public void onFirebaseLoadFailed(String message) {
        Toast.makeText(this, ""+message, Toast.LENGTH_LONG).show();

    }


/*
    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(sliderRunnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.postDelayed(sliderRunnable, 3000);
    }*/
}