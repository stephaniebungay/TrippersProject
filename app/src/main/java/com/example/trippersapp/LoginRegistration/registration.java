package com.example.trippersapp.LoginRegistration;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.trippersapp.Extra.Logger;
import com.example.trippersapp.Models.User;
import com.example.trippersapp.R;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mobsandgeeks.saripaar.Validator;

public class registration extends AppCompatActivity {

    private String TAG = registration.class.getSimpleName();
    private static int PICK_PHOTOS_CODE = 1;
    private static int REQUEST_PERMISSION_CODE = 1;
    protected Validator validator;
    FirebaseFirestore firebaseFirestore;
    private Context context;
    private ImageView profilePic;
    private Uri pfpUri;
    private EditText fName, lName, contactNo, emailAdd, passWord, reType;
    private CheckBox agreementBx;
    private TextView agreement;
    private ImageButton backButton;
    private MaterialButton regbtn;
    private FirebaseAuth mAuth;
    private Dialog dialog;


    public registration() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Logger.LogView(TAG, "onCreate", "");

        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        StorageReference storageReference = FirebaseStorage.getInstance("gs://trippersapp-cffca.appspot.com").getReference();

        init();
        pfpUri = Uri.parse(String.valueOf(R.drawable.profilepicture));

        regbtn = (MaterialButton) findViewById(R.id.registerbtn);
        regbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    registerUser();
            }
        });
    }

    private void registerUser() {
        User user = new User();

        String fnametxt = fName.getText().toString().trim();
        String lnametxt = lName.getText().toString().trim();
        String contacttxt = contactNo.getText().toString().trim();
        String emailaddtxt = emailAdd.getText().toString().trim();
        String passtxt = passWord.getText().toString().trim();
        String retypetxt = reType.getText().toString().trim();

        user.setContactno(contacttxt);
        user.setEmailadd(emailaddtxt);
        user.setFname(fnametxt);
        user.setLname(lnametxt);
        user.setPassword(retypetxt);

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
                                        user.setPassword(retypetxt);
                                        firebaseFirestore.collection("User").document(emailaddtxt).set(user);
                                        pfpStorage(pfpUri, mAuth.getCurrentUser());

                                        startActivity(new Intent(registration.this, verifypage.class));
                                        finish();
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(registration.this, "Email address is already taken!", Toast.LENGTH_LONG).show();
                            return;
                        }
                    }
                });
    }

    public void init() {
        context = registration.this;

        profilePic = (ImageView) findViewById(R.id.pfp);
        fName = (EditText) findViewById(R.id.fname);
        lName = (EditText) findViewById(R.id.lname);
        contactNo = (EditText) findViewById(R.id.contactno);
        emailAdd = (EditText) findViewById(R.id.emailadd);
        passWord = (EditText) findViewById(R.id.password);
        reType = (EditText) findViewById(R.id.retype);
        agreementBx = (CheckBox) findViewById(R.id.agreementbox);
        agreement = findViewById(R.id.agreementxt);
        dialog = new Dialog(registration.this);


        agreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopUp(view);
            }
        });

        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Build.VERSION.SDK_INT >= 22) {
                    checkAndRequestForPermission();
                }
                else{
                    selectPicture();
                }
            }
        });


        backButton = (ImageButton) findViewById(R.id.backbtn);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(registration.this, login.class));
            }
        });



    }






    private void checkAndRequestForPermission() {
        if (ContextCompat.checkSelfPermission(registration.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(registration.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Toast.makeText(registration.this, "Please Accept for required permission", Toast.LENGTH_SHORT).show();
            } else {
                ActivityCompat.requestPermissions(registration.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION_CODE);
            }
        } else
            selectPicture();
    }

    private void selectPicture() {
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, PICK_PHOTOS_CODE);
    }

    private void pfpStorage(Uri pfpUri, FirebaseUser firebaseUser){
        User user = new User();
        String emailaddtxt = emailAdd.getText().toString().trim();

        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("User_Photos");
        StorageReference filePath = storageReference.child(emailaddtxt + "_" + System.currentTimeMillis());
        filePath.putFile(pfpUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                filePath.getDownloadUrl().addOnSuccessListener((new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder()
                                .setPhotoUri(uri)
                                .build();
                        firebaseUser.updateProfile(profileChangeRequest);
                    }
                }));
            }
        });

    }

    public void showPopUp(View view){
        ImageView exit;

        dialog.setContentView(R.layout.terms_condition);
        exit = (ImageView) dialog.findViewById(R.id.exitTerms);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_PHOTOS_CODE && data != null) {
            pfpUri = data.getData();
            if (pfpUri != null) {
                profilePic.setImageURI(pfpUri);
            }

        }
    }
}
