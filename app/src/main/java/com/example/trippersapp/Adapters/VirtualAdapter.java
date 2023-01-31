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

public class VirtualAdapter extends RecyclerView.Adapter<VirtualAdapter.MyViewHolder> {

    Context context;
    ArrayList<Packages> virtualList;


    public VirtualAdapter(Context context, ArrayList<Packages> virtualList) {
        this.context = context;
        this.virtualList = virtualList;
    }


    @NonNull
    @Override
    public VirtualAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_container_recommend, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VirtualAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Packages packages = virtualList.get(position);
        Glide.with(context)
                .load(virtualList.get(position).getPackage_poster())
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
                i.putExtra("days", virtualList.get(position).getDays());
                i.putExtra("nights", virtualList.get(position).getNights());
                i.putExtra("attractions", virtualList.get(position).getPackage_attraction());
                i.putExtra("availability", virtualList.get(position).getPackage_availability());
                i.putExtra("country", virtualList.get(position).getPackage_country());
                i.putExtra("description", virtualList.get(position).getPackage_description());
                i.putExtra("id", virtualList.get(position).getPackage_id());
                i.putExtra("latitude", virtualList.get(position).getPackage_latitude());
                i.putExtra("longitude", virtualList.get(position).getPackage_longitude());
                i.putExtra("name", virtualList.get(position).getPackage_name());
                //photos
                i.putExtra("price", virtualList.get(position).getPackage_price());
                i.putExtra("region", virtualList.get(position).getPackage_region());
                i.putExtra("videourl", virtualList.get(position).getPackage_video());
                context.startActivity(i);

            }
        });

    }

    @Override
    public int getItemCount() {
        return virtualList.size();
    }

    public static class MyViewHolder extends  RecyclerView.ViewHolder{
        RoundedImageView imagePoster;
        TextView textName, textCountry;
        RatingBar ratingBar;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imagePoster = itemView.findViewById(R.id.imagePoster);
            textName = itemView.findViewById(R.id.textName);
            textCountry = itemView.findViewById(R.id.textCountry);
            ratingBar = itemView.findViewById(R.id.ratingBar);
        }

    }
}
