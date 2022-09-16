package com.example.trippersapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity implements View.OnClickListener {

    private EditText emailbox;
    private Button sendbtn;
    private ImageButton forgbackbtn2;

    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        emailbox = (EditText) findViewById(R.id.emailbx);
        sendbtn = (Button) findViewById(R.id.forgotpassbtn);
        forgbackbtn2 = (ImageButton) findViewById(R.id.backbtn2);
        auth = FirebaseAuth.getInstance();

        sendbtn.setOnClickListener(this);
        forgbackbtn2.setOnClickListener(this);

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.forgotpassbtn:
                resetPassword();
                break;

            case R.id.backbtn2:
                startActivity(new Intent(getApplicationContext(), login.class));
                break;
        }

    }

    private void resetPassword() {
        String email = emailbox.getText().toString().trim();
        if(email.isEmpty()){
            emailbox.setError("Email is required!");
            emailbox.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailbox.setError("Please provide valid email!");
            emailbox.requestFocus();
            return;
        }
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override

            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Intent intent = new Intent(ForgotPassword.this, ForgotPass2.class);
                    intent.putExtra("emailaddress", email);
                    startActivity(new Intent(ForgotPassword.this, ForgotPass2.class));
                    //startActivity(intent);


                }
                else{
                    Toast.makeText(ForgotPassword.this, "Try again! Make sure you have a registered account!", Toast.LENGTH_SHORT).show();
                    return;


                }

            }

        });



    }


}