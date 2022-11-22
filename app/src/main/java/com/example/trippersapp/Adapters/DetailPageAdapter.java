package com.example.trippersapp.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
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
            case 0:
                return new AboutFragment();
            case 1:
                return new AttractionsFragment();
            case 2:
                return new ReviewsFragment();
            default:
                return new AboutFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
