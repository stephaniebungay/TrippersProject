package com.example.trippersapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trippersapp.Adapters.PackageAdapter;
import com.example.trippersapp.Models.Packages;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class TopDestinations extends AppCompatActivity {

    String TAG = TopDestinations.class.getSimpleName();

    RecyclerView recyclerView;
    FirebaseFirestore firebaseFirestore;

    PackageAdapter adapter;
    List<Packages> packageList;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_destinations);

        searchView = findViewById(R.id.searchView);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return false;
            }
        });
        recyclerView = findViewById(R.id.packageView);
        firebaseFirestore = firebaseFirestore.getInstance();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        packageList = new ArrayList<>();
        adapter = new PackageAdapter(this, packageList);
        recyclerView.setAdapter(adapter);


        CollectionReference collectionReference = firebaseFirestore.collection("Destinations");
        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d(TAG, document.getId() + " => " + document.getData());
                        Packages packages = new Packages();
                        packages.package_attractions = document.getString("package_attraction").toString();
                        packages.package_availability = document.getString("package_availability").toString();
                        packages.package_country = document.getString("package_country").toString();
                        packages.package_description = document.getString("package_description").toString();
                        packages.package_id = document.getString("package_id");

                        packages.package_latitude = Double.parseDouble(document.getDouble("package_latitude").toString());
                        packages.package_longitude = Double.parseDouble(document.getDouble("package_longitude").toString());
                        packages.package_name = document.getString("package_name").toString();
//                        packages.package_photos = document.getDocumentReference("package_photos").
                        packages.package_poster = document.getString("package_poster").toString();
                        packages.package_price = document.getString("package_price").toString();
                        packages.package_region = document.getString("package_region").toString();
                        packages.package_video = document.getString("package_video").toString();


                        packageList.add(packages);

                        adapter.notifyDataSetChanged();

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

    private void filterList(String text) {
        List<Packages> filteredList = new ArrayList<>();
        for(Packages packages : packageList){
            if(packages.getPackage_name().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(packages);
            }

        }
        if(filteredList.isEmpty()){
            Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
        }
        else{
            adapter.setFilteredList(filteredList);

        }
    }
}