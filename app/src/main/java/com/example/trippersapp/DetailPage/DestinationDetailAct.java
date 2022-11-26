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

import java.util.HashMap;

public class DestinationDetailAct extends FragmentActivity {

    public String package_attractions;
    public String package_availability;
    public String package_country;
    public String package_description;
    public String package_name;
    public HashMap<String, Object> package_photos;
    public String package_poster;
    public String package_price;
    public Double package_rating;
    public String package_region;
    public String package_video;

    ActivityDestinationDetailBinding binding;

    TextView destinationName, destinationRegion, destinationCountry, desattractions, desAbout;
    FloatingActionButton destinationSave, destinationUNSave;
    VideoView destinationVideo;
    String desName, desRegion, desCountry, desVideo, attractions, about;
    TabLayout destinationTabLayout;
    ViewPager2 destinationViewPager;
    DetailPageAdapter detailPageAdapter;

    public DestinationDetailAct(){

    }

    public DestinationDetailAct(String package_attractions, String package_availability, String package_country, String package_description, String package_name, HashMap<String, Object> package_photos, String package_poster, String package_price, Double package_rating, String package_region, String package_video) {
        this.package_attractions = package_attractions;
        this.package_availability = package_availability;
        this.package_country = package_country;
        this.package_description = package_description;
        this.package_name = package_name;
        this.package_photos = package_photos;
        this.package_poster = package_poster;
        this.package_price = package_price;
        this.package_rating = package_rating;
        this.package_region = package_region;
        this.package_video = package_video;
    }


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