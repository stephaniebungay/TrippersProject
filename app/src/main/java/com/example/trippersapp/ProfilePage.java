package com.example.trippersapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.trippersapp.MainPages.BookingPage;
import com.example.trippersapp.MainPages.MainActivity;
import com.example.trippersapp.MainPages.UserPage;
import com.facebook.AccessTokenTracker;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class ProfilePage extends AppCompatActivity implements View.OnClickListener{

    private String TAG = ProfilePage.class.getSimpleName();
    private static int PICK_PHOTOS_CODE = 1;
    private static int REQUEST_PERMISSION_CODE = 1;
    private BottomNavigationView bottomNavigationView;
    private EditText name, contact;
    private ImageView backBtn, image;
    private MaterialButton changeBtn, resetBtn, saveBtn;
    private Uri imageUri;
    GoogleSignInClient googleSignInClient;
    private AccessTokenTracker accessTokenTracker;


    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);
        mAuth = FirebaseAuth.getInstance();

        image = findViewById(R.id.pfimage);
        name = findViewById(R.id.fullname);
        contact = findViewById(R.id.givencontact);
        backBtn = findViewById(R.id.pfbackbtn);
        changeBtn = findViewById(R.id.changebtn);
        resetBtn = findViewById(R.id.resetbtn);
        saveBtn = findViewById(R.id.savebtn);

        pageUI();


        bottomNavigationView = findViewById(R.id.bottomnav);
        bottomNavigationView.setSelectedItemId(R.id.user);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.user:
                        startActivity(new Intent(getApplicationContext(), UserPage.class));
                        overridePendingTransition(0, 0);
                        return true;

                   /* case R.id.notification:
                        startActivity(new Intent(getApplicationContext(), NotifPage.class));
                        overridePendingTransition(0, 0);
                        return true;*/

                    case R.id.booking:
                        startActivity(new Intent(getApplicationContext(), BookingPage.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.homepage:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
        backBtn.setOnClickListener(this);
        changeBtn.setOnClickListener(this);
        resetBtn.setOnClickListener(this);
        saveBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.pfbackbtn:
                startActivity(new Intent(this, UserPage.class));
                finish();
                break;

            case R.id.changebtn:
                if(Build.VERSION.SDK_INT >= 22) {
                    checkAndRequestForPermission();
                }
                else{
                    image.setImageURI(null);
                    selectPicture();
                }
                break;

            case R.id.resetbtn:
                pageUI();
                break;

            case R.id.savebtn:
                updateUser(imageUri, mAuth.getCurrentUser());

                break;

        }
    }


    private void checkAndRequestForPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Toast.makeText(this, "Please Accept for required permission", Toast.LENGTH_SHORT).show();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION_CODE);
            }
        } else
            selectPicture();
    }

    private void selectPicture() {
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, PICK_PHOTOS_CODE);
    }

    private void updateUser(Uri imageUri, FirebaseUser firebaseUser) {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String emailaddtxt = currentUser.getEmail();
        String nametxt = name.getText().toString();

        if(imageUri != null) {
            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("User_Photos");
            StorageReference filePath = storageReference.child(emailaddtxt + "_" + System.currentTimeMillis());
            filePath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    filePath.getDownloadUrl().addOnSuccessListener((new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder()
                                    .setPhotoUri(uri)
                                    .setDisplayName(nametxt)
                                    .build();
                            firebaseUser.updateProfile(profileChangeRequest);

                        }
                    }));
                }
            });

        }
        else{
            UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder()
                    .setDisplayName(nametxt)
                    .build();
            firebaseUser.updateProfile(profileChangeRequest);
        }
        Toast.makeText(this, "Profile Updated!", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(ProfilePage.this, UserPage.class));
    }

    private void pageUI(){
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String nametxt = currentUser.getDisplayName();
        String contacttxt = currentUser.getPhoneNumber();
        Uri pfp = currentUser.getPhotoUrl();

        if(contacttxt == null) {
            contact.setText("-");

        }
        name.setText(nametxt);
        contact.setText(contacttxt);
        Glide.with(this)
                .load(pfp)
                .thumbnail(0.05f)
                .transition(DrawableTransitionOptions.withCrossFade())
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .into(image);

       /* GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if(acct != null){
            String personGivenName = acct.getGivenName();
            String personFamilyName = acct.getFamilyName();
            Uri displayPhoto = acct.getPhotoUrl();

            fname.setText(personGivenName);
            lname.setText(personFamilyName);
            Glide.with(this)
                    .load(displayPhoto)
                    .thumbnail(0.05f)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .diskCacheStrategy(DiskCacheStrategy.DATA)
                    .into(image);*/
        }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_PHOTOS_CODE && data != null) {
            imageUri = data.getData();
            image.setImageURI(imageUri);
        }
    }
}