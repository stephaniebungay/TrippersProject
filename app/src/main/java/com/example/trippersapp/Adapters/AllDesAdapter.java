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

public class AllDesAdapter extends RecyclerView.Adapter<AllDesAdapter.MyViewHolder> {
    Context context;
    ArrayList<Packages> allDesList;
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            allDesList.addAll(allDesList);
            notifyDataSetChanged();
        }
    };


    public AllDesAdapter(Context context, ArrayList<Packages> allDesList) {
        this.context = context;
        this.allDesList = allDesList;

    }

    @NonNull
    @Override
    public AllDesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.destination_container, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        Packages packages = allDesList.get(position);
        Glide.with(context)
                .load(allDesList.get(position).getPackage_poster())
                .thumbnail(0.05f)
                .transition(DrawableTransitionOptions.withCrossFade())
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .into(holder.imagePoster);

        holder.textName.setText(packages.getPackage_name());
        holder.textCountry.setText(packages.getPackage_country());
        holder.ratingBar.setRating(3.2f);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, DestinationDetail.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("days", allDesList.get(position).getDays());
                i.putExtra("nights", allDesList.get(position).getNights());
                i.putExtra("attractions", allDesList.get(position).getPackage_attraction());
                i.putExtra("availability", allDesList.get(position).getPackage_availability());
                i.putExtra("country", allDesList.get(position).getPackage_country());
                i.putExtra("description", allDesList.get(position).getPackage_description());
                i.putExtra("id", allDesList.get(position).getPackage_id());
                i.putExtra("latitude", allDesList.get(position).getPackage_latitude());
                i.putExtra("longitude", allDesList.get(position).getPackage_longitude());
                i.putExtra("name", allDesList.get(position).getPackage_name());
                i.putExtra("images", String.valueOf(allDesList.get(position).getPackage_images()));
                i.putExtra("price", allDesList.get(position).getPackage_price());
                i.putExtra("region", allDesList.get(position).getPackage_region());
                i.putExtra("videourl", allDesList.get(position).getPackage_video());
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return allDesList.size();
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
