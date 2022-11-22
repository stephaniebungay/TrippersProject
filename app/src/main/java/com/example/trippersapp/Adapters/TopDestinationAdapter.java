package com.example.trippersapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.trippersapp.Models.Packages;
import com.example.trippersapp.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

public class TopDestinationAdapter extends RecyclerView.Adapter<TopDestinationAdapter.MyViewHolder> {
    Context context;
    ArrayList<Packages> topDestinationList;
    private ViewPager2 topDesViewPager;

    public TopDestinationAdapter(Context context, ArrayList<Packages> topDestinationList, ViewPager2 topDesViewPager) {
        this.context = context;
        this.topDestinationList = topDestinationList;
        this.topDesViewPager = topDesViewPager;
    }


    @NonNull
    @Override
    public TopDestinationAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_container_recommend, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TopDestinationAdapter.MyViewHolder holder, int position) {
        if(position == topDestinationList.size() - 2){
            topDesViewPager.post(runnable);
        }
        Packages packages = topDestinationList.get(position);
        Glide.with(context)
                .load(topDestinationList.get(position).getPackage_poster())
                .thumbnail(0.05f)
                .transition(DrawableTransitionOptions.withCrossFade())
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(holder.imagePoster);
        holder.textName.setText(packages.getPackage_name());
        holder.textCountry.setText(packages.getPackage_country());
        holder.ratingBar.setRating(3.2f);
    }

    @Override
    public int getItemCount() {
        return topDestinationList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        RoundedImageView imagePoster;
        TextView textName, textCountry;
        RatingBar ratingBar;
        public  MyViewHolder (@NonNull View itemView) {
            super(itemView);

            imagePoster = itemView.findViewById(R.id.imagePoster);
            textName = itemView.findViewById(R.id.textName);
            textCountry = itemView.findViewById(R.id.textCountry);
            ratingBar = itemView.findViewById(R.id.ratingBar);
        }
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            topDestinationList.addAll(topDestinationList);
            notifyDataSetChanged();
        }
    };
    }

