package com.example.trippersapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecommendAdapter extends RecyclerView.Adapter<RecommendAdapter.MyViewHolder> {
    Context context;
    ArrayList<Recommendation> recommendationList;
    private ViewPager2 recommendViewPager;


    public RecommendAdapter(Context context, ArrayList<Recommendation> recommendationList, ViewPager2 recommendViewPager) {
        this.context = context;
        this.recommendationList = recommendationList;
        this.recommendViewPager = recommendViewPager;

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_container_recommend,parent,false);
        return  new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if(position == recommendationList.size() - 2){
            recommendViewPager.post(runnable);
        }

        Recommendation recommendation = recommendationList.get(position);
        Picasso.get().load(String.valueOf(recommendationList.get(position).getPackage_photos())).into(holder.imagePoster);
        holder.textName.setText(recommendation.getPackage_name());
        holder.textCountry.setText(recommendation.getPackage_country());
        holder.ratingBar.setRating(3.2f);



    }

    @Override
    public int getItemCount() {
        return recommendationList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        RoundedImageView imagePoster;
        TextView textName, textCountry;
        RatingBar ratingBar;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imagePoster = itemView.findViewById(R.id.imagePoster);
            textName = itemView.findViewById(R.id.textName);
            textCountry = itemView.findViewById(R.id.textCountry);
            ratingBar = itemView.findViewById(R.id.ratingBar);

            /*firstName = itemView.findViewById(R.id.tvfirstName);
            lastName = itemView.findViewById(R.id.tvlastName);
            age = itemView.findViewById(R.id.tvage);*/

        }
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            recommendationList.addAll(recommendationList);
            notifyDataSetChanged();
        }
    };




}
