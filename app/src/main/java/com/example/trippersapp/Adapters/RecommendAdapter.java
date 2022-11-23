package com.example.trippersapp.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.trippersapp.DetailPage.DestinationDetail;
import com.example.trippersapp.Models.Packages;
import com.example.trippersapp.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

public class RecommendAdapter extends RecyclerView.Adapter<RecommendAdapter.MyViewHolder> {
    Context context;
    ArrayList<Packages> recommendList;
    private ViewPager2 recommendViewPager;



    public RecommendAdapter(Context context, ArrayList<Packages> recommendList, ViewPager2 recommendViewPager) {
        this.context = context;
        this.recommendList = recommendList;
        this.recommendViewPager = recommendViewPager;

    }


    @NonNull
    @Override
    public RecommendAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_container_recommend,parent,false);
        return  new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        if(position == recommendList.size() - 2){
            recommendViewPager.post(runnable);
        }

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
                AppCompatActivity activity=(AppCompatActivity)view.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.framemain,new DestinationDetail(packages.getPackage_attractions(), packages.getPackage_availability(), packages.getPackage_country(), packages.getPackage_description(), packages.getPackage_name(), packages.getPackage_photos(), packages.getPackage_poster(), packages.getPackage_price(), packages.getPackage_rating(), packages.getPackage_region(), packages.getPackage_video())).addToBackStack(null).commit();

           /* Intent i = new Intent(context, DestinationDetailAct.class);
                i.putExtra("name", recommendList.get(position).getPackage_name());
                i.putExtra("region", recommendList.get(position).getPackage_region());
                i.putExtra("country", recommendList.get(position).getPackage_country());
                i.putExtra("videourl", recommendList.get(position).getPackage_video());
                context.startActivity(i);*/
            }
        });

    }

    @Override
    public int getItemCount() {
        return recommendList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        RoundedImageView imagePoster;
        TextView textName, textCountry, titletext, titletext2;
        RatingBar ratingBar;
        VideoView video;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imagePoster = itemView.findViewById(R.id.imagePoster);
            textName = itemView.findViewById(R.id.textName);
            textCountry = itemView.findViewById(R.id.textCountry);
            ratingBar = itemView.findViewById(R.id.ratingBar);

            /*titletext = itemView.findViewById(R.id.titletext);
            titletext2 = itemView.findViewById(R.id.titletext2);
            video = itemView.findViewById(R.id.video);*/



            /*firstName = itemView.findViewById(R.id.tvfirstName);
            lastName = itemView.findViewById(R.id.tvlastName);
            age = itemView.findViewById(R.id.tvage);*/

        }
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            recommendList.addAll(recommendList);
            notifyDataSetChanged();
        }
    };





}
