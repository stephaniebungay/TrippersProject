package com.example.trippersapp.Adapters;

public class ViewHolder {/*extends RecyclerView.ViewHolder {

    View mView;
    public ViewHolder(@NonNull View itemView) {
        super(itemView);

        mView = itemView;

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickListener.onItemClick(view,getBindingAdapterPosition());

            }
        });

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mClickListener.ontItemLongClick(view, getBindingAdapterPosition());

                return true;
            }
        });
    }

    public void setDetails(Context ctx, String mposter, String mtitle,
                           String mregion, String mcountry, String mprice){
        RoundedImageView poster;
        TextView title, ratingNum, region, country, price;
        RatingBar ratingBar;

        poster = itemView.findViewById(R.id.imgposter);
        title = itemView.findViewById(R.id.cardtitle);
        ratingNum = itemView.findViewById(R.id.ratingtxt);
        ratingBar = itemView.findViewById(R.id.desrating);
        region = itemView.findViewById(R.id.textregion);
        country = itemView.findViewById(R.id.textcountry);
        price = itemView.findViewById(R.id.pricepax);

        title.setText(mtitle);
        region.setText(mregion);
        country.setText(mcountry);
        price.setText(mprice);
        ratingBar.setRating(3.2f);
        ratingNum.setText("3.2");

        Glide.with(ctx)
                .load(mposter)
                .thumbnail(0.05f)
                .transition(DrawableTransitionOptions.withCrossFade())
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .into(poster);
    }

    private ViewHolder.ClickListener mClickListener;

    public interface ClickListener{
        void onItemClick (View view, int position);
        void ontItemLongClick (View view, int position);
    }

    public void setOnClickListener(ViewHolder.ClickListener clickListener){
        mClickListener = clickListener;
    }*/
}
