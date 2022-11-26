package com.example.trippersapp.MainPages;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

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

    private long backPressTime;
    Toast toast;
    View toastView;
            ;

    @Override
    public void onBackPressed() {

        if (backPressTime + 2000 > System.currentTimeMillis()) {
//            super.onBackPressed();

            //Function: To exit the application
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            toast.cancel();
            return;
        } else {
            toast = Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP, 0, 0);
            toastView = toast.getView();
            toastView.setBackgroundColor(getResources().getColor(R.color.white));
            toast.setView(toastView);
            toast.show();
        }
        backPressTime = System.currentTimeMillis();
    }

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