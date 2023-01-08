package com.example.trippersapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.button.MaterialButton;


public class AccountSettings extends AppCompatActivity {
    private Toolbar topBar;
    private AppBarLayout appBarLayout;
    private ActionBar actionBar;
    private MaterialButton deleteBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);

        deleteBtn = findViewById(R.id.deleteacct);

        topBar = findViewById(R.id.topAppBar1);
        appBarLayout = findViewById(R.id.appBarLayout1);
        setSupportActionBar(topBar);
        getSupportActionBar().setTitle("Account Settings");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            startActivity(new Intent(AccountSettings.this, DeleteUser.class));
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}