package com.example.trippersapp.DetailPage;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.MediaController;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trippersapp.Adapters.PanoramaAdapter;
import com.example.trippersapp.Adapters.ReviewsAdapter;
import com.example.trippersapp.Extra.TextViewEx;
import com.example.trippersapp.Models.Panorama;
import com.example.trippersapp.Models.Reviews;
import com.example.trippersapp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DestinationDetail extends AppCompatActivity implements View.OnClickListener {

    String TAG = DestinationDetail.class.getSimpleName();
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseFirestore firebaseFirestore;
    ReviewsAdapter reviewsAdapter;
    PanoramaAdapter panoramaAdapter;
    ArrayList<Reviews> reviewsList;
    ArrayList<Panorama> panoramaList;
    private TextView destinationName, destinationRegion, destinationCountry, destinationPrice, destinationAttractions, days, nights;
    private TextViewEx destinationDescription;
    private VideoView destinationVideo;
    private String desID, desAttractions, desAvailability, desCountry, desAbout, desName, desRating, desRegion, desReviewer, desVideo;
    private Integer desPrice, desDays, desNights;
    private List<String> desImages;
    private Double deslatitude, deslongitude;
    private MaterialButton detailsBtn, attractionsBtn, mapBtn, reviewsBtn, addrevBtn, revSubmit, revCancel, bookBtn;
    private ScrollView detailsLayout, attractionsLayout, addreviewsLayout;
    private FrameLayout mapLayout;
    private ConstraintLayout reviewsLayout;
    private RatingBar revRating;
    private EditText revComment;
    private RecyclerView rvReviews;
    private RecyclerView albumRV;
    private Toolbar topBar;
    private AppBarLayout appBarLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination_detail);

        init();
        activeTab(detailsBtn);
        userReviews();


        bookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent o = new Intent(DestinationDetail.this, TravelBooking.class);
                o.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                o.putExtra("days", desDays);
                o.putExtra("nights", desNights);
                o.putExtra("price", desPrice);
                o.putExtra("id", desID);
                o.putExtra("attractions", desAttractions);
                o.putExtra("availability", desAvailability);
                o.putExtra("country", desCountry);
                o.putExtra("description", desAbout);
                o.putExtra("latitude", deslatitude);
                o.putExtra("longitude", deslongitude);
                o.putExtra("name", desName);
                //photos
                o.putExtra("price", desPrice);
                o.putExtra("rating", desRating);
                o.putExtra("region", desRegion);
                o.putExtra("reviewer", desReviewer);
                o.putExtra("videourl", desVideo);

                startActivity(o);

            }
        });


    }//END OF ONCREATE



    private void init(){
        topBar = findViewById(R.id.topAppBarr);
        appBarLayout = findViewById(R.id.appBarLayoutt);
        setSupportActionBar(topBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        bookBtn = findViewById(R.id.booknowbtn);

        Intent intent = getIntent();
        desDays = Math.toIntExact(intent.getIntExtra("days", 99));
        desNights = Math.toIntExact(intent.getIntExtra("nights", 99));
        desID = intent.getStringExtra("id");
        desAttractions = intent.getStringExtra("attractions");
        desAvailability = intent.getStringExtra("availability");
        desCountry = intent.getStringExtra("country");
        desAbout = intent.getStringExtra("description");
        deslatitude = intent.getDoubleExtra("latitude", 0);
        deslongitude = intent.getDoubleExtra("longitude", 0);
        desName = intent.getStringExtra("name");
        desPrice = Math.toIntExact(intent.getIntExtra("price", 99));
        desRating = intent.getStringExtra("rating");
        desRegion = intent.getStringExtra("region");
        desReviewer = intent.getStringExtra("reviewer");
        desVideo = intent.getStringExtra("videourl");
        desImages = Collections.singletonList(intent.getStringExtra("images"));


        destinationName = findViewById(R.id.DestinationName);
        destinationRegion = findViewById(R.id.DestinationRegion);
        destinationCountry = findViewById(R.id.DestinationCountry);
        destinationVideo = findViewById(R.id.DestinationVideo);
        destinationPrice = findViewById(R.id.DestinationPrice);
        destinationDescription = findViewById(R.id.description);
        destinationAttractions = findViewById(R.id.description2);
        days = findViewById(R.id.days);
        nights = findViewById(R.id.nights);
        TextView daytxt = findViewById(R.id.daytxt);
        TextView nighttxt = findViewById(R.id.nightxt);

        days.setText(String.valueOf(desDays));
        if(desDays == 1){
            nights.setVisibility(View.GONE);
            nighttxt.setVisibility(View.GONE);
            daytxt.setText("Day");
        }else {
            nights.setText(String.valueOf(desNights));
        }

        destinationName.setText(desName);
        destinationRegion.setText(desRegion);
        destinationCountry.setText(desCountry);
        destinationPrice.setText(String.valueOf(desPrice));
        destinationDescription.setText(desAbout, true);
        destinationAttractions.setText(desAttractions.replace("</br>",System.lineSeparator()));

        /** * -----VIDEO PLAYER ----- * */
        destinationVideo.setVideoURI(Uri.parse(String.valueOf(desVideo)));
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(destinationVideo);
        mediaController.setMediaPlayer(destinationVideo);
        destinationVideo.setMediaController(mediaController);
        destinationVideo.start();

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

        rvReviews = findViewById(R.id.rvreviews);
        rvReviews.setHasFixedSize(true);
        rvReviews.setLayoutManager(new LinearLayoutManager(this));

        revRating = findViewById(R.id.revrating);
        revComment = findViewById(R.id.revcomment);
        revSubmit = findViewById(R.id.revsubmit);
        revCancel = findViewById(R.id.revcancel);

        albumRV = findViewById(R.id.albumrv);
        albumRV.setHasFixedSize(true);
        albumRV.setLayoutManager(new LinearLayoutManager(this));

        detailsBtn.setOnClickListener(this);
        attractionsBtn.setOnClickListener(this);
        mapBtn.setOnClickListener(this);
        reviewsBtn.setOnClickListener(this);
        addrevBtn.setOnClickListener(this);
        revSubmit.setOnClickListener(this);
        revCancel.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.detailsbtn:
                showDetails();
                break;

            case R.id.attractionsbtn:
                showAttractions();
                panoramaPics();
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

    private void panoramaPics(){

        DatabaseReference panoRef = firebaseDatabase.getReference("VirtualTour").child("ListOfPanorama");
        panoRef.orderByChild("destination").equalTo(desName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                panoramaList = new ArrayList<>();
                for (DataSnapshot snap : snapshot.getChildren()) {
                    Panorama panorama = snap.getValue(Panorama.class);
                    panoramaList.add(panorama);

                }
                panoramaAdapter = new PanoramaAdapter(getApplicationContext(), panoramaList);
                albumRV.setAdapter(panoramaAdapter);
                panoramaAdapter.notifyDataSetChanged();
                if(panoramaAdapter.getItemCount()==0){
                    albumRV.setVisibility(View.GONE);
                }else{
                    albumRV.setVisibility(View.VISIBLE);

                }

                LinearLayoutManager lin = new LinearLayoutManager(getApplicationContext());
                lin.setReverseLayout(true);
                lin.setStackFromEnd(true);
                albumRV.setHasFixedSize(true);
                albumRV.setLayoutManager(lin);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    private void submitReview() {
        DatabaseReference reviewRef = firebaseDatabase.getReference("Reviews").child("ListOfReviews").push();
        String review_comment = revComment.getText().toString();
        String uid = firebaseUser.getEmail();
        String uimg = firebaseUser.getPhotoUrl().toString();
        String uname = firebaseUser.getDisplayName();
        float rating = revRating.getRating();
        Reviews reviews = new Reviews(review_comment,desID, rating, uid, uimg, uname);

        if (rating == 0) {
            Toast.makeText(this, "Please add rating!", Toast.LENGTH_SHORT).show();
            return;
        } else {
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
        Log.d(TAG, "Henlo" + desImages);

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
       SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.gmaps);
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                LatLng latLng = new LatLng(deslatitude, deslongitude);
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.title(latLng.latitude+" KG " + latLng.longitude);
                googleMap.clear();
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                googleMap.addMarker(markerOptions);
            }
        });
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

    private void userReviews() {
        rvReviews.setLayoutManager(new LinearLayoutManager(this));

        DatabaseReference reference = firebaseDatabase.getReference("Reviews").child("ListOfReviews");
        reference.orderByChild("id").equalTo(desID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                reviewsList = new ArrayList<>();
                for (DataSnapshot snap : snapshot.getChildren()) {
                    Reviews reviews = snap.getValue(Reviews.class);
                    reviewsList.add(reviews);

                }
                reviewsAdapter = new ReviewsAdapter(getApplicationContext(), reviewsList);
                rvReviews.setAdapter(reviewsAdapter);
                reviewsAdapter.notifyDataSetChanged();

                LinearLayoutManager lin = new LinearLayoutManager(getApplicationContext());
                lin.setStackFromEnd(true);
                lin.setReverseLayout(true);
                rvReviews.setLayoutManager(lin);
                rvReviews.setHasFixedSize(true);

                firebaseDatabase = FirebaseDatabase.getInstance();
                databaseReference = firebaseDatabase.getReference("Reviews");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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


    private void activeTab(MaterialButton materialButton) {
        materialButton.setBackgroundColor(getResources().getColor(R.color.accent));
        materialButton.setTextColor(getResources().getColor(R.color.white));
    }

    private void inactiveTab(MaterialButton materialButton) {
        materialButton.setBackgroundColor(getResources().getColor(R.color.transparent));
        materialButton.setTextColor(getResources().getColor(R.color.textcolor));

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

}