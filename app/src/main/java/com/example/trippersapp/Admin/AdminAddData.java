package com.example.trippersapp.Admin;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
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

public class AdminAddData extends AppCompatActivity {

    private final int REQUEST_PERMISSION_CODE = 35;
    private final int PICK_POSTER_CODE = 38;
    private final int PICK_PHOTOS_CODE = 39;
    private final int PICK_VIDEO = 40;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://trippersapp-cffca-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();
    private FirebaseDatabase firebaseDatabase;
    private BottomNavigationView bottomNavigationView;
    private ViewPager viewPagePoster, viewPagePhotos;
    private VideoView videoView;
    private MaterialButton posterButton, photosButton, videoButton, addPackagebtn;
    private ArrayList<Uri> imagesUri1, imagesUri2;
    private Uri selectVideo;
    private EditText packageid, packagename, desc, price, attractions,  availability;
    private Spinner country, region;
    private ProgressDialog progressDialog;
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_data);

        packageid = findViewById(R.id.text1box);
        packagename = findViewById(R.id.text2box);
        desc = findViewById(R.id.text3box);
        price = findViewById(R.id.text4box);
        attractions = findViewById(R.id.text5box);
        country = findViewById(R.id.text6box);
        region = findViewById(R.id.text7box);
        availability = findViewById(R.id.text8box);

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


        bottomNavigationView = findViewById(R.id.bottomnav);
        bottomNavigationView.setSelectedItemId(R.id.addDestination);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.addDestination:
                        return true;

                    case R.id.addAttractions:
                        startActivity(new Intent(getApplicationContext(), AdminAttractions.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.adminOut:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }
                return false;
            }
        });

        viewPagePoster = findViewById(R.id.viewPage2);
        viewPagePhotos = findViewById(R.id.viewPage3);
        posterButton = findViewById(R.id.imageBtn2);
        photosButton = findViewById(R.id.imageBtn3);

        imagesUri1 = new ArrayList<>();
        imagesUri2 = new ArrayList<>();

        posterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withContext(AdminAddData.this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {

                                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                    intent.setType("image/*");
                                    intent.setAction(Intent.ACTION_GET_CONTENT);
                                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                                    startActivityForResult(intent, PICK_POSTER_CODE);

                                    if(imagesUri2 != null){
                                        imagesUri2.clear();

                                }

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
        });

        photosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withContext(AdminAddData.this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                intent.setType("image/*");
                                intent.setAction(Intent.ACTION_GET_CONTENT);
                                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                                startActivityForResult(intent, PICK_PHOTOS_CODE);

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
        });

        videoView = findViewById(R.id.videoView2);
        videoButton = findViewById(R.id.videoBtn2);

        videoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkP();

            }
        });

        addPackagebtn = findViewById(R.id.addpackage);
        addPackagebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listPackage();
            }
        });
    }


    private void listPackage() {
        String packageidtxt = packageid.getText().toString().trim();
        String packagenametxt = packagename.getText().toString().trim();
        String description = desc.getText().toString().trim();
        String pricetxt = price.getText().toString().trim();
        String attractionstxt = attractions.getText().toString().trim();
        String countrytxt = country.getSelectedItem().toString().trim();
        String regiontxt = region.getSelectedItem().toString().trim();
        String availabletxt = availability.getText().toString().trim();

        if (packageidtxt.isEmpty()) {
            packageid.setError("Package ID required!");
            packageid.requestFocus();
            return;
        }

        if (packagenametxt.isEmpty()) {
            packagename.setError("Package Name required!");
            packagename.requestFocus();
            return;
        }

        if (description.isEmpty()) {
            desc.setError("Description required!");
            desc.requestFocus();
            return;
        }

        if (pricetxt.isEmpty()) {
            price.setError("Please indicate the price!");
            price.requestFocus();
            return;
        }

        if(country.getSelectedItem().toString().trim().equals("Choose")){
            Toast.makeText(AdminAddData.this, "Please select a country!", Toast.LENGTH_LONG).show();
            country.requestFocus();
            return;
        }

        if (availabletxt.isEmpty()) {
            availability.setError("Please indicate the availability of the package!");
            availability.requestFocus();
            return;

        } else {
            databaseReference.child("Packages").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.hasChild(packageidtxt)) {
                        Toast.makeText(AdminAddData.this, "Package ID is already registered in the database!", Toast.LENGTH_LONG).show();
                    } else {
                        databaseReference.child("Packages").child(packageidtxt).child("package_name").setValue(packagenametxt);
                        databaseReference.child("Packages").child(packageidtxt).child("package_description").setValue(description);
                        databaseReference.child("Packages").child(packageidtxt).child("package_price").setValue(pricetxt);
                        databaseReference.child("Packages").child(packageidtxt).child("package_attractions").setValue(attractionstxt);
                        databaseReference.child("Packages").child(packageidtxt).child("package_country").setValue(countrytxt);
                        databaseReference.child("Packages").child(packageidtxt).child("package_region").setValue(regiontxt);
                        databaseReference.child("Packages").child(packageidtxt).child("package_availability").setValue(availabletxt);
                        uploadVideo();
                        compressPoster();
                        compressPhotos();


                        Toast.makeText(AdminAddData.this, "Saved successfully to database!", Toast.LENGTH_LONG).show();
                        overridePendingTransition(0, 0);
                        startActivity(getIntent());
                        overridePendingTransition(0, 0);
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(AdminAddData.this, "DATABASE ERROR", Toast.LENGTH_LONG).show();


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


    /**private void checkUserPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(AdminAddData.this, permission)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(AdminAddData.this, new String[]{permission},
                    requestCode);
        } else {
            pickImages();
        }
    }

    private void pickImages() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(intent, PICK_PHOTOS_CODE);
    }*/
/**
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                pickImages();
            } else {
                Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_PHOTOS_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                if (data.getClipData() != null) {
                    int count = data.getClipData().getItemCount();

                    for (int i = 0; i < count; i++) {
                        imagesUri1.add(data.getClipData().getItemAt(i).getUri());
                    }
                } else {
                    imagesUri1.add(data.getData());
                }
                PhotosAdapter photosAdapter1 = new PhotosAdapter(this, imagesUri1);
                viewPagePhotos.setAdapter(photosAdapter1);
                photosAdapter1.notifyDataSetChanged();

                //setAdapter();
            }
        }

        if (requestCode == PICK_POSTER_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                if (data.getClipData() != null) {
                    int count = data.getClipData().getItemCount();

                    for (int i = 0; i < count; i++) {
                        imagesUri2.add(data.getClipData().getItemAt(i).getUri());
                    }
                } else {
                    imagesUri2.add(data.getData());
                }
                PosterAdapter photosAdapter = new PosterAdapter(this, imagesUri2);
                viewPagePoster.setAdapter(photosAdapter);
                photosAdapter.notifyDataSetChanged();

                //setAdapter();
            }
        }

        if (requestCode == PICK_VIDEO && resultCode == RESULT_OK) {
            if (data != null) {
                selectVideo = data.getData();
                trimVideo(selectVideo);
            }
        }
        if (requestCode == TrimVideo.VIDEO_TRIMMER_REQ_CODE && data != null) {
            Uri uri = Uri.parse(TrimVideo.getTrimmedVideoPath(data));
            showVideo(uri);

        }
    }

    private void trimVideo(Uri videoUri) {
        TrimVideo.activity(String.valueOf(videoUri))
                .setHideSeekBar(true)
                .start(this);

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

    private void uploadVideo(){
        String packageidtxt = packageid.getText().toString().trim();
        String timestamp = "_" + System.currentTimeMillis() + ".mp4";
        String filePathAndName = "Packages/" + packageidtxt + timestamp;

        StorageReference storageReference = FirebaseStorage.getInstance().getReference(filePathAndName);
        storageReference.putFile(selectVideo).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                //video uploaded get url of uploaded video
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while(!uriTask.isSuccessful());
                Uri downloadUri = uriTask.getResult();
                if(uriTask.isSuccessful()){
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("Videolink", "" + downloadUri);

                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Packages").child(packageidtxt);
                    databaseReference.child("package_video")
                            .setValue(hashMap)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    //Toast.makeText(AdminAddData.this, "Video Uploaded", Toast.LENGTH_LONG).show();

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(AdminAddData.this, ""+e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            });
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AdminAddData.this, ""+e.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }


    private void compressPoster() {
        progressDialog.setMessage("Uploading...");
        progressDialog.show();
        for (int i = 0; i < imagesUri2.size(); i++) {
            try {
                Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imagesUri2.get(i));
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 60, stream);
                byte[] imageByte = stream.toByteArray();
                uploadPoster(imageByte);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadPoster(byte[] imageByte) {
        String packageidtxt = packageid.getText().toString().trim();
        StorageReference storageReference = FirebaseStorage.getInstance("gs://trippersapp-cffca.appspot.com").getReference()
                .child("Packages")
                .child(packageidtxt + "_" + "POSTER.jpg");
        storageReference.putBytes(imageByte).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        uploadPosterUri(String.valueOf(uri));

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.cancel();
                        Toast.makeText(AdminAddData.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.cancel();
                Toast.makeText(AdminAddData.this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    private void uploadPosterUri(String uri) {
        String packageidtxt = packageid.getText().toString().trim();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Packages").child(packageidtxt);
        String key = databaseReference.push().getKey();
        databaseReference.child("package_poster").setValue(uri).addOnSuccessListener(new OnSuccessListener<Void>() {

            @Override
            public void onSuccess(Void unused) {
                    progressDialog.dismiss();
                    Toast.makeText(AdminAddData.this, "Uploaded", Toast.LENGTH_LONG).show();
                    finish();


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.cancel();
                Toast.makeText(AdminAddData.this, e.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }

    private void compressPhotos() {
        progressDialog.setMessage("Uploading...");
        progressDialog.show();
        for (int i = 0; i < imagesUri1.size(); i++) {
            try {
                Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imagesUri1.get(i));
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 60, stream);
                byte[] imageByte = stream.toByteArray();
                uploadPhotos(imageByte);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadPhotos(byte[] imageByte) {
        String packageidtxt = packageid.getText().toString().trim();
        StorageReference storageReference = FirebaseStorage.getInstance("gs://trippersapp-cffca.appspot.com").getReference()
                .child("Packages")
                .child(packageidtxt + "_" + System.nanoTime() + ".jpg");
        storageReference.putBytes(imageByte).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        uploadPhotosUri(String.valueOf(uri));

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.cancel();
                        Toast.makeText(AdminAddData.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.cancel();
                Toast.makeText(AdminAddData.this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    private void uploadPhotosUri(String uri) {
        String packageidtxt = packageid.getText().toString().trim();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Packages").child(packageidtxt).child("package_photos");
        String key = databaseReference.push().getKey();
        databaseReference.child(key).setValue(uri).addOnSuccessListener(new OnSuccessListener<Void>() {

            @Override
            public void onSuccess(Void unused) {
                count += 1;
                if (count == imagesUri1.size()) {
                    progressDialog.dismiss();
                    Toast.makeText(AdminAddData.this, "Uploaded", Toast.LENGTH_LONG).show();
                    finish();

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.cancel();
                Toast.makeText(AdminAddData.this, e.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }
}