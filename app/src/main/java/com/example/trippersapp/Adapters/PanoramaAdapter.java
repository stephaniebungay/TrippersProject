package com.example.trippersapp.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.trippersapp.Models.Panorama;
import com.example.trippersapp.PanoSample;
import com.example.trippersapp.R;

import java.util.List;

public class PanoramaAdapter extends RecyclerView.Adapter<PanoramaAdapter.MyViewHolder> {

    Context context;
    List<Panorama> panoramaList;

    public PanoramaAdapter(Context context, List<Panorama> panoramaList){
        this.context = context;
        this.panoramaList = panoramaList;
    }

    @NonNull
    @Override
    public PanoramaAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.panoramaview, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PanoramaAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Panorama panorama = panoramaList.get(position);
        Glide.with(context)
                .load(panoramaList.get(position).getImage())
                .thumbnail(0.05f)
                .transition(DrawableTransitionOptions.withCrossFade())
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .into(holder.poster);
        holder.name.setText(panorama.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, PanoSample.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("destination", panoramaList.get(position).getDestination());
                i.putExtra("image", panoramaList.get(position).getImage());
                i.putExtra("name", panoramaList.get(position).getName());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return panoramaList.size();
    }

    public static class MyViewHolder extends  RecyclerView.ViewHolder{
        ImageView poster;
        TextView name;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            poster = itemView.findViewById(R.id.panoImg);
            name = itemView.findViewById(R.id.panoName);
        }

        }
}
