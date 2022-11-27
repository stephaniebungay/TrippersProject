package com.example.trippersapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
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

    static int REQUESCODE = 1;
    static int PReqCode = 1;
    protected boolean validated;
    protected Validator validator;
    FirebaseFirestore firebaseFirestore;
    private StorageReference storageReference;
    private String TAG = registration.class.getSimpleName();
    private Context context;

    private ImageView profilePic;
    private Uri pfpUri;
    private Bitmap compressor;


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
        StorageReference storageReference = FirebaseStorage.getInstance("gs://trippersapp-cffca.appspot.com").getReference();

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

        user.setContactno(contacttxt);
        user.setEmailadd(emailaddtxt);
        user.setFname(fnametxt);
        user.setLname(lnametxt);
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

    /*private void uploadPfpStorage(byte[] imageByte){
        FirebaseUser userAuth = FirebaseAuth.getInstance().getCurrentUser();
        String emailaddtxt = emailAdd.getText().toString().trim();
        StorageReference storageReference = FirebaseStorage.getInstance("gs://trippersapp-cffca.appspot.com")
                .getReference()
                .child("Profile_Pictures")
                .child(emailaddtxt + "_" +System.currentTimeMillis()+".jpg");
        storageReference.putBytes(imageByte).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                                .setPhotoUri(uri)
                                .build();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(registration.this, e.getMessage(), Toast.LENGTH_LONG).show();

                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(registration.this, e.getMessage(), Toast.LENGTH_LONG).show();

            }
        });


    }*/


    private void checkAndRequestForPermission() {
        if (ContextCompat.checkSelfPermission(registration.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(registration.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Toast.makeText(registration.this, "Please Accept for required permission", Toast.LENGTH_SHORT).show();
            } else {
                ActivityCompat.requestPermissions(registration.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PReqCode);
            }
        } else
            selectPicture();
    }

    private void selectPicture() {
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, REQUESCODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUESCODE && data != null) {
            pfpUri = data.getData();
            profilePic.setImageURI(pfpUri);
        }
    }
}
