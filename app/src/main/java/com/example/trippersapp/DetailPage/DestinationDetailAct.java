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

import com.example.trippersapp.R;
import com.example.trippersapp.databinding.ActivityDestinationDetailBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DestinationDetailAct extends FragmentActivity {

    ActivityDestinationDetailBinding binding;

    TextView destinationName, destinationRegion, destinationCountry, destinationDescription, destinationAttractions, destinationPrice;
    FloatingActionButton destinationSave, destinationUNSave;
    VideoView destinationVideo;
    String desName, desRegion, desCountry, desVideo, desAttractions, desAbout, desPrice;


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

        //destinationDescription.setText(desAbout);
       // destinationAttractions.setText(desAttractions.replace("\n",System.getProperty("line.separator")));

        destinationVideo.setVideoURI(Uri.parse(String.valueOf(desVideo)));
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(destinationVideo);
        mediaController.setMediaPlayer(destinationVideo);
        destinationVideo.setMediaController(mediaController);
        destinationVideo.start();
        desAttractions.replaceAll( "\n", "\n" );

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

    }
}