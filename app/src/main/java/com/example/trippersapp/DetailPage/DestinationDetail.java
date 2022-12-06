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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DestinationDetail extends AppCompatActivity {

    ActivityDestinationDetailBinding binding;

    private TextView destinationName, destinationRegion, destinationCountry, destinationAttractions, destinationPrice;
    private TextViewEx destinationDescription;
    private FloatingActionButton destinationSave, destinationUNSave;
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
        destinationAttractions.setText(desAttractions.replace("</br>",System.lineSeparator()));

        /**
         * -----VIDEO PLAYER -----
         * */
       /* destinationVideo.setVideoURI(Uri.parse(String.valueOf(desVideo)));
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(destinationVideo);
        mediaController.setMediaPlayer(destinationVideo);
        destinationVideo.setMediaController(mediaController);
        destinationVideo.start();*/


      /*  destinationUNSave = findViewById(R.id.DestinationUNSave);
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
        });*/
        detailsLayout = findViewById(R.id.detailslayout);
        attractionsLayout = findViewById(R.id.attractionslayout);
        mapLayout = findViewById(R.id.maplayout);
        reviewsLayout = findViewById(R.id.reviewslayout);

        detailsBtn = findViewById(R.id.detailsbtn);
        attractionsBtn = findViewById(R.id.attractionsbtn);
        mapBtn = findViewById(R.id.mapbtn);
        reviewsBtn = findViewById(R.id.reviewsbtn);


        detailsBtn.setBackgroundColor(getResources().getColor(R.color.accent));
        detailsBtn.setTextColor(getResources().getColor(R.color.white));

        detailsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                detailsLayout.setVisibility(View.VISIBLE);
                attractionsLayout.setVisibility(View.INVISIBLE);
                mapLayout.setVisibility(View.INVISIBLE);
                reviewsLayout.setVisibility(View.INVISIBLE);

                detailsBtn.setBackgroundColor(getResources().getColor(R.color.accent));
                detailsBtn.setTextColor(getResources().getColor(R.color.white));

                attractionsBtn.setBackgroundColor(getResources().getColor(R.color.transparent));
                attractionsBtn.setTextColor(getResources().getColor(R.color.textcolor));

                mapBtn.setBackgroundColor(getResources().getColor(R.color.transparent));
                mapBtn.setTextColor(getResources().getColor(R.color.textcolor));

                reviewsBtn.setBackgroundColor(getResources().getColor(R.color.transparent));
                reviewsBtn.setTextColor(getResources().getColor(R.color.textcolor));

            }
        });

        attractionsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attractionsLayout.setVisibility(View.VISIBLE);
                detailsLayout.setVisibility(View.INVISIBLE);
                mapLayout.setVisibility(View.INVISIBLE);
                reviewsLayout.setVisibility(View.INVISIBLE);

                attractionsBtn.setBackgroundColor(getResources().getColor(R.color.accent));
                attractionsBtn.setTextColor(getResources().getColor(R.color.white));

                detailsBtn.setBackgroundColor(getResources().getColor(R.color.transparent));
                detailsBtn.setTextColor(getResources().getColor(R.color.textcolor));

                mapBtn.setBackgroundColor(getResources().getColor(R.color.transparent));
                mapBtn.setTextColor(getResources().getColor(R.color.textcolor));

                reviewsBtn.setBackgroundColor(getResources().getColor(R.color.transparent));
                reviewsBtn.setTextColor(getResources().getColor(R.color.textcolor));

            }
        });

        mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mapLayout.setVisibility(View.VISIBLE);
                detailsLayout.setVisibility(View.INVISIBLE);
                attractionsLayout.setVisibility(View.INVISIBLE);
                reviewsLayout.setVisibility(View.INVISIBLE);

                mapBtn.setBackgroundColor(getResources().getColor(R.color.accent));
                mapBtn.setTextColor(getResources().getColor(R.color.white));

                attractionsBtn.setBackgroundColor(getResources().getColor(R.color.transparent));
                attractionsBtn.setTextColor(getResources().getColor(R.color.textcolor));

                detailsBtn.setBackgroundColor(getResources().getColor(R.color.transparent));
                detailsBtn.setTextColor(getResources().getColor(R.color.textcolor));

                reviewsBtn.setBackgroundColor(getResources().getColor(R.color.transparent));
                reviewsBtn.setTextColor(getResources().getColor(R.color.textcolor));

            }
        });

        reviewsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reviewsLayout.setVisibility(View.VISIBLE);
                detailsLayout.setVisibility(View.INVISIBLE);
                attractionsLayout.setVisibility(View.INVISIBLE);
                mapLayout.setVisibility(View.INVISIBLE);

                reviewsBtn.setBackgroundColor(getResources().getColor(R.color.accent));
                reviewsBtn.setTextColor(getResources().getColor(R.color.white));

                attractionsBtn.setBackgroundColor(getResources().getColor(R.color.transparent));
                attractionsBtn.setTextColor(getResources().getColor(R.color.textcolor));

                detailsBtn.setBackgroundColor(getResources().getColor(R.color.transparent));
                detailsBtn.setTextColor(getResources().getColor(R.color.textcolor));

                mapBtn.setBackgroundColor(getResources().getColor(R.color.transparent));
                mapBtn.setTextColor(getResources().getColor(R.color.textcolor));
            }
        });

    }//END OF ONCREATE
}