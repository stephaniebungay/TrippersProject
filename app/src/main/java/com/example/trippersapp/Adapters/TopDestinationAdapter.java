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

public class TopDestinationAdapter extends RecyclerView.Adapter<TopDestinationAdapter.MyViewHolder> {
    Context context;
    ArrayList<Packages> topDestinationList;
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            topDestinationList.addAll(topDestinationList);
            notifyDataSetChanged();
        }
    };


    public TopDestinationAdapter(Context context, ArrayList<Packages> topDestinationList) {
        this.context = context;
        this.topDestinationList = topDestinationList;
    }

    @NonNull
    @Override
    public TopDestinationAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.destination_container, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TopDestinationAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
       /* if(position == topDestinationList.size() - 2){
            topDesViewPager.post(runnable);
        }*/
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

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, DestinationDetail.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("id", topDestinationList.get(position).getPackage_id());
                i.putExtra("attractions", topDestinationList.get(position).getPackage_attractions());
                i.putExtra("availability", topDestinationList.get(position).getPackage_availability());
                i.putExtra("country", topDestinationList.get(position).getPackage_country());
                i.putExtra("description", topDestinationList.get(position).getPackage_description());
                i.putExtra("latitude", topDestinationList.get(position).getPackage_latitude());
                i.putExtra("longitude", topDestinationList.get(position).getPackage_longitude());
                i.putExtra("name", topDestinationList.get(position).getPackage_name());
                //photos
                i.putExtra("price", topDestinationList.get(position).getPackage_price());
                i.putExtra("rating", topDestinationList.get(position).getPackage_rating());
                i.putExtra("region", topDestinationList.get(position).getPackage_region());
                i.putExtra("reviewer", topDestinationList.get(position).getPackage_reviewer());
                i.putExtra("videourl", topDestinationList.get(position).getPackage_video());

                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return topDestinationList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        RoundedImageView imagePoster;
        TextView textName, textCountry;
        RatingBar ratingBar;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imagePoster = itemView.findViewById(R.id.imagePoster1);
            textName = itemView.findViewById(R.id.textName1);
            textCountry = itemView.findViewById(R.id.textCountry1);
            ratingBar = itemView.findViewById(R.id.ratingBar1);
        }
    }
}

