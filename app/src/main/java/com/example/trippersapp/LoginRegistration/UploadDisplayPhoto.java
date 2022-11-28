package com.example.trippersapp.LoginRegistration;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.trippersapp.MainPages.MainActivity;
import com.example.trippersapp.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class UploadDisplayPhoto extends AppCompatActivity implements View.OnClickListener {

    private ImageView uploadView;
    private MaterialButton uploadBtn, skipUpload, saveUpload;

    private static int PICK_PHOTOS_CODE = 1; //REQUESCODE
    private static int REQUEST_PERMISSION_CODE = 1; //PReqCode
    private Uri pfpUri;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_display_photo);

        mAuth = FirebaseAuth.getInstance();
        StorageReference storageReference = FirebaseStorage.getInstance("gs://trippersapp-cffca.appspot.com").getReference();

        uploadView = findViewById(R.id.uploadimageview);
        uploadBtn = findViewById(R.id.uploadbtn);
        skipUpload = findViewById(R.id.skipupload);
        saveUpload = findViewById(R.id.saveupload);

        uploadBtn.setOnClickListener(this);
        skipUpload.setOnClickListener(this);
        saveUpload.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.uploadbtn:
                checkAndRequestForPermission();
                break;
            case R.id.skipupload:
                noProfilePicture(mAuth.getCurrentUser());
                startActivity(new Intent(UploadDisplayPhoto.this, MainActivity.class));
                break;
            case R.id.saveupload:
                saveToStorage(pfpUri, mAuth.getCurrentUser());
                break;


        }
    }

    private void noProfilePicture(FirebaseUser firebaseUser) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String emailaddtxt = currentUser.getEmail();

        Uri path = Uri.parse("android.resource://com.example.trippers" + R.drawable.profilepicture);
        String imgPath = path.toString();
        uploadView.setImageURI(path);

        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("User_Photos");
        StorageReference filePath = storageReference.child(emailaddtxt + "_" + System.currentTimeMillis());
        filePath.putFile(path).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder()
                                .setPhotoUri(uri)
                                .build();
                        firebaseUser.updateProfile(profileChangeRequest);
                        startActivity(new Intent(UploadDisplayPhoto.this, MainActivity.class));
                    }
                });
            }
        });

    }

    private void saveToStorage(Uri pfpUri, FirebaseUser firebaseUser) {
       FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String emailaddtxt = currentUser.getEmail();
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
                        startActivity(new Intent(UploadDisplayPhoto.this, MainActivity.class));

                    }
                }));
            }
        });

    }


    private void checkAndRequestForPermission() {
        if (ContextCompat.checkSelfPermission(UploadDisplayPhoto.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(UploadDisplayPhoto.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Toast.makeText(UploadDisplayPhoto.this, "Please Accept for required permission", Toast.LENGTH_SHORT).show();
            } else {
                ActivityCompat.requestPermissions(UploadDisplayPhoto.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION_CODE);
            }
        } else
            selectPicture();
    }

    private void selectPicture() {
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, PICK_PHOTOS_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_PHOTOS_CODE && data != null) {
            pfpUri = data.getData();
            uploadView.setImageURI(pfpUri);
        }

    }
}