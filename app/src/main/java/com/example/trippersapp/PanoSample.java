package com.example.trippersapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.material.appbar.AppBarLayout;

public class PanoSample extends AppCompatActivity {

    String TAG = PanoSample.class.getSimpleName();


    private ImageView img;
    private boolean isSupported = false;
    private float mScaleFactor = 1.0f;
    private String name, poster;
    private Toolbar topBar;
    private AppBarLayout appBarLayout;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pano_sample);

        init();
        Log.d(TAG, "Panorama: " + poster);

    }

    private void init() {
        Intent i = getIntent();
        name = i.getStringExtra("name");
        poster = i.getStringExtra("image");

        topBar = findViewById(R.id.topAppBar2);
        appBarLayout = findViewById(R.id.appBarLayout2);
        setSupportActionBar(topBar);
        getSupportActionBar().setTitle(name);
        getSupportActionBar().setSubtitle("scroll right and left to see full view");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);



        img = findViewById(R.id.imagePano);


        Glide.with(this)
                .asBitmap()
                .load(poster)
                .apply(new RequestOptions()
                        .fitCenter()
                        .format(DecodeFormat.PREFER_ARGB_8888)
                        .override(Target.SIZE_ORIGINAL))
                .into(new SimpleTarget<Bitmap>(100,100) {

                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        img.setImageBitmap(resource);

                    }

                });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

}

