package com.example.trippersapp.MainPages;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.example.trippersapp.Adapters.RecommendAdapter;
import com.example.trippersapp.Adapters.TopAttractionAdapter;
import com.example.trippersapp.Adapters.TopDestinationAdapter;
import com.example.trippersapp.Models.Packages;
import com.example.trippersapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class HomeFragment extends Fragment {

    private DatabaseReference database;
    private ViewPager2 recommendViewPager, topDesViewPager, topAttractionViewPager;
    private ArrayList<Packages> recommendList, topDestinationList, topAttractionList;
    private RecommendAdapter recommendAdapter;
    private TopDestinationAdapter topDestinationAdapter;
    private TopAttractionAdapter topAttractionAdapter;
    private Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            recommendViewPager.setCurrentItem(recommendViewPager.getCurrentItem() + 1);
        }
    };


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);


        recommendViewPager = view.findViewById(R.id.recommendViewPager);
        topDesViewPager = view.findViewById(R.id.topDesViewPager);
        topAttractionViewPager = view.findViewById(R.id.topAttractionViewPager);

        setupViewPager(recommendViewPager);
        setupViewPager(topDesViewPager);
        setupViewPager(topAttractionViewPager);

        database = FirebaseDatabase.getInstance().getReference("Packages");
        recommendList = new ArrayList<>();
        topDestinationList = new ArrayList<>();
        topAttractionList = new ArrayList<>();

        recommendAdapter = new RecommendAdapter(getContext(), recommendList, recommendViewPager);
        recommendViewPager.setAdapter(recommendAdapter);

        topDestinationAdapter = new TopDestinationAdapter(getContext(), topDestinationList, topDesViewPager);
        topDesViewPager.setAdapter(topDestinationAdapter);

        topAttractionAdapter = new TopAttractionAdapter(getContext(), topAttractionList, topAttractionViewPager);
        topAttractionViewPager.setAdapter(topAttractionAdapter);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Packages packages = dataSnapshot.getValue(Packages.class);
                    recommendList.add(packages);
                    topDestinationList.add(packages);
                    topAttractionList.add(packages);
                }
                recommendAdapter.notifyDataSetChanged();
                topDestinationAdapter.notifyDataSetChanged();
                topAttractionAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view;
    }

    private void setupViewPager(ViewPager2 viewPager2) {
        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(1));
        viewPager2.setPageTransformer(compositePageTransformer);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        //super.onCreateOptionsMenu(menu);
        getActivity().getMenuInflater().inflate(R.menu.top_menu, menu);
        return true;
    }
}