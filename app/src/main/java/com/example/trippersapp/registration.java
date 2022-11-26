package com.example.trippersapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.trippersapp.Models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Checked;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.mobsandgeeks.saripaar.annotation.Pattern;

import java.util.List;

public class registration extends AppCompatActivity implements Validator.ValidationListener {

    FirebaseFirestore firebaseFirestore;
    protected boolean validated;
    protected Validator validator;
//    DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://trippersapp-cffca-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();
    private String TAG = registration.class.getSimpleName();
    private Context context;

    @NotEmpty(message = "First name is required")
    private EditText fName;

    @NotEmpty(message = "Last name is required")
    private EditText lName;

    @NotEmpty(message = "Last name is required")
    @Pattern(regex = RegexValue.MOBILENUMBER)
    private EditText contactNo;

    @NotEmpty
    @Email
    private EditText emailAdd;

    @NotEmpty
    @Length(min = 6, message = "Password should be atleast 6 character")
    @Password
    private EditText passWord;

    @NotEmpty
    @ConfirmPassword
    private EditText reType;
    @NotEmpty
    @Checked
    private CheckBox agreementBx;

    private ImageButton backButton;
    private MaterialButton regbtn;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Logger.LogView(TAG, "onCreate", "");

        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        init();
        validator = new Validator(this);
        validator.setValidationListener(this);

    }

    private void registerUser() {
        User user = new User();

        String fnametxt = fName.getText().toString().trim();
        String lnametxt = lName.getText().toString().trim();
        String contacttxt = contactNo.getText().toString().trim();
        String emailaddtxt = emailAdd.getText().toString().trim();
        String passtxt = passWord.getText().toString().trim();

        user.setFname(fnametxt);
        user.setLname(lnametxt);
        user.setContactno(contacttxt);
        user.setEmailadd(emailaddtxt);
        user.setPassword(passtxt);

        mAuth.createUserWithEmailAndPassword(emailaddtxt, passtxt)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                           @Override
                                           public void onComplete(@NonNull Task<AuthResult> task) {

                                               if (task.isSuccessful()) {
                                                   FirebaseUser userAuth = FirebaseAuth.getInstance().getCurrentUser();
                                                   UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(fnametxt + " " + lnametxt).build();
                                                   userAuth.updateProfile(profileChangeRequest);
                                                   firebaseFirestore.collection("User").document(emailaddtxt).set(user);
                                                   DocumentReference documentReference = firebaseFirestore.collection("User").document();
                                                   documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                       @Override
                                                       public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                           if (documentSnapshot.exists()) {
                                                               Toast.makeText(registration.this, "Account already registered!", Toast.LENGTH_LONG).show();
                                                           } else {
                                                               userAuth.sendEmailVerification();
                                                               user.setFname(fnametxt);
                                                               user.setLname(lnametxt);
                                                               user.setContactno(contacttxt);
                                                               user.setEmailadd(emailaddtxt);
                                                               user.setPassword(passtxt);
                                                               firebaseFirestore.collection("User").document(emailaddtxt).set(user);

                                                               startActivity(new Intent(registration.this, verifypage.class));
                                                               finish();
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


                            /*FirebaseDatabase.getInstance("https://trippersapp-cffca-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            FirebaseUser userr = FirebaseAuth.getInstance().getCurrentUser();
                                            UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(fnametxt + " " + lnametxt).build();
                                            userr.updateProfile(profileChangeRequest);
                                            if (task.isSuccessful()) {
                                                userr.sendEmailVerification();
                                                startActivity(new Intent(registration.this, verifypage.class));
                                                finish();
                                            } else {
                                                Toast.makeText(registration.this, "Failed to register. Try again!", Toast.LENGTH_LONG).show();
                                                return;
                                            }
                                        }
                                    });
                        } else {
                            Toast.makeText(registration.this, "Email address is already taken!", Toast.LENGTH_LONG).show();
                            return;*/



    public void init() {
        context = registration.this;
        fName = (EditText) findViewById(R.id.fname);
        lName = (EditText) findViewById(R.id.lname);
        contactNo = (EditText) findViewById(R.id.contactno);
        emailAdd = (EditText) findViewById(R.id.emailadd);
        passWord = (EditText) findViewById(R.id.password);
        reType = (EditText) findViewById(R.id.retype);
        agreementBx = (CheckBox) findViewById(R.id.agreementbox);


        backButton = (ImageButton) findViewById(R.id.backbtn);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(registration.this, login.class));
            }
        });
        regbtn = (MaterialButton) findViewById(R.id.registerbtn);
        regbtn.setOnClickListener((view) -> {
            regbtn_onClick(view);
            if (agreementBx.isChecked()) {
                registerUser();
            }
        });


    }

    private void regbtn_onClick(View view) {
        validator.validate();

    }

    protected boolean validate() {
        if (validator != null)
            validator.validate();
        return validated;           // would be set in one of the callbacks below
    }


    @Override
    public void onValidationSucceeded() {
        Toast.makeText(this, "YES", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        validated = false;

        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            }
        }
    }
}
