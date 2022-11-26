package com.example.trippersapp.Adapters;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.trippersapp.DetailPage.AboutFragment;
import com.example.trippersapp.DetailPage.AttractionsFragment;
import com.example.trippersapp.DetailPage.ReviewsFragment;

public class DetailPageAdapter extends FragmentStateAdapter {

    public DetailPageAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch(position){
            case 1:
                return new AttractionsFragment();
            case 2:
                return new ReviewsFragment();
            default:
                return new AboutFragment();
        }
    }

/*    @NonNull
    @Override
    public DetailPageAdapter.DetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull DetailPageAdapter.DetailViewHolder holder, int position) {

    }*/

    @Override
    public int getItemCount() {
        return 3;
    }

    public static class DetailViewHolder extends RecyclerView.ViewHolder{

        public DetailViewHolder(@NonNull View itemView){
            super (itemView);
        }

    }
}
