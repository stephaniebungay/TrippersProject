package com.example.trippersapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class sampledeets extends AppCompatActivity {

    TextView titletext, titletext2, attracts;
    VideoView video;

    String name, country, videourl, attraction;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        country = intent.getStringExtra("country");
        videourl = intent.getStringExtra("videourl");
        attraction = intent.getStringExtra("attraction");


        titletext = findViewById(R.id.titletext);
        titletext2 = findViewById(R.id.titletext2);
        video = findViewById(R.id.video);
        attracts = findViewById(R.id.hatdog);

        titletext.setText(name);
        titletext2.setText(country);
        attracts.setText(attraction);
        video.setVideoURI(Uri.parse(String.valueOf(videourl)));

        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(video);
        mediaController.setMediaPlayer(video);
        video.setMediaController(mediaController);
        video.start();


    }
}