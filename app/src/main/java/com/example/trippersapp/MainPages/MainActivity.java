package com.example.trippersapp.MainPages;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trippersapp.Adapters.AllDesAdapter;
import com.example.trippersapp.Adapters.BudgetAdapter;
import com.example.trippersapp.Adapters.VirtualAdapter;
import com.example.trippersapp.Models.Packages;
import com.example.trippersapp.R;
import com.example.trippersapp.TopDestinations;
import com.example.trippersapp.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    String TAG = MainActivity.class.getSimpleName();
    private Toolbar topBar;
    private AppBarLayout appBarLayout;
    private FirebaseAuth firebaseAuth;
    private ActivityMainBinding binding;
    private BottomNavigationView bottomNavigationView;
    private ActionBar actionBar;
    private TextView seeALL;
    private FirebaseFirestore firebaseFirestore;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private ArrayList<Packages> virtualList, allDesList, budgetList;
    private RecyclerView virtualRv, allRv, budgetRv;
    private VirtualAdapter virtualAdapter;
    private AllDesAdapter allDesAdapter;
    private BudgetAdapter budgetAdapter;
    Packages packages;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseFirestore = firebaseFirestore.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Destinations");

        init();
        dataFetch();
        sortedView();

        bottomNavigationView = findViewById(R.id.bottomnav);
        bottomNavigationView.setSelectedItemId(R.id.homepage);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.homepage:
                        return true;

                    case R.id.booking:
                        startActivity(new Intent(getApplicationContext(), BookingPage.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.user:
                        startActivity(new Intent(getApplicationContext(), UserPage.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });//end of bottom nav
    }//end of oncreate

    private void init(){
        topBar = findViewById(R.id.appBar);
        appBarLayout = findViewById(R.id.appBarLayout);
        setSupportActionBar(topBar);
        getSupportActionBar().setTitle("Trippers: Homepage");

        virtualRv = findViewById(R.id.virtualTourRv);
        allRv = findViewById(R.id.allDesRv);
        budgetRv = findViewById(R.id.budgetRv);

        seeALL = findViewById(R.id.viewAllDes);
        seeALL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, TopDestinations.class));
            }
        });

    }


    private void dataFetch() {
        CollectionReference collectionReference = firebaseFirestore.collection("Destinations");
        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d(TAG, document.getId() + " => " + document.getData());
                        Packages packages = new Packages();
                        packages.days = Integer.valueOf(Math.toIntExact(document.getLong("days")));
                        packages.nights = Integer.valueOf(Math.toIntExact(document.getLong("nights")));
                        packages.package_attraction = document.getString("package_attraction").toString();
                        packages.package_availability = document.getString("package_availability").toString();
                        packages.package_country = document.getString("package_country").toString();
                        packages.package_description = document.getString("package_description").toString();
                        packages.package_id = document.getString("package_id").toString();
//                        packages.package_latitude = Double.parseDouble(document.getDouble("package_latitude").toString());
                        packages.package_longitude = Double.parseDouble(document.getDouble("package_longitude").toString());
                        packages.package_name = document.getString("package_name").toString();
                        packages.package_poster = document.getString("package_poster").toString();
                        packages.package_price = Integer.valueOf(Math.toIntExact(document.getLong("package_price")));
                        packages.package_region = document.getString("package_region").toString();
                        packages.package_video = document.getString("package_video").toString();
                        packages.tourStatus = document.getString("tourStatus").toString();

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

    private void sortedView() {
        virtualList = new ArrayList<>();
        virtualAdapter = new VirtualAdapter(getApplicationContext(), virtualList);
        virtualRv.setAdapter(virtualAdapter);
        virtualRv.setHasFixedSize(true);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        virtualRv.setLayoutManager(layoutManager1);

        firebaseFirestore.collection("Destinations")
                .whereEqualTo("tourStatus", "Yes")
                .whereEqualTo("package_availability","Yes")
                .orderBy("package_name", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        List listofVirtual = value.getDocumentChanges();
                        for (DocumentChange documentChange : value.getDocumentChanges()){
                            if (documentChange.getType() == DocumentChange.Type.ADDED){
                                Log.d(TAG, documentChange.getDocument() + " => " );
                                virtualList.add(documentChange.getDocument().toObject(Packages.class));
                            }
                            virtualAdapter.notifyDataSetChanged();
                        }
                    }
                });


        allDesList = new ArrayList<>();
        allDesAdapter = new AllDesAdapter(getApplicationContext(), allDesList);
        allRv.setAdapter(allDesAdapter);
        allRv.setHasFixedSize(true);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        allRv.setLayoutManager(layoutManager2);

        firebaseFirestore.collection("Destinations")
                .whereEqualTo("package_availability", "Yes")
                .orderBy("package_name", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        List listofAll = value.getDocumentChanges();
                        for (DocumentChange documentChange : value.getDocumentChanges()){
                            if (documentChange.getType() == DocumentChange.Type.ADDED){
                                Log.d(TAG, documentChange.getDocument() + " => " );
                                allDesList.add(documentChange.getDocument().toObject(Packages.class));
                            }
                            allDesAdapter.notifyDataSetChanged();
                        }
                    }
                });

        budgetList = new ArrayList<>();
        budgetAdapter = new BudgetAdapter(getApplicationContext(), budgetList);
        budgetRv.setAdapter(budgetAdapter);
        budgetRv.setHasFixedSize(true);
        LinearLayoutManager layoutManager3 = new LinearLayoutManager(getApplicationContext());
        budgetRv.setLayoutManager(layoutManager3);

        firebaseFirestore.collection("Destinations")
                .whereEqualTo("package_availability", "Yes")
                .orderBy("package_price", Query.Direction.ASCENDING)
                .limit(5)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        List listofBudget = value.getDocumentChanges();
                        for (DocumentChange documentChange : value.getDocumentChanges()){
                            if (documentChange.getType() == DocumentChange.Type.ADDED){
                                Log.d(TAG, documentChange.getDocument() + " => " );
                                budgetList.add(documentChange.getDocument().toObject(Packages.class));
                            }
                            budgetAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }



}