package com.example.trippersapp.DetailPage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.trippersapp.Models.Packages;
import com.example.trippersapp.R;

import java.util.HashMap;

public class AboutFragment extends Fragment {

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
    private Packages packages;

    public AboutFragment(){

    }

    public AboutFragment(String package_attractions, String package_availability, String package_country, String package_description, String package_name, HashMap<String, Object> package_photos, String package_poster, String package_price, Double package_rating, String package_region, String package_video) {
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about, container, false);

        TextView aboutDescription = view.findViewById(R.id.aboutDesctiption);

        //aboutDescription.setText(packages.getPackage_description());
        return view;
    }

    /*public void onBackPressed(){
        Fragment fragment = new Fragment();
        AppCompatActivity activity=(AppCompatActivity)getContext();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.aboutFrame, fragment).addToBackStack(null).commit();
    }*/
}