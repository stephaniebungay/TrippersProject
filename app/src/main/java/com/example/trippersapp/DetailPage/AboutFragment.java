package com.example.trippersapp.DetailPage;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.trippersapp.Models.Packages;
import com.example.trippersapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
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
    private ArrayList<Packages> packageList;
    String TAG = AboutFragment.class.getSimpleName();
    private FirebaseFirestore firebaseFirestore;
    private ViewPager2 viewpageAbout;
    private TextView aboutDescription;


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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about, container, false);
        firebaseFirestore = firebaseFirestore.getInstance();



       /* aboutDescription = view.findViewById(R.id.aboutDescription);
       *//* viewpageAbout = view.findViewById(R.id.viewpageabout);

        aboutAdapter = new AboutAdapter(getContext(), packageList);
        viewpageAbout.setAdapter(aboutAdapter);*//*



        aboutDescription.setText(package_description);*/
        aboutDescription = view.findViewById(R.id.aboutDescription);
        Log.d("About","package_description :" + package_description);
        aboutDescription.setText(this.package_description);
        return view;
    }



    public void onStart(){
        super.onStart();

       /* viewpageAbout = view.findViewById(R.id.viewpageabout);

        aboutAdapter = new AboutAdapter(getContext(), packageList);
        viewpageAbout.setAdapter(aboutAdapter);*/


        //fetch of data from firebase
        CollectionReference collectionReference = firebaseFirestore.collection("Packages");
        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d(TAG, document.getId() + " => " + document.getData());
                        Packages packages = new Packages();
                        packages.package_attractions = document.getString("package_attractions").toString();
                        packages.package_availability = document.getString("package_availability").toString();
                        packages.package_country = document.getString("package_country").toString();
                        packages.package_description = document.getString("package_description").toString();
                        packages.package_name = document.getString("package_name").toString();
                        packages.package_poster = document.getString("package_poster").toString();
                        packages.package_price = document.getString("package_price").toString();
                        packages.package_region = document.getString("package_region").toString();
                        packages.package_video = document.getString("package_video").toString();

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

    }

    public void onBackPressed(){
        Fragment fragment = new Fragment();
        AppCompatActivity activity=(AppCompatActivity)getContext();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.frameabout, fragment).addToBackStack(null).commit();
    }
}