package com.example.trippersapp.DetailPage;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trippersapp.Extra.TextViewEx;
import com.example.trippersapp.R;
import com.example.trippersapp.databinding.ActivityDestinationDetailBinding;
import com.google.android.material.button.MaterialButton;

public class DestinationDetail extends AppCompatActivity implements View.OnClickListener {

    ActivityDestinationDetailBinding binding;

    private TextView destinationName, destinationRegion, destinationCountry, destinationPrice;
    private TextViewEx destinationDescription, destinationAttractions;
    private VideoView destinationVideo;
    private String desName, desRegion, desCountry, desVideo, desAttractions, desAbout, desPrice;
    private MaterialButton detailsBtn, attractionsBtn, mapBtn, reviewsBtn;
    private ScrollView detailsLayout, attractionsLayout, mapLayout, reviewsLayout;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination_detail);

        Intent intent = getIntent();
        desName = intent.getStringExtra("name");
        desRegion = intent.getStringExtra("region");
        desCountry = intent.getStringExtra("country");
        desVideo = intent.getStringExtra("videourl");
        desPrice = intent.getStringExtra("price");
        desAbout = intent.getStringExtra("description");
        desAttractions = intent.getStringExtra("attractions");

        destinationName = findViewById(R.id.DestinationName);
        destinationRegion = findViewById(R.id.DestinationRegion);
        destinationCountry = findViewById(R.id.DestinationCountry);
        destinationVideo = findViewById(R.id.DestinationVideo);
        destinationPrice = findViewById(R.id.DestinationPrice);
        destinationDescription = findViewById(R.id.description);
        destinationAttractions = findViewById(R.id.description2);

        destinationName.setText(desName);
        destinationRegion.setText(desRegion);
        destinationCountry.setText(desCountry);
        destinationPrice.setText(desPrice);
        destinationDescription.setText(desAbout, true);
        destinationAttractions.setText(desAttractions.replace("</br>",System.lineSeparator()), true);

        /**
         * -----VIDEO PLAYER -----
         * */
       /* destinationVideo.setVideoURI(Uri.parse(String.valueOf(desVideo)));
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(destinationVideo);
        mediaController.setMediaPlayer(destinationVideo);
        destinationVideo.setMediaController(mediaController);
        destinationVideo.start();*/

        detailsLayout = findViewById(R.id.detailslayout);
        attractionsLayout = findViewById(R.id.attractionslayout);
        mapLayout = findViewById(R.id.maplayout);
        reviewsLayout = findViewById(R.id.reviewslayout);

        detailsBtn = findViewById(R.id.detailsbtn);
        attractionsBtn = findViewById(R.id.attractionsbtn);
        mapBtn = findViewById(R.id.mapbtn);
        reviewsBtn = findViewById(R.id.reviewsbtn);

        detailsBtn.setOnClickListener(this);
        attractionsBtn.setOnClickListener(this);
        mapBtn.setOnClickListener(this);
        reviewsBtn.setOnClickListener(this);
        activeTab(detailsBtn);



    }//END OF ONCREATE

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.detailsbtn:
                showDetails();
                break;
            case R.id.attractionsbtn:
                showAttractions();
                break;
            case R.id.mapbtn:
                showMap();
                break;
            case R.id.reviewsbtn:
                showReviews();
                break;
        }
    }

    private void showDetails() {
        detailsLayout.setVisibility(View.VISIBLE);
        attractionsLayout.setVisibility(View.INVISIBLE);
        mapLayout.setVisibility(View.INVISIBLE);
        reviewsLayout.setVisibility(View.INVISIBLE);

        activeTab(detailsBtn);
        inactiveTab(reviewsBtn);
        inactiveTab(mapBtn);
        inactiveTab(attractionsBtn);
    }

    private void showAttractions() {
        attractionsLayout.setVisibility(View.VISIBLE);
        detailsLayout.setVisibility(View.INVISIBLE);
        mapLayout.setVisibility(View.INVISIBLE);
        reviewsLayout.setVisibility(View.INVISIBLE);

        activeTab(attractionsBtn);
        inactiveTab(detailsBtn);
        inactiveTab(mapBtn);
        inactiveTab(reviewsBtn);
    }

    private void showMap() {
        mapLayout.setVisibility(View.VISIBLE);
        detailsLayout.setVisibility(View.INVISIBLE);
        attractionsLayout.setVisibility(View.INVISIBLE);
        reviewsLayout.setVisibility(View.INVISIBLE);

        activeTab(mapBtn);
        inactiveTab(detailsBtn);
        inactiveTab(attractionsBtn);
        inactiveTab(reviewsBtn);
    }

    private void showReviews() {
        reviewsLayout.setVisibility(View.VISIBLE);
        detailsLayout.setVisibility(View.INVISIBLE);
        attractionsLayout.setVisibility(View.INVISIBLE);
        mapLayout.setVisibility(View.INVISIBLE);

        activeTab(reviewsBtn);
        inactiveTab(detailsBtn);
        inactiveTab(attractionsBtn);
        inactiveTab(mapBtn);
    }



    private void activeTab (MaterialButton materialButton){
        materialButton.setBackgroundColor(getResources().getColor(R.color.accent));
        materialButton.setTextColor(getResources().getColor(R.color.white));
    }

    private void inactiveTab (MaterialButton materialButton){
        materialButton.setBackgroundColor(getResources().getColor(R.color.transparent));
        materialButton.setTextColor(getResources().getColor(R.color.textcolor));

    }

}