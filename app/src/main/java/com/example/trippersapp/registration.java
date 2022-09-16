package com.example.trippersapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class registration extends AppCompatActivity implements View.OnClickListener{

    //create object of DatabaseReference class to access firebase's Realtime Database
    DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://trippersapp-cffca-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();

    private EditText fName, lName, contactNo, emailAdd, passWord, reType;
    private CheckBox agreementBx;
    private ProgressBar progbar;
    private ImageButton backButton;
    private Button regbtn;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mAuth = FirebaseAuth.getInstance();

        fName = (EditText) findViewById(R.id.fname);
        lName = (EditText) findViewById(R.id.lname);
        contactNo = (EditText) findViewById(R.id.contactno);
        emailAdd = (EditText) findViewById(R.id.emailadd);
        passWord = (EditText) findViewById(R.id.password);
        reType = (EditText) findViewById(R.id.retype);
        agreementBx = (CheckBox) findViewById(R.id.agreementbox);

        backButton = (ImageButton) findViewById(R.id.backbtn);
        backButton.setOnClickListener(this);
        regbtn = (Button) findViewById(R.id.registerbtn);
        regbtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.backbtn:
                startActivity(new Intent(this, login.class));
                break;
            case R.id.registerbtn:
                registerUser();
                break;
        }

    }

    private void registerUser() {
        String fnametxt = fName.getText().toString().trim();
        String lnametxt = lName.getText().toString().trim();
        String contacttxt = contactNo.getText().toString().trim();
        String emailaddtxt = emailAdd.getText().toString().trim();
        String passtxt = passWord.getText().toString().trim();
        String retypetxt = reType.getText().toString().trim();

        if(fnametxt.isEmpty()){
            fName.setError("First name is required!");
            fName.requestFocus();
            return;
        }

        if(lnametxt.isEmpty()){
            lName.setError("Last name is required!");
            lName.requestFocus();
            return;
        }

        if(contacttxt.isEmpty()){
            contactNo.setError("Phone number is required");
            contactNo.requestFocus();
            return;
        }

        if(emailaddtxt.isEmpty()){
            emailAdd.setError("Email address is required");
            emailAdd.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(emailaddtxt).matches()){
            emailAdd.setError("Please provide a valid email address!");
            emailAdd.requestFocus();
            return;
        }
        if(passtxt.isEmpty()){
            passWord.setError("Password is required!");
            passWord.requestFocus();
            return;
        }
        if(retypetxt.isEmpty()){
            reType.setError("Confirm password!");
            reType.requestFocus();
            return;
        }
        if(passtxt.length() < 6){
            passWord.setError("Password should be atleast 6 characters!");
            passWord.requestFocus();
            return;
        }
        if(!passtxt.equals(retypetxt)){
            Toast.makeText(this, "Password is not matching", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!agreementBx.isChecked()){
            Toast.makeText(this, "You must accept our Terms and Services", Toast.LENGTH_LONG).show();
            agreementBx.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(emailaddtxt, passtxt)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            User user = new User(fnametxt, lnametxt, contacttxt, emailaddtxt, passtxt);

                            FirebaseDatabase.getInstance("https://trippersapp-cffca-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            FirebaseUser userr = FirebaseAuth.getInstance().getCurrentUser();
                                            UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(fnametxt +" " + lnametxt).build();
                                            userr.updateProfile(profileChangeRequest);
                                            if(task.isSuccessful()){
                                                userr.sendEmailVerification();
                                                startActivity(new Intent(registration.this, verifypage.class));
                                                finish();
                                            }else{
                                                Toast.makeText(registration.this, "Failed to register. Try again!", Toast.LENGTH_LONG).show();
                                                return;
                                            }
                                        }
                                    });
                                    }else{
                                        Toast.makeText(registration.this, "Email address is already taken!", Toast.LENGTH_LONG).show();
                                        return;

                        }
                    }
                });

    }
}