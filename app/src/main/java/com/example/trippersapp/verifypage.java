package com.example.trippersapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trippersapp.databinding.ActivityVerifypageBinding;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class verifypage extends AppCompatActivity {
    private TextView verifyemail;
    private EditText emailadd;
    private Button proceedlogin;
    private GoogleSignInClient googleSignInClient;
    private FirebaseAuth firebaseAuth;
    private ActivityVerifypageBinding binding;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verifypage);
        binding = ActivityVerifypageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseAuth = FirebaseAuth.getInstance();
        checkUser();

        verifyemail = findViewById(R.id.verifyemail);
        proceedlogin = findViewById(R.id.proceedlogin);

        String email = getIntent().getStringExtra("emailaddress");
        verifyemail.setText(email);

        binding.proceedlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                checkUser();
                startActivity(new Intent(verifypage.this, login.class));
                finish();
            }
        });
    }

    private void checkUser() {
        //get current user
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser == null){
            //user not logged in
            startActivity(new Intent(this, login.class));
            //Toast.makeText(this, "Logged out", Toast.LENGTH_LONG).show();
            finish();
        }
        else{
            //user logged in
            //get user info
            String email = firebaseUser.getEmail();
            //set email
            //binding.loginemail.setText(email);
        }
    }
}