package com.example.trippersapp;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.trippersapp.Adapters.DetailPageAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DestinationDetail#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DestinationDetail extends Fragment {


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
    DetailPageAdapter detailPageAdapter;

    public DestinationDetail(){

    }

    public DestinationDetail(String package_attractions, String package_availability, String package_country, String package_description, String package_name, HashMap<String, Object> package_photos, String package_poster, String package_price, Double package_rating, String package_region, String package_video) {
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


    // TODO: Rename and change types and number of parameters


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_example, container, false);
        VideoView sampleVideo = view.findViewById(R.id.sampleVideo);
        TextView sampleName = view.findViewById(R.id.sampleName);
        TextView sampleRegion = view.findViewById(R.id.sampleRegion );
        TextView sampleCountry = view.findViewById(R.id.sampleCountry);
        FloatingActionButton save = view.findViewById(R.id.sampleSave);
        FloatingActionButton unsave = view.findViewById(R.id.sampleUnSave);

        ViewPager2 sampleViewPager = view.findViewById(R.id.sampleViewPager);
        TabLayout sampleTabLayout = view.findViewById(R.id.sampleTabLayout);
        detailPageAdapter = new DetailPageAdapter(getActivity());
        sampleViewPager.setAdapter(detailPageAdapter);
        sampleTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                sampleViewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        sampleViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                sampleTabLayout.getTabAt(position).select();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save.setVisibility(View.GONE);
                unsave.setVisibility(View.VISIBLE);
            }
        });
        unsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unsave.setVisibility(View.GONE);
                save.setVisibility(View.VISIBLE);
            }
        });
        sampleVideo.setVideoURI(Uri.parse(String.valueOf(package_video)));

        MediaController mediaController = new MediaController(getContext());
        mediaController.setAnchorView(sampleVideo);
        mediaController.setMediaPlayer(sampleVideo);
        sampleVideo.setMediaController(mediaController);

        sampleName.setText(package_name);
        sampleRegion.setText(package_region);
        sampleCountry.setText(package_country);

        return view;
    }
    public void onBackPressed()
    {
        AppCompatActivity activity=(AppCompatActivity)getContext();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.framemain,new Fragment()).addToBackStack(null).commit();

    }
}