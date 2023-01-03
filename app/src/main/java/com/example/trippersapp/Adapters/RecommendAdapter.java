package com.example.trippersapp.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.trippersapp.DetailPage.DestinationDetail;
import com.example.trippersapp.Models.Packages;
import com.example.trippersapp.R;
import com.example.trippersapp.TravelBooking;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

public class RecommendAdapter extends RecyclerView.Adapter<RecommendAdapter.MyViewHolder> {
    Context context;
    ArrayList<Packages> recommendList;
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            recommendList.addAll(recommendList);
            notifyDataSetChanged();
        }
    };


    public RecommendAdapter(Context context, ArrayList<Packages> recommendList) {
        this.context = context;
        this.recommendList = recommendList;

    }

    @NonNull
    @Override
    public RecommendAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.destination_container, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
      /*  if (position == recommendList.size() - 2) {
            recommendViewPager.post(runnable);
        }*/

        Packages packages = recommendList.get(position);
        Glide.with(context)
                .load(recommendList.get(position).getPackage_poster())
                .thumbnail(0.05f)
                .transition(DrawableTransitionOptions.withCrossFade())
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .into(holder.imagePoster);

        //Picasso.get().load(String.valueOf(recommendList.get(position).getPackage_poster())).into(holder.imagePoster);
        holder.textName.setText(packages.getPackage_name());
        holder.textCountry.setText(packages.getPackage_country());
        holder.ratingBar.setRating(3.2f);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, DestinationDetail.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("id", recommendList.get(position).getPackage_id());
                i.putExtra("attractions", recommendList.get(position).getPackage_attractions());
                i.putExtra("availability", recommendList.get(position).getPackage_availability());
                i.putExtra("country", recommendList.get(position).getPackage_country());
                i.putExtra("description", recommendList.get(position).getPackage_description());
                i.putExtra("latitude", recommendList.get(position).getPackage_latitude());
                i.putExtra("longitude", recommendList.get(position).getPackage_longitude());
                i.putExtra("name", recommendList.get(position).getPackage_name());
                //photos
                i.putExtra("price", recommendList.get(position).getPackage_price());
                i.putExtra("rating", recommendList.get(position).getPackage_rating());
                i.putExtra("region", recommendList.get(position).getPackage_region());
                i.putExtra("reviewer", recommendList.get(position).getPackage_reviewer());
                i.putExtra("videourl", recommendList.get(position).getPackage_video());
                context.startActivity(i);

                Intent o = new Intent(context, TravelBooking.class);
                o.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                o.putExtra("id", recommendList.get(position).getPackage_id());
                o.putExtra("attractions", recommendList.get(position).getPackage_attractions());
                o.putExtra("availability", recommendList.get(position).getPackage_availability());
                o.putExtra("country", recommendList.get(position).getPackage_country());
                o.putExtra("description", recommendList.get(position).getPackage_description());
                o.putExtra("latitude", recommendList.get(position).getPackage_latitude());
                o.putExtra("longitude", recommendList.get(position).getPackage_longitude());
                o.putExtra("name", recommendList.get(position).getPackage_name());
                //photos
                o.putExtra("price", recommendList.get(position).getPackage_price());
                o.putExtra("rating", recommendList.get(position).getPackage_rating());
                o.putExtra("region", recommendList.get(position).getPackage_region());
                o.putExtra("reviewer", recommendList.get(position).getPackage_reviewer());
                o.putExtra("videourl", recommendList.get(position).getPackage_video());
                context.startActivities(new Intent[]{o, i});


                /*AppCompatActivity activity = (AppCompatActivity) view.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.gmaps,
                                new MapsFragment(packages.getPackage_id(),
                                        packages.getPackage_attractions(),
                                        packages.getPackage_availability(),
                                        packages.getPackage_country(),
                                        packages.getPackage_description(),
                                        packages.getPackage_latitude(),
                                        packages.getPackage_longitude(),
                                        packages.getPackage_name(),
                                        packages.getPackage_photos(),
                                        packages.getPackage_poster(),
                                        packages.getPackage_price(),
                                        packages.getPackage_rating(),
                                        packages.getPackage_region(),
                                        packages.getPackage_reviewer(),
                                        packages.getPackage_video()))
                        .addToBackStack(null).commit();*/


            }
        });

    }

    @Override
    public int getItemCount() {
        return recommendList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        RoundedImageView imagePoster;
        TextView textName, textCountry, titletext, titletext2;
        RatingBar ratingBar;
        VideoView video;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imagePoster = itemView.findViewById(R.id.imagePoster1);
            textName = itemView.findViewById(R.id.textName1);
            textCountry = itemView.findViewById(R.id.textCountry1);
            ratingBar = itemView.findViewById(R.id.ratingBar1);

            /*titletext = itemView.findViewById(R.id.titletext);
            titletext2 = itemView.findViewById(R.id.titletext2);
            video = itemView.findViewById(R.id.video);*/



            /*firstName = itemView.findViewById(R.id.tvfirstName);
            lastName = itemView.findViewById(R.id.tvlastName);
            age = itemView.findViewById(R.id.tvage);*/

                       /*AppCompatActivity activity = (AppCompatActivity) view.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.framemain,
                        new DestinationDetail(packages.getPackage_attractions(),
                                packages.getPackage_availability(),
                                packages.getPackage_country(),
                                packages.getPackage_description(),
                                packages.getPackage_name(),
                                packages.getPackage_photos(),
                                packages.getPackage_poster(),
                                packages.getPackage_price(),
                                packages.getPackage_rating(),
                                packages.getPackage_region(),
                                packages.getPackage_video()))
                        .addToBackStack(null).commit();
*/

        }
    }


}
