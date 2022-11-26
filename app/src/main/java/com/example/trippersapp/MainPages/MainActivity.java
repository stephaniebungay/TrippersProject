package com.example.trippersapp.MainPages;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.example.trippersapp.Adapters.RecommendAdapter;
import com.example.trippersapp.Adapters.TopAttractionAdapter;
import com.example.trippersapp.Adapters.TopDestinationAdapter;
import com.example.trippersapp.Models.Packages;
import com.example.trippersapp.R;
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

public class MainActivity extends AppCompatActivity {





    private FirebaseAuth firebaseAuth;
    private ActivityMainBinding binding;
    private BottomNavigationView bottomNavigationView;
    private Toolbar topBar;
    private AppBarLayout appBarLayout;
    private ActionBar actionBar;

    private DatabaseReference database;
    private ViewPager2 recommendViewPager, topDesViewPager, topAttractionViewPager;
    private ArrayList<Packages> recommendList, topDestinationList, topAttractionList;
    private RecommendAdapter recommendAdapter;
    private TopDestinationAdapter topDestinationAdapter;
    private TopAttractionAdapter topAttractionAdapter;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        topBar = findViewById(R.id.topAppBar);
//        ((AppCompatActivity)getActivity()).setSupportActionBar(topBar);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
        appBarLayout = findViewById(R.id.appBarLayout);
        appBarLayout.setOutlineProvider(null);

        recommendViewPager = findViewById(R.id.recommendViewPager);
        topDesViewPager = findViewById(R.id.topDesViewPager);
        topAttractionViewPager = findViewById(R.id.topAttractionViewPager);

        setupViewPager(recommendViewPager);
        setupViewPager(topDesViewPager);
        setupViewPager(topAttractionViewPager);

        database = FirebaseDatabase.getInstance().getReference("Packages");
        recommendList = new ArrayList<>();
        topDestinationList = new ArrayList<>();
        topAttractionList = new ArrayList<>();

        recommendAdapter = new RecommendAdapter(this, recommendList, recommendViewPager);
        recommendViewPager.setAdapter(recommendAdapter);

        topDestinationAdapter = new TopDestinationAdapter(this, topDestinationList, topDesViewPager);
        topDesViewPager.setAdapter(topDestinationAdapter);

        topAttractionAdapter = new TopAttractionAdapter(this, topAttractionList, topAttractionViewPager);
        topAttractionViewPager.setAdapter(topAttractionAdapter);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Packages packages = dataSnapshot.getValue(Packages.class);
                    recommendList.add(packages);
                    topDestinationList.add(packages);
                    topAttractionList.add(packages);
                }
                recommendAdapter.notifyDataSetChanged();
                topDestinationAdapter.notifyDataSetChanged();
                topAttractionAdapter.notifyDataSetChanged();
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

                    /*case R.id.map:
                        startActivity(new Intent(getApplicationContext(), MapPage.class));
                        overridePendingTransition(0, 0);
                        return true;*/

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
        });//end of bottom nav
    }//end of oncreate

    private void setupViewPager(ViewPager2 viewPager2) {
        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(1));
        viewPager2.setPageTransformer(compositePageTransformer);
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
}