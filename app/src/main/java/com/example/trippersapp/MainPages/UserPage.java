package com.example.trippersapp.MainPages;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.trippersapp.R;
import com.example.trippersapp.databinding.ActivityUserPageBinding;
import com.example.trippersapp.login;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserPage extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private ImageView userImage;
    private TextView userName, userEmail;
    private MaterialButton userProfileBtn, userHelpBtn, userPrivacyBtn, logoutBtn;

    private FirebaseAuth firebaseAuth;
    //private DatabaseReference databaseReference;
    //private FirebaseDatabase firebaseDatabase;
    private ActivityUserPageBinding binding;
    private FirebaseAuth.AuthStateListener authStateListener;
    private AccessTokenTracker accessTokenTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);

        binding = ActivityUserPageBinding.inflate((getLayoutInflater()));
        setContentView(binding.getRoot());


       // databaseReference = firebaseDatabase.getReference("fname");
        firebaseAuth = FirebaseAuth.getInstance();
        checkUser();
       // getFullname();

        FacebookSdk.sdkInitialize(getApplicationContext());

        bottomNavigationView = findViewById(R.id.bottomnav);
        bottomNavigationView.setSelectedItemId(R.id.user);

        userImage = findViewById(R.id.userimage);
        userName = findViewById(R.id.username);
        userEmail = findViewById(R.id.useremail);
        userProfileBtn = findViewById(R.id.userprofilebtn);
        userHelpBtn = findViewById(R.id.helpsupportbtn);
        userPrivacyBtn = findViewById(R.id.privacybtn);
        logoutBtn = findViewById(R.id.logoutbtn);

        userProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        userHelpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        userPrivacyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        binding.logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.getInstance().signOut();
                fbLogout();
                finish();;
            }

            private void fbLogout() {
                LoginManager.getInstance().logOut();
                Intent intent = new Intent(UserPage.this, login.class);
                intent.putExtra("finish", true);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_CLEAR_TASK |
                        Intent.FLAG_ACTIVITY_NEW_TASK |
                        Intent.FLAG_ACTIVITY_NO_ANIMATION |
                        Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                finish();
            }
        });

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user!=null){
                    checkUser();
                }
                else{
                    checkUser();
                }
            }
        };

        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(@Nullable AccessToken accessToken, @Nullable AccessToken accessToken1) {
                if(accessToken1 == null){
                    firebaseAuth.getInstance().signOut();
                    checkUser();
                    finish();
                }
            }
        };



    }//end of oncreate



    private void checkUser() {
        //get current user
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser != null){
            String email = firebaseUser.getEmail();
            String name = firebaseUser.getDisplayName();

            binding.useremail.setText(email);
            binding.username.setText(name);


          /*  if(firebaseUser.getPhotoUrl() != null){
                String photoUrl = firebaseUser.getPhotoUrl().toString();
                photoUrl = "https://graph.facebook.com/" + photoUrl + "?type=large";
                Picasso.get().load(photoUrl).into(userImage);
            }
            else{
                userImage.setImageResource(R.drawable.logo);
            }*/
        }
        else{
            //user not logged in
            startActivity(new Intent(this, login.class));
            Toast.makeText(this, "Logged out", Toast.LENGTH_LONG).show();
            finish();


        }
    }

    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }
    protected void onStop(){
        super.onStop();
        if (authStateListener != null){
            firebaseAuth.removeAuthStateListener(authStateListener);
        }
    }
}