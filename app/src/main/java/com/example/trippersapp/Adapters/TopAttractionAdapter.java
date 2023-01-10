package com.example.trippersapp.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.trippersapp.DetailPage.DestinationDetail;
import com.example.trippersapp.Models.Packages;
import com.example.trippersapp.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

public class TopAttractionAdapter extends RecyclerView.Adapter<TopAttractionAdapter.MyViewHolder> {
    Context context;
    ArrayList<Packages> topAttractionList;

    public TopAttractionAdapter(Context context, ArrayList<Packages> topAttractionList) {
        this.context = context;
        this.topAttractionList = topAttractionList;
    }

    @NonNull
    @Override
    public TopAttractionAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.destination_container, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
  /*      if(position == topAttractionList.size() - 2){
            topAttractionViewPager.post(runnable);
        }*/
        Packages packages = topAttractionList.get(position);
       Glide.with(context)
                .load(topAttractionList.get(position).getPackage_poster())
                .thumbnail(0.05f)
                .transition(DrawableTransitionOptions.withCrossFade())
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(holder.imagePoster);
        holder.textName.setText(packages.getPackage_name());
        holder.textCountry.setText(packages.getPackage_country());
        holder.ratingBar.setRating(2.5f);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, DestinationDetail.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("id", topAttractionList.get(position).getPackage_id());
                i.putExtra("attractions", topAttractionList.get(position).getPackage_attractions());
                i.putExtra("availability", topAttractionList.get(position).getPackage_availability());
                i.putExtra("country", topAttractionList.get(position).getPackage_country());
                i.putExtra("description", topAttractionList.get(position).getPackage_description());
                i.putExtra("latitude", topAttractionList.get(position).getPackage_latitude());
                i.putExtra("longitude", topAttractionList.get(position).getPackage_longitude());
                i.putExtra("name", topAttractionList.get(position).getPackage_name());
                //photos
                i.putExtra("price", topAttractionList.get(position).getPackage_price());
                i.putExtra("region", topAttractionList.get(position).getPackage_region());
                i.putExtra("videourl", topAttractionList.get(position).getPackage_video());
                context.startActivity(i);

            }
        });
    }

    @Override
    public int getItemCount() {
        return topAttractionList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        RoundedImageView imagePoster;
        TextView textName, textCountry;
        RatingBar ratingBar;
        public static String desID;
        public  MyViewHolder (@NonNull View itemView) {
            super(itemView);

            imagePoster = itemView.findViewById(R.id.imagePoster1);
            textName = itemView.findViewById(R.id.textName1);
            textCountry = itemView.findViewById(R.id.textCountry1);
            ratingBar = itemView.findViewById(R.id.ratingBar1);

        }
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            topAttractionList.addAll(topAttractionList);
            notifyDataSetChanged();
        }
    };
}