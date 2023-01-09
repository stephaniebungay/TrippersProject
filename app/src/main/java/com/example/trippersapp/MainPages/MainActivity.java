package com.example.trippersapp.MainPages;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.example.trippersapp.Adapters.RecommendAdapter;
import com.example.trippersapp.Adapters.SearchAdapter;
import com.example.trippersapp.Adapters.TopAttractionAdapter;
import com.example.trippersapp.Adapters.TopDestinationAdapter;
import com.example.trippersapp.Models.Packages;
import com.example.trippersapp.R;
import com.example.trippersapp.TopDestinations;
import com.example.trippersapp.databinding.ActivityMainBinding;
import com.firebase.ui.database.FirebaseRecyclerOptions;
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


    private Toolbar topBar;
    private AppBarLayout appBarLayout;
    String TAG = MainActivity.class.getSimpleName();
    private FirebaseAuth firebaseAuth;
    private ActivityMainBinding binding;
    private BottomNavigationView bottomNavigationView;
    private ActionBar actionBar;
    private TextView seeALL;

    //private DatabaseReference database;
    private FirebaseFirestore firebaseFirestore;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private ArrayList<Packages> recommendList, topDestinationList, topAttractionList, searchList;
    private RecommendAdapter recommendAdapter;
    private TopDestinationAdapter topDestinationAdapter;
    private TopAttractionAdapter topAttractionAdapter;
    private SearchAdapter searchAdapter;
    private RecyclerView recommendViewPager, topAttractionViewPager, topDesViewPager, results;
    private SearchView SearchBar;
    private FirebaseRecyclerOptions<Packages> options;
    /*private FirebaseRecyclerAdapter<Packages, com.example.trippersapp.Adapters.ViewHolder> firebaseRecyclerAdapter;
    private LinearLayoutManager mLinearLayoutManager;*/

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
        databaseReference = firebaseDatabase.getReference("Packages");


        init();
        /*dataFetch();*/

        /** APPBAR */





        seeALL = findViewById(R.id.recommendSeeAll);

        recommendViewPager = findViewById(R.id.recommendViewPager);
        topDesViewPager = findViewById(R.id.topDesViewPager);
        topAttractionViewPager = findViewById(R.id.topAttractionViewPager);

        recommendViewPager.setHasFixedSize(true);
        topDesViewPager.setHasFixedSize(true);
        topAttractionViewPager.setHasFixedSize(true);

        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recommendViewPager.setLayoutManager(layoutManager1);

        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        topDesViewPager.setLayoutManager(layoutManager2);

        LinearLayoutManager layoutManager3 = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        topAttractionViewPager.setLayoutManager(layoutManager3);


        recommendList = new ArrayList<>();
        topDestinationList = new ArrayList<>();
        topAttractionList = new ArrayList<>();

        recommendAdapter = new RecommendAdapter(this, recommendList);
        recommendViewPager.setAdapter(recommendAdapter);

        /*topDestinationAdapter = new TopDestinationAdapter(this, topDestinationList);
        topDesViewPager.setAdapter(topDestinationAdapter);*/

        topAttractionAdapter = new TopAttractionAdapter(this, topAttractionList);
        topAttractionViewPager.setAdapter(topAttractionAdapter);


        CollectionReference collectionReference = firebaseFirestore.collection("Packages");
        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d(TAG, document.getId() + " => " + document.getData());
                        Packages packages = new Packages();
                        packages.package_id = document.getId().toString();
                        packages.package_attractions = document.getString("package_attractions").toString();
                        packages.package_availability = document.getString("package_availability").toString();
                        packages.package_country = document.getString("package_country").toString();
                        packages.package_description = document.getString("package_description").toString();
                        packages.package_latitude = Double.parseDouble(document.getDouble("package_latitude").toString());
                        packages.package_longitude = Double.parseDouble(document.getDouble("package_longitude").toString());
                        packages.package_name = document.getString("package_name").toString();
//                        packages.package_photos = document.getDocumentReference("package_photos").
                        packages.package_poster = document.getString("package_poster").toString();
                        packages.package_price = document.getString("package_price").toString();
                        packages.package_region = document.getString("package_region").toString();
                        packages.package_video = document.getString("package_video").toString();

                        recommendList.add(packages);
                        /*topDestinationList.add(packages);*/
                        topAttractionList.add(packages);
/*
                        topDesViewPager.setAdapter(topDestinationAdapter);
*/

                        recommendAdapter = new RecommendAdapter(getApplicationContext(), recommendList);
                        recommendViewPager.setAdapter(recommendAdapter);

                        topAttractionAdapter = new TopAttractionAdapter(getApplicationContext(), topAttractionList);
                        topAttractionViewPager.setAdapter(topAttractionAdapter);

                        recommendAdapter.notifyDataSetChanged();
/*
                        topDestinationAdapter.notifyDataSetChanged();
*/
                        topAttractionAdapter.notifyDataSetChanged();
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

        seeALL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, TopDestinations.class));

            }
        });

        search();

    }//end of oncreate

    private void search() {
        topDestinationAdapter = new TopDestinationAdapter(getApplicationContext(), topDestinationList);
        topDesViewPager.setAdapter(topDestinationAdapter);
        firebaseFirestore.collection("Packages")
                .whereEqualTo("package_country", "Philippines")
                .orderBy("package_name", Query.Direction.DESCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        List listofDes = value.getDocumentChanges();
                        if (listofDes.isEmpty()) {
                            Toast.makeText(MainActivity.this, "No recyclerview", Toast.LENGTH_SHORT).show();
                        } else {
                            for (DocumentChange documentChange : value.getDocumentChanges()) {
                                if (documentChange.getType() == DocumentChange.Type.ADDED) {
                                    topDestinationList.add(documentChange.getDocument().toObject(Packages.class));
                                }
                                topDestinationAdapter.notifyDataSetChanged();
                            }
                        }

                    }
                });
    }

    private void init(){
        topBar = findViewById(R.id.appBar);
        appBarLayout = findViewById(R.id.appBarLayout);
        setSupportActionBar(topBar);
        getSupportActionBar().setTitle("Trippers: Homepage");
        /** END OF APPBAR */


    }

   /* private void dataFetch() {
        options = new FirebaseRecyclerOptions.Builder<Packages>().setQuery(databaseReference, Packages.class).build();

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Packages, ViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Packages model) {
                holder.setDetails(getApplicationContext(),
                        model.getPackage_poster(),
                        model.getPackage_name(),
                        model.getPackage_region(),
                        model.getPackage_country(),
                        model.getPackage_price());
            }

            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.destination_landscape_container, parent, false);

                ViewHolder viewHolder = new ViewHolder(itemView);
                viewHolder.setOnClickListener(new ViewHolder.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Toast.makeText(MainActivity.this, "hello", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void ontItemLongClick(View view, int position) {
                        Toast.makeText(MainActivity.this, "helloooooo", Toast.LENGTH_LONG).show();


                    }
                });
                return viewHolder;
            }
        };
        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setReverseLayout(true);
        mLinearLayoutManager.setStackFromEnd(true);

        results.setLayoutManager(mLinearLayoutManager);
        firebaseRecyclerAdapter.startListening();
        results.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.notifyDataSetChanged();


    }
*/


    private void setupViewPager(ViewPager2 viewPager2) {
        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setFocusable(true);
        viewPager2.getDescendantFocusability();
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(1));
        viewPager2.setPageTransformer(compositePageTransformer);
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.top_menu, menu);


        return true;
    }*/
/*protected void onStart(){
        super.onStart();

        if (firebaseRecyclerAdapter != null){
            firebaseRecyclerAdapter.startListening();
        }
}*/
   /* private Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            recommendViewPager.setCurrentItem(recommendViewPager.getCurrentItem() + 1);

        }
    };*/
}