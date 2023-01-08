package com.example.trippersapp.DetailPage;

import android.annotation.SuppressLint;
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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trippersapp.Adapters.ReviewsAdapter;
import com.example.trippersapp.Extra.TextViewEx;
import com.example.trippersapp.Models.Gallery;
import com.example.trippersapp.Models.Reviews;
import com.example.trippersapp.R;
import com.example.trippersapp.databinding.ActivityDestinationDetailBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class DestinationDetail extends AppCompatActivity implements View.OnClickListener {

    static String REVIEWS_KEY = "Reviews";
    String TAG = DestinationDetail.class.getSimpleName();
    ActivityDestinationDetailBinding binding;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseFirestore firebaseFirestore;
    ReviewsAdapter reviewsAdapter;
    ArrayList<Reviews> reviewsList;
    ArrayList<Gallery> galleries;
    private TextView destinationName, destinationRegion, destinationCountry, destinationPrice, destinationAttractions;
    private TextViewEx destinationDescription;
    private VideoView destinationVideo;
    private String desID, desAttractions, desAvailability, desCountry, desAbout, desName, desPrice, desRating, desRegion, desReviewer, desVideo;
    private Double deslatitude, deslongitude;
    private MaterialButton detailsBtn, attractionsBtn, mapBtn, reviewsBtn, addrevBtn, revSubmit, revCancel, bookBtn;
    private ScrollView detailsLayout, attractionsLayout, addreviewsLayout;
    private FrameLayout mapLayout;
    private ConstraintLayout reviewsLayout;
    private RatingBar revRating;
    private EditText revComment;
    private RecyclerView rvReviews;
    DestinationDetail context;
    TravelBooking tb;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination_detail);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        bookBtn = findViewById(R.id.booknowbtn);



        rvReviews = findViewById(R.id.rvreviews);
        rvReviews.setHasFixedSize(true);
        rvReviews.setLayoutManager(new LinearLayoutManager(this));

        Intent intent = getIntent();
        desID = intent.getStringExtra("id");
        desAttractions = intent.getStringExtra("attractions");
        desAvailability = intent.getStringExtra("availability");
        desCountry = intent.getStringExtra("country");
        desAbout = intent.getStringExtra("description");
        deslatitude = intent.getDoubleExtra("latitude", 0);
        deslongitude = intent.getDoubleExtra("longitude", 0);
        desName = intent.getStringExtra("name");
        desPrice = intent.getStringExtra("price");
        desRating = intent.getStringExtra("rating");
        desRegion = intent.getStringExtra("region");
        desReviewer = intent.getStringExtra("reviewer");
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
        destinationAttractions.setText(desAttractions.replace("</br>", System.lineSeparator()));

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
        userReviews();
        addGallery();

        bookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent o = new Intent(DestinationDetail.this, TravelBooking.class);
                o.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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

    private void addGallery() {
        CollectionReference collectionReference = firebaseFirestore.collection("Packages").document(desID).collection("package_latitude");
        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d(TAG, document.getId() + " => " + document.getData());
                        String pic = document.getData().toString();
                    }

                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });



       /* DatabaseReference reference = firebaseDatabase.getReference("Packages").child(desID).child("package_photos");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Gallery gallery = dataSnapshot.getValue(Gallery.class);
                    galleries.add(gallery);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/

    }


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


    private void submitReview() {
        DatabaseReference reviewRef = firebaseDatabase.getReference("Reviews").child(desID).push();
        String review_comment = revComment.getText().toString();
        String uid = firebaseUser.getEmail();
        String uimg = firebaseUser.getPhotoUrl().toString();
        String uname = firebaseUser.getDisplayName();
        float rating = revRating.getRating();
        Reviews reviews = new Reviews(review_comment, rating, uid, uimg, uname);

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
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20));
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

        DatabaseReference reference = firebaseDatabase.getReference(REVIEWS_KEY).child(desID);
        reference.addValueEventListener(new ValueEventListener() {
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

}