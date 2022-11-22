package com.example.trippersapp.DetailPage;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.trippersapp.Adapters.DetailPageAdapter;
import com.example.trippersapp.R;
import com.example.trippersapp.databinding.ActivityDestinationDetailBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

public class DestinationDetailAct extends FragmentActivity {

    ActivityDestinationDetailBinding binding;

    TextView destinationName, destinationRegion, destinationCountry, desattractions, desAbout;
    FloatingActionButton destinationSave, destinationUNSave;
    VideoView destinationVideo;
    String desName, desRegion, desCountry, desVideo, attractions, about;
    TabLayout destinationTabLayout;
    ViewPager2 destinationViewPager;
    DetailPageAdapter detailPageAdapter;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination_detail);

        destinationTabLayout = findViewById(R.id.DestinationTabLayout);
        destinationViewPager = findViewById(R.id.DestinationViewPager);
        detailPageAdapter = new DetailPageAdapter(this);
        destinationViewPager.setAdapter(detailPageAdapter);
        destinationTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                destinationViewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        destinationViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                destinationTabLayout.getTabAt(position).select();
            }
        });

        Intent intent = getIntent();
        desName = intent.getStringExtra("name");
        desRegion = intent.getStringExtra("region");
        desCountry = intent.getStringExtra("country");
        desVideo = intent.getStringExtra("videourl");
        destinationName = findViewById(R.id.DestinationName);
        destinationRegion = findViewById(R.id.DestinationRegion);
        destinationCountry = findViewById(R.id.DestinationCountry);
        destinationVideo = findViewById(R.id.DestinationVideo);

       destinationUNSave = findViewById(R.id.DestinationUNSave);
        destinationUNSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                destinationUNSave.setVisibility(View.GONE);
                destinationSave.setVisibility(View.VISIBLE);
            }
        });

        destinationSave = findViewById(R.id.DestinationSave);
        destinationSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                destinationSave.setVisibility(View.GONE);
                destinationUNSave.setVisibility(View.VISIBLE);
            }
        });
        destinationName.setText(desName);
        destinationRegion.setText(desRegion);
        destinationCountry.setText(desCountry);
        destinationVideo.setVideoURI(Uri.parse(String.valueOf(desVideo)));
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(destinationVideo);
        mediaController.setMediaPlayer(destinationVideo);
        destinationVideo.setMediaController(mediaController);
        destinationVideo.start();
    }
}