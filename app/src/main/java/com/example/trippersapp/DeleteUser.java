package com.example.trippersapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.trippersapp.LoginRegistration.login;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DeleteUser extends AppCompatActivity {

    private Toolbar topBar;
    private AppBarLayout appBarLayout;
    private MaterialButton delete;
    private EditText email, password;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_user);

        topBar = findViewById(R.id.topAppBar2);
        appBarLayout = findViewById(R.id.appBarLayout2);
        setSupportActionBar(topBar);
        getSupportActionBar().setTitle("Delete Account");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        email = findViewById(R.id.emailbb);
        password = findViewById(R.id.passbb);

        delete = findViewById(R.id.delbtn);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteAccount(email.getText().toString(), password.getText().toString());
            }
        });


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    private void  deleteAccount(String emailtxt, String passtxt){

        user = FirebaseAuth.getInstance().getCurrentUser();
        AuthCredential credential = EmailAuthProvider.getCredential(emailtxt, passtxt);

        if(user != null){
            user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                startActivity(new Intent(DeleteUser.this, login.class));
                                Toast.makeText(DeleteUser.this, "Deleted User Successfully", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            });
        }


    }
}