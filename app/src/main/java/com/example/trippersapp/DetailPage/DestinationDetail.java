package com.example.trippersapp.DetailPage;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.trippersapp.Extra.TextViewEx;
import com.example.trippersapp.Models.Reviews;
import com.example.trippersapp.R;
import com.example.trippersapp.databinding.ActivityDestinationDetailBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DestinationDetail extends AppCompatActivity implements View.OnClickListener {

    ActivityDestinationDetailBinding binding;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    private TextView destinationName, destinationRegion, destinationCountry, destinationPrice;
    private TextViewEx destinationDescription, destinationAttractions;
    private VideoView destinationVideo;
    private String desID, desAttractions, desAvailability, desCountry, desAbout, desName, desPrice, desRating, desRegion, desVideo;
    private MaterialButton detailsBtn, attractionsBtn, mapBtn, reviewsBtn, addrevBtn, revSubmit, revCancel;
    private ScrollView detailsLayout, attractionsLayout, mapLayout, addreviewsLayout;
    private ConstraintLayout reviewsLayout;
    private RatingBar revRating;
    private EditText revComment;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination_detail);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();

        Intent intent = getIntent();
        desID = intent.getStringExtra("id");
        desAttractions = intent.getStringExtra("attractions");
        desAvailability = intent.getStringExtra("availability");
        desCountry = intent.getStringExtra("country");
        desAbout = intent.getStringExtra("description");
        desName = intent.getStringExtra("name");
        desPrice = intent.getStringExtra("price");
        desRating = intent.getStringExtra("rating");
        desRegion = intent.getStringExtra("region");
        desVideo = intent.getStringExtra("videourl");

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
        destinationAttractions.setText(desAttractions.replace("</br>", System.lineSeparator()), true);

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
        addreviewsLayout = findViewById(R.id.addreviewslayout);

        detailsBtn = findViewById(R.id.detailsbtn);
        attractionsBtn = findViewById(R.id.attractionsbtn);
        mapBtn = findViewById(R.id.mapbtn);
        reviewsBtn = findViewById(R.id.reviewsbtn);
        addrevBtn = findViewById(R.id.addrevbtn);

        revRating = findViewById(R.id.revrating);
        revComment = findViewById(R.id.revcomment);
        revSubmit = findViewById(R.id.revsubmit);
        revCancel = findViewById(R.id.revcancel);

        detailsBtn.setOnClickListener(this);
        attractionsBtn.setOnClickListener(this);
        mapBtn.setOnClickListener(this);
        reviewsBtn.setOnClickListener(this);
        addrevBtn.setOnClickListener(this);
        revSubmit.setOnClickListener(this);
        revCancel.setOnClickListener(this);
        activeTab(detailsBtn);


    }//END OF ONCREATE

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
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
            case R.id.revcancel:
                showReviews();
                break;

            case R.id.addrevbtn:
                showAddReview();
                break;

            case R.id.revsubmit:
                submitReview();
                break;
        }
    }

    private void showAddReview() {
        addreviewsLayout.setVisibility(View.VISIBLE);
        reviewsLayout.setVisibility(View.INVISIBLE);
        detailsLayout.setVisibility(View.INVISIBLE);
        attractionsLayout.setVisibility(View.INVISIBLE);
        mapLayout.setVisibility(View.INVISIBLE);

        activeTab(reviewsBtn);
        inactiveTab(detailsBtn);
        inactiveTab(attractionsBtn);
        inactiveTab(mapBtn);
    }

    private void submitReview() {
        DatabaseReference reviewRef = firebaseDatabase.getReference("Reviews").child(desID).push();
        String review_comment = revComment.getText().toString();
        String uid = firebaseUser.getEmail();
        String uimg = firebaseUser.getPhotoUrl().toString();
        String uname = firebaseUser.getDisplayName();
        String rating = String.valueOf(revRating.getRating());
        Reviews reviews = new Reviews(review_comment, uid, uimg, uname, rating);

        if (Float.parseFloat(rating)==0) {
            Toast.makeText(this, "Please add rating!", Toast.LENGTH_SHORT).show();
            return;
        }
        else{
            reviewRef.setValue(reviews).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(DestinationDetail.this, "Review Added!", Toast.LENGTH_SHORT).show();
                    revComment.setText("");
                    revRating.setRating(0);
                    showReviews();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(DestinationDetail.this, "Fail to add review: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }

    }

    private void showDetails() {
        detailsLayout.setVisibility(View.VISIBLE);
        attractionsLayout.setVisibility(View.INVISIBLE);
        mapLayout.setVisibility(View.INVISIBLE);
        reviewsLayout.setVisibility(View.INVISIBLE);
        addreviewsLayout.setVisibility(View.INVISIBLE);


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
        addreviewsLayout.setVisibility(View.INVISIBLE);


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
        addreviewsLayout.setVisibility(View.INVISIBLE);


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
        addreviewsLayout.setVisibility(View.INVISIBLE);


        activeTab(reviewsBtn);
        inactiveTab(detailsBtn);
        inactiveTab(attractionsBtn);
        inactiveTab(mapBtn);

        revComment.setText("");
        revRating.setRating(0);
    }


    private void activeTab(MaterialButton materialButton) {
        materialButton.setBackgroundColor(getResources().getColor(R.color.accent));
        materialButton.setTextColor(getResources().getColor(R.color.white));
    }

    private void inactiveTab(MaterialButton materialButton) {
        materialButton.setBackgroundColor(getResources().getColor(R.color.transparent));
        materialButton.setTextColor(getResources().getColor(R.color.textcolor));

    }

}