package com.example.trippersapp.MainPages;

import static com.facebook.FacebookSdk.getApplicationContext;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.trippersapp.R;
import com.example.trippersapp.login;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserFragment extends Fragment {


    private BottomNavigationView bottomNavigationView;
    private ImageView userImage;
    private TextView userName, userEmail;
    private MaterialButton userProfileBtn, userHelpBtn, userPrivacyBtn, logoutBtn;

    private FirebaseAuth firebaseAuth;
    //private DatabaseReference databaseReference;
    //private FirebaseDatabase firebaseDatabase;

    private FirebaseAuth.AuthStateListener authStateListener;
    private AccessTokenTracker accessTokenTracker;

    Activity context;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_user, container, false);
        // Inflate the layout for this fragment
        context = getActivity();

        // databaseReference = firebaseDatabase.getReference("fname");
        firebaseAuth = FirebaseAuth.getInstance();
        checkUser();
        // getFullname();

        FacebookSdk.sdkInitialize(getApplicationContext());


        userImage = view.findViewById(R.id.userimage);
        userName = context.findViewById(R.id.username);
        userEmail = context.findViewById(R.id.useremail);
        userProfileBtn = view.findViewById(R.id.userprofilebtn);
        userHelpBtn = view.findViewById(R.id.helpsupportbtn);
        userPrivacyBtn = view.findViewById(R.id.privacybtn);


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
                    getActivity().finish();
                }
            }
        };
        return inflater.inflate(R.layout.fragment_user, container, false);
    }




    private void checkUser() {
        //get current user
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser != null){
            String email = firebaseUser.getEmail();
            String name = firebaseUser.getDisplayName();

          /*  userEmail.setText(email);
            userName.setText(name);
*/


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
            startActivity(new Intent(getActivity(), login.class));
            Toast.makeText(getActivity(), "Logged out", Toast.LENGTH_LONG).show();
            getActivity().finish();


        }
    }


    public void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
        logoutBtn = context.findViewById(R.id.logoutbtn);

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.getInstance().signOut();

                LoginManager.getInstance().logOut();
                Intent intent = new Intent(context, login.class);
                intent.putExtra("finish", true);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_CLEAR_TASK |
                        Intent.FLAG_ACTIVITY_NEW_TASK |
                        Intent.FLAG_ACTIVITY_NO_ANIMATION |
                        Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                context.finish();

            }

        });
    }
    public void onStop(){
        super.onStop();
        if (authStateListener != null){
            firebaseAuth.removeAuthStateListener(authStateListener);
        }
    }

}