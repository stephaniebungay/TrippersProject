package com.example.trippersapp.Admin;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.example.trippersapp.MainPages.MainActivity;
import com.example.trippersapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.gowtham.library.utils.TrimVideo;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class AdminAttractions extends AppCompatActivity {

    private DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://trippersapp-cffca-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();
    private FirebaseDatabase firebaseDatabase;

    private final int REQUEST_PERMISSION_CODE = 35;
    private final int PICK_IMAGE_CODE = 39;
    private final int PICK_VIDEO = 40;
    private BottomNavigationView bottomNavigationView;
    private ViewPager viewPage;
    private VideoView videoView;
    private MaterialButton imageButton, videoButton, addAttractionbtn;
    private ArrayList<Uri> imagesUri;
    private Uri selectVideo;
    private EditText attractionid, attractionname, desc, availability;
    private Spinner packageid, country, region;
    private ProgressDialog progressDialog;
    private int count = 0;

    private ArrayList<String> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_attractions);

        packageid = findViewById(R.id.attrbox1);
        attractionid = findViewById(R.id.attrbox2);
        attractionname = findViewById(R.id.attrbox7);
        desc = findViewById(R.id.attrbox3);
        country = findViewById(R.id.attrbox4);
        region = findViewById(R.id.attrbox5);
        availability = findViewById(R.id.attrbox6);

        progressDialog = new ProgressDialog(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.countries_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        country.setAdapter(adapter);
        country.setFocusable(true);
        country.setFocusableInTouchMode(true);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.regions_array, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        region.setAdapter(adapter2);
        region.setFocusable(true);
        region.setFocusableInTouchMode(true);

        showDataSpinner();

        bottomNavigationView = findViewById(R.id.bottomnav);
        bottomNavigationView.setSelectedItemId(R.id.addAttractions);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.addAttractions:
                        return true;

                    case R.id.addDestination:
                        startActivity(new Intent(getApplicationContext(), AdminAddData.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.adminOut:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0, 0);
                }
                return false;
            }
        });

        viewPage = findViewById(R.id.viewPage2);
        imageButton = findViewById(R.id.imageBtn2);

        imagesUri = new ArrayList<>();

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkUserPermission(Manifest.permission.READ_EXTERNAL_STORAGE, REQUEST_PERMISSION_CODE);
            }
        });

        videoView = findViewById(R.id.videoView2);
        videoButton = findViewById(R.id.videoBtn2);

        videoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkP();
            }
        });

        addAttractionbtn = findViewById(R.id.addattraction);
        addAttractionbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listAttraction();
            }
        });

    }

    private void showDataSpinner() {
        databaseReference.child("Packages").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                for(DataSnapshot item : snapshot.getChildren()){
                    arrayList.add(item.getKey());
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(AdminAttractions.this, android.R.layout.simple_spinner_dropdown_item, arrayList);
                packageid.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void listAttraction() {
        String packageidtxt = packageid.getSelectedItem().toString().trim();
        String attractionidtxt = attractionid.getText().toString().trim();
        String attractionnametxt = attractionname.getText().toString().trim();
        String desctxt = desc.getText().toString().trim();
        String countrytxt = country.getSelectedItem().toString().trim();
        String regiontxt = region.getSelectedItem().toString().trim();
        String availtxt = availability.getText().toString().trim();

       /* if(packageidtxt.isEmpty()){
            Toast.makeText(AdminAttractions.this, "Package ID required!", Toast.LENGTH_LONG).show();
            packageid.requestFocus();
            return;
        }*/
        if(attractionidtxt.isEmpty()){
            attractionid.setError("Attraction ID required!");
            attractionid.requestFocus();
            return;
        }
        if(attractionnametxt.isEmpty()){
            attractionname.setError("Attraction name required!");
            attractionname.requestFocus();
            return;
        }
        if(desctxt.isEmpty()){
            desc.setError("Description required!");
            desc.requestFocus();
            return;
        }
        if(country.getSelectedItem().toString().trim().equals("Choose")){
            Toast.makeText(AdminAttractions.this, "Please select a country!", Toast.LENGTH_LONG).show();
            country.requestFocus();
            return;
        }
        if(availtxt.isEmpty()){
            availability.setError("Please indicate te availability!");
            availability.requestFocus();
            return;
        }
        else{
            databaseReference.child("Attractions").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.hasChild(attractionidtxt)){
                        Toast.makeText(AdminAttractions.this, "Attraction ID is already registered!", Toast.LENGTH_LONG).show();
                    }else{
                        databaseReference.child("Attractions").child(attractionidtxt).child("package_id").setValue(packageidtxt);
                        databaseReference.child("Attractions").child(attractionidtxt).child("attraction_id").setValue(attractionidtxt);
                        databaseReference.child("Attractions").child(attractionidtxt).child("attraction_name").setValue(attractionnametxt);
                        databaseReference.child("Attractions").child(attractionidtxt).child("attraction_description").setValue(desctxt);
                        databaseReference.child("Attractions").child(attractionidtxt).child("attraction_country").setValue(countrytxt);
                        databaseReference.child("Attractions").child(attractionidtxt).child("attraction_region").setValue(regiontxt);
                        databaseReference.child("Attractions").child(attractionidtxt).child("attraction_availability").setValue(availtxt);
                        uploadVideo();
                        compressImages();

                        Toast.makeText(AdminAttractions.this, "Saved successfully to database!", Toast.LENGTH_LONG).show();
                        startActivity(getIntent());
                        overridePendingTransition(0, 0);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(AdminAttractions.this, "DATABASE ERROR", Toast.LENGTH_LONG).show();

                }
            });
        }
    }

    private void checkP() {
        Dexter.withContext(this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_PICK);
                        intent.setType("video/*");
                        startActivityForResult(intent, PICK_VIDEO);
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
    }

    private void checkUserPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(AdminAttractions.this, permission)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(AdminAttractions.this, new String[]{permission},
                    requestCode);
        }
        else{
            pickImages();
        }
    }

    private void pickImages() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(intent, PICK_IMAGE_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_CODE){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                pickImages();
            } else{
                Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_CODE && resultCode == RESULT_OK){
            if(data != null){
                if (data.getClipData() != null){
                    int count = data.getClipData().getItemCount();

                    for (int i = 0; i < count; i++){
                        imagesUri.add(data.getClipData().getItemAt(i).getUri());
                    }
                }else{
                    imagesUri.add(data.getData());
                }
                setAdapter();
            }
        }
        if (requestCode == PICK_VIDEO && resultCode == RESULT_OK){
            if (data != null){
                selectVideo = data.getData();
                trimVideo(selectVideo);
            }
        }
        if (requestCode == TrimVideo.VIDEO_TRIMMER_REQ_CODE && data != null) {
            Uri uri = Uri.parse(TrimVideo.getTrimmedVideoPath(data));
            showVideo(uri);
        }
    }

    private void showVideo(Uri trimVideo) {
        videoView.setVideoURI(trimVideo);
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.start();
            }
        });
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.start();
            }
        });
    }

    private void trimVideo(Uri videoUri) {
        TrimVideo.activity(String.valueOf(videoUri))
                .setHideSeekBar(true)
                .start(this);
    }

    private void uploadVideo(){
    String attractionidtxt = attractionid.getText().toString().trim();
    String attractionnametxt = attractionname.getText().toString().trim();
    String timestamp = "" + System.currentTimeMillis();
    String filePathAndName = "Attractions/" + attractionnametxt + timestamp;

    StorageReference storageReference = FirebaseStorage.getInstance().getReference(filePathAndName);
    storageReference.putFile(selectVideo).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
        @Override
        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
            Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
            while(!uriTask.isSuccessful());
            Uri downloadUri = uriTask.getResult();
            if (uriTask.isSuccessful()){
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("Videolink", "" + downloadUri);

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Attractions").child(attractionidtxt);
                databaseReference.child("attraction_video")
                        .setValue(hashMap)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(AdminAttractions.this, ""+e.getMessage(), Toast.LENGTH_LONG).show();

                            }
                        });
            }
        }
    }).addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
            Toast.makeText(AdminAttractions.this, ""+e.getMessage(), Toast.LENGTH_LONG).show();

        }
    });
    }

    private void setAdapter() {
        PhotosAdapter photosAdapter = new PhotosAdapter(this, imagesUri);
        viewPage.setAdapter(photosAdapter);
    }

    private void compressImages(){
        progressDialog.setMessage("Uploading...");
        progressDialog.show();
        for(int i = 0; i < imagesUri.size(); i++){
            try{
                Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imagesUri.get(i));
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 60, stream);
                byte[] imageByte = stream.toByteArray();
                uploadImages(imageByte);
            } catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    private void uploadImages(byte[] imageByte) {
        StorageReference storageReference = FirebaseStorage.getInstance("gs://trippersapp-cffca.appspot.com").getReference()
                .child("Attractions")
                .child("images" + System.nanoTime() + ".jpg");
        storageReference.putBytes(imageByte).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        uploadImagesUri(String.valueOf(uri));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.cancel();
                        Toast.makeText(AdminAttractions.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.cancel();
                Toast.makeText(AdminAttractions.this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void uploadImagesUri(String uri) {
        String attractionidtxt = attractionid.getText().toString().trim();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Attractions").child(attractionidtxt).child("attractions_photos");
        String key = databaseReference.push().getKey();
        databaseReference.child(key).setValue(uri).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                count += 1;
                if (count == imagesUri.size()) {
                    progressDialog.dismiss();
                    Toast.makeText(AdminAttractions.this, "Uploaded", Toast.LENGTH_LONG).show();
                    finish();

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.cancel();
                Toast.makeText(AdminAttractions.this, e.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

    }
}