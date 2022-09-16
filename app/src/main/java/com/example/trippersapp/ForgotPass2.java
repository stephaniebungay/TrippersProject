package com.example.trippersapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPass2 extends AppCompatActivity implements View.OnClickListener {

    private Button back2login, resendbtn;
    private TextView emailCopy;
    private EditText emailadd;
    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass2);

        back2login = (Button) findViewById(R.id.back2login);
        resendbtn = (Button) findViewById(R.id.resendbtn);

        auth = FirebaseAuth.getInstance();
        emailCopy = findViewById(R.id.emailcopy);
        emailadd = findViewById(R.id.emailbx);

        String email = getIntent().getStringExtra("emailaddress");

        emailCopy.setText(email);




        resendbtn.setOnClickListener(this);
        back2login.setOnClickListener(this);
    }

        public void onClick (View view) {
            switch (view.getId()) {
                case R.id.back2login:
                    startActivity(new Intent(getApplicationContext(), login.class));
                    break;

                case R.id.resendbtn:
                    resendCode();
                    break;
            }
        }




    private void resendCode() {
        String email = emailCopy.getText().toString().trim();
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(ForgotPass2.this, "Password reset link sent!", Toast.LENGTH_LONG).show();
                return;
            }
        });

    }


}