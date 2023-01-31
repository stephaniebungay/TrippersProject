package com.example.trippersapp.Adapters;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trippersapp.DetailPage.BookingPopUp;
import com.example.trippersapp.Models.Bookings;
import com.example.trippersapp.R;

import java.util.ArrayList;

public class BookedAdapter extends RecyclerView.Adapter<BookedAdapter.MyViewHolder> {

    Context context;
    ArrayList<Bookings> bookingList;

    public BookedAdapter(Context context, ArrayList<Bookings> bookingList) {
        this.context = context;
        this.bookingList = bookingList;
    }


    @NonNull
    @Override
    public BookedAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.booked_packages, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BookedAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Bookings bookings = bookingList.get(position);
        /*Glide.with(context)
                .load(packageList.get(position).getPackage_poster())
                .thumbnail(0.05f)
                .transition(DrawableTransitionOptions.withCrossFade())
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .into(holder.poster);*/
        holder.place.setText(bookings.getDestination());
        holder.region.setText(bookings.getRegion() +", " + bookings.getCountry());
        holder.pax.setText("For "+bookings.getPax()+ " Pax");
        holder.process.setText(bookings.getProcess());
        Dialog dialog = new Dialog(context);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(context, BookingPopUp.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("country", bookingList.get(position).getCountry());
                i.putExtra("customer", bookingList.get(position).getCustomer());
                i.putExtra("date", bookingList.get(position).getDate());
                i.putExtra("dateEnd", bookingList.get(position).getDateEnd());
                i.putExtra("destination", bookingList.get(position).getDestination());
                i.putExtra("email", bookingList.get(position).getEmail());
                i.putExtra("note", bookingList.get(position).getNote());
                i.putExtra("pax", bookingList.get(position).getPax());
                i.putExtra("payment", bookingList.get(position).getPayment());
                i.putExtra("phone", bookingList.get(position).getPhone());
                i.putExtra("process", bookingList.get(position).getProcess());
                i.putExtra("region", bookingList.get(position).getRegion());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookingList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView place, region, country, name, email, phone, pax, total, process;
        Dialog dialog;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);


            place = itemView.findViewById(R.id.pname);
            region = itemView.findViewById(R.id.pplace);
            pax = itemView.findViewById(R.id.ppax);
            process = itemView.findViewById(R.id.process);


        }
    }
}
