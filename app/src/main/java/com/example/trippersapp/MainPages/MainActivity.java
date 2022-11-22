package com.example.trippersapp.MainPages;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.trippersapp.R;
import com.example.trippersapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {


    ActivityMainBinding binding;
   /* private Toolbar topBar;
    private AppBarLayout appBarLayout;
    private ActionBar actionBar;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());

        /*topBar = findViewById(R.id.topAppBar);
//        ((AppCompatActivity)getActivity()).setSupportActionBar(topBar);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
        appBarLayout = findViewById(R.id.appBarLayout);
        appBarLayout.setOutlineProvider(null);*/

        binding.bottomnav.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.homepage:
                    replaceFragment(new HomeFragment());
                    break;
                case R.id.notification:
                    replaceFragment(new NotifFragment());
                    break;
                case R.id.map:
                    replaceFragment(new MapFragment());
                    break;
                case R.id.booking:
                    replaceFragment(new BookingFragment());
                    break;
                case R.id.user:
                    replaceFragment(new UserFragment());
                    break;
            }
            return true;
        });
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framemain, fragment);
        fragmentTransaction.commit();
    }
}