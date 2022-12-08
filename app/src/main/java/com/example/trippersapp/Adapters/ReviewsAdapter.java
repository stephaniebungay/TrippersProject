package com.example.trippersapp.Adapters;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.trippersapp.Extra.TextViewEx;
import com.example.trippersapp.Models.Reviews;
import com.example.trippersapp.R;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewViewHolder> {


    private Context context;
    private List<Reviews> mData;

    public ReviewsAdapter(Context context, List<Reviews> mData) {
        this.context = context;
        this.mData = mData;
    }


    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.reviews_layout, parent, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        Glide.with(context).load(mData.get(position).getUser_pfp()).into(holder.pfp);
        holder.name.setText(mData.get(position).getUsername());
        holder.rating.setRating(mData.get(position).getRating());
        holder.content.setText(mData.get(position).getContent());
        holder.rdate.setText(timestampToString((Long)mData.get(position).getTimestamp()));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder{

        ImageView pfp;
        TextView name, rdate;
        RatingBar rating;
        TextViewEx content;

        public ReviewViewHolder(View itemView){
            super(itemView);
            pfp = itemView.findViewById(R.id.reviewerpfp);
            name = itemView.findViewById(R.id.reviewername);
            rdate = itemView.findViewById(R.id.reviewdate);
            rating = itemView.findViewById(R.id.reviewerrating);
            content = itemView.findViewById(R.id.reviewercomment);
        }
    }

    private String timestampToString(long time) {
        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTimeInMillis(time);
        String date = DateFormat.format("hh-mm", calendar).toString();
        return date;
    }

}
