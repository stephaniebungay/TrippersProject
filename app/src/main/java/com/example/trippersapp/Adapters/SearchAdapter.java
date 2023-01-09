package com.example.trippersapp.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
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
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder> implements Filterable {
    Context context;
    ArrayList<Packages> searchList;
    ArrayList<Packages> searchListFull;

    public SearchAdapter(Context context, ArrayList<Packages> searchList) {
        this.context = context;
        this.searchList = searchList;
    }

    @NonNull
    @Override
    public SearchAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.destination_landscape_container, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
       /*if(position == topAttractionList.size() - 2){
            topAttractionViewPager.post(runnable);
        }*/
        Packages packages = searchList.get(position);
        Glide.with(context)
                .load(searchList.get(position).getPackage_poster())
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
                Intent i = new Intent(context, DestinationDetail.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("id", searchList.get(position).getPackage_id());
                i.putExtra("attractions", searchList.get(position).getPackage_attractions());
                i.putExtra("availability", searchList.get(position).getPackage_availability());
                i.putExtra("country", searchList.get(position).getPackage_country());
                i.putExtra("description", searchList.get(position).getPackage_description());
                i.putExtra("latitude", searchList.get(position).getPackage_latitude());
                i.putExtra("longitude", searchList.get(position).getPackage_longitude());
                i.putExtra("name", searchList.get(position).getPackage_name());
                //photos
                i.putExtra("price", searchList.get(position).getPackage_price());
                i.putExtra("rating", searchList.get(position).getPackage_rating());
                i.putExtra("region", searchList.get(position).getPackage_region());
                i.putExtra("reviewer", searchList.get(position).getPackage_reviewer());
                i.putExtra("videourl", searchList.get(position).getPackage_video());
                context.startActivity(i);

            }
        });
    }

    @Override
    public int getItemCount() {
        return searchList.size();
    }

    @Override
    public Filter getFilter() {
        return packagefilter;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

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

    private Filter packagefilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<Packages> filterList = new ArrayList<>();
            if (charSequence == null || charSequence.length() == 0) {
                filterList.addAll(searchListFull);
            } else {
                String pattern = charSequence.toString().toLowerCase().trim();
                for (Packages item : searchListFull) {
                    if (item.getPackage_name().toLowerCase().contains(pattern)) {
                        filterList.add(item);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filterList;
            return (FilterResults) filterList;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            searchList.clear();
            /*searchList.add(filterResults.values);*/
            notifyDataSetChanged();
        }
    };


    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            searchList.addAll(searchList);
            notifyDataSetChanged();
        }
    };

}