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
import com.example.trippersapp.DetailPage.DestinationDetailAct;
import com.example.trippersapp.Models.Packages;
import com.example.trippersapp.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

public class PackageAdapter extends RecyclerView.Adapter<PackageAdapter.MyViewHolder> {

    Context context;
    ArrayList<Packages> packageList;

    public PackageAdapter(Context context, ArrayList<Packages> packageList) {
        this.context = context;
        this.packageList = packageList;
    }


    @NonNull
    @Override
    public PackageAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.destination_landscape_container, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PackageAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Packages packages = packageList.get(position);
        Glide.with(context)
                .load(packageList.get(position).getPackage_poster())
                .thumbnail(0.05f)
                .transition(DrawableTransitionOptions.withCrossFade())
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .into(holder.poster);
        holder.title.setText(packages.getPackage_name());
        holder.region.setText(packages.getPackage_region());
        holder.country.setText(packages.getPackage_country());
        holder.ratingBar.setRating(3.2f);
        holder.price.setText(packages.getPackage_price());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, DestinationDetailAct.class);
                i.putExtra("name", packageList.get(position).getPackage_name());
                i.putExtra("region", packageList.get(position).getPackage_region());
                i.putExtra("country", packageList.get(position).getPackage_country());
                i.putExtra("videourl", packageList.get(position).getPackage_video());
                i.putExtra("description", packageList.get(position).getPackage_description());
                i.putExtra("attractions", packageList.get(position).getPackage_attractions());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return packageList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        RoundedImageView poster;
        TextView title, ratingNum, region, country, price;
        RatingBar ratingBar;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            poster = itemView.findViewById(R.id.imgposter);
            title = itemView.findViewById(R.id.cardtitle);
            ratingNum = itemView.findViewById(R.id.ratingtxt);
            ratingBar = itemView.findViewById(R.id.desrating);
            region = itemView.findViewById(R.id.textregion);
            country = itemView.findViewById(R.id.textcountry);
            price = itemView.findViewById(R.id.pricepax);
        }
    }
}
