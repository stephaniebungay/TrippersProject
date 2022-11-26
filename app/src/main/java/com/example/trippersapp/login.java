package com.example.trippersapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.trippersapp.MainPages.MainActivity;
import com.example.trippersapp.databinding.ActivityLoginBinding;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class login extends AppCompatActivity{

    DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://trippersapp-cffca-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();

    private EditText email, pass;
    private TextView forgotpasslink;
    private MaterialButton facebookView;
    private Button registerlink;
    private MaterialButton  loginButton;
    private CheckBox checkbx;
    private FirebaseAuth mAuth;
    private LoginButton facebookbtn;
    private ImageView userImage;

    private ActivityLoginBinding binding;
    private static final  int RC_SIGN_IN = 100;
    private GoogleSignInClient googleSignInClient;
    private FirebaseAuth firebaseAuth;
    private static final String TAG = "GOOGLE_SIGN_IN_TAG";

    private CallbackManager mCallbackManager;
    private static final String TAG2 = "FacebookAuthentication";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //configure the Google Signin
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .requestProfile()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);
        GoogleApiClient mGoogleApiClient;

        //init firebase auth
        firebaseAuth = FirebaseAuth.getInstance();
        checkUser();

        email = (EditText) findViewById(R.id.emailbox);
        pass = (EditText) findViewById(R.id.passbox);
        loginButton = (MaterialButton) findViewById(R.id.loginbtn);
        registerlink = (Button) findViewById(R.id.reglink);
        facebookView = (MaterialButton) findViewById(R.id.facebookview);

        mAuth = FirebaseAuth.getInstance();
        //mUser = mAuth.getCurrentUser();
        forgotpasslink = (TextView) findViewById(R.id.forgotpass);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();
            }
        });
        forgotpasslink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(login.this, ForgotPassword.class));
            }
        });
        registerlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(login.this, registration.class));
            }
        });

        //GOOGLE LOGIN BUTTON
        binding.gglbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                googleSignInClient.signOut(); 
                Log.d(TAG, "onClick: begin Google SignIn");
                Intent intent = googleSignInClient.getSignInIntent();
                startActivityForResult(intent, RC_SIGN_IN);
            }
        });
        //FACEBOOK LOGIN BUTTON
        facebookView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view.getId() == R.id.facebookview){
                    facebookbtn.performClick();
                }
            }
        });

        facebookbtn = (LoginButton) findViewById(R.id.fbbtn);
        facebookbtn.setReadPermissions("email", "public_profile");
        FacebookSdk.sdkInitialize(getApplicationContext());
        mCallbackManager = CallbackManager.Factory.create();
        facebookbtn.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG2, "onSuccess" + loginResult);
                handleFacebookToken(loginResult.getAccessToken());
            }
            @Override
            public void onError(@NonNull FacebookException e) {
                Log.d(TAG2, "onError" + e);

            }
            @Override
            public void onCancel() {
                Log.d(TAG2, "onCancel");
            }
        });
            }//end of oncreate

    private void userLogin() {
        String emailtxt = email.getText().toString().trim();
        String passwordtxt = pass.getText().toString().trim();

        if(emailtxt.isEmpty()){
            email.setError("Email is required!");
            email.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(emailtxt).matches()){
            email.setError("Please enter a valid email!");
            email.requestFocus();
            return;
        }
        if(passwordtxt.isEmpty()){
            pass.setError("Password is required!");
            pass.requestFocus();
            return;
        }
        if(passwordtxt.length() < 6){
            pass.setError("Minimum password length is 6 characters!");
            pass.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(emailtxt, passwordtxt).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    //redirect to user profile

                    if(user.isEmailVerified()) {
                        startActivity(new Intent(login.this, MainActivity.class));
                    } else{
                        user.sendEmailVerification();
                        Toast.makeText(login.this, "Please check your email to verify your account", Toast.LENGTH_LONG).show();
                    }
                } else{
                    Toast.makeText(login.this, "Try again! The email/password you provided does not match any of our records", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void handleFacebookToken(AccessToken token){
        Log.d(TAG2, "handleFacebookToken" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                 Log.d(TAG2, "sign in with credential: successful");
                 FirebaseUser user = firebaseAuth.getCurrentUser();
                 startActivity(new Intent(login.this, MainActivity.class));
                 updateUI(user);
                }else{
                    Log.d(TAG2, "sign in with credential: failure", task.getException());
                    Toast.makeText(login.this,"An account already exists with the same email address but different sign-in credentials", Toast.LENGTH_LONG).show();
                    //updateUI(null);
                    logoutFB();
                }
            }

            private void logoutFB() {
                {
                    if (AccessToken.getCurrentAccessToken() == null) {
                        return; // already logged out
                    }

                    new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/permissions/", null, HttpMethod.DELETE,
                            new GraphRequest.Callback() {
                                @Override
                                public void onCompleted(GraphResponse graphResponse)
                                {
                                    LoginManager.getInstance().logOut();
                                    Toast.makeText(login.this,"An account already exists with the same email address but different sign-in credentials", Toast.LENGTH_LONG).show();

                                }
                            })
                            .executeAsync();
                }
            }
        });
    }

    private void updateUI(FirebaseUser user){
        if(user != null){
            Log.d(TAG2,"Already logged in");
            startActivity(new Intent(this, MainActivity.class));
            finish();

        }
    }

    private void checkUser() {
        //if user is already signed in then go to profile activity
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null){
            Log.d(TAG, "checkUser: Already logged in");
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RC_SIGN_IN){
            Log.d(TAG, "onActivityResult: Google Signin intent result");
            Task<GoogleSignInAccount> accountTask = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                //google sign in success,
                GoogleSignInAccount account = accountTask.getResult(ApiException.class);
                firebaseAuthWithGoogleAccount(account);
            } catch (Exception e) {
                Log.d(TAG, "onActivityResult: "+e.getMessage());
            }
        }
    }

    private void firebaseAuthWithGoogleAccount(GoogleSignInAccount account) {
        Log.d(TAG, "firebaseAuthWithGoogleAccount: begin firebase auth with google account");
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        //login success
                        Log.d(TAG, "onSuccess: Logged In");

                        //get logged in user
                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                        //get user info
                        String uid = firebaseUser.getUid();
                        String email = firebaseUser.getEmail();

                        Log.d(TAG, "onSuccess: Email: "+email);
                        Log.d(TAG, "onSuccess: UID: "+uid);

                        //check if user is new or existing
                        if(authResult.getAdditionalUserInfo().isNewUser()){
                            //user is new account created
                            startActivity(new Intent(login.this, MainActivity.class));
                            finish();
                            Log.d(TAG, "onSuccess: Account Created...\n"+email);
                            Toast.makeText(login.this, "Account Created...\n"+email, Toast.LENGTH_LONG).show();
                        }
                        else{
                            startActivity(new Intent(login.this, MainActivity.class));
                            //existing user - logged in
                            Log.d(TAG, "onSuccess: Existing user...\n"+email);
                            Toast.makeText(login.this, "Existing user...\n"+email, Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //login failed
                        Log.d(TAG, "onFailure: Login Failed "+e.getMessage());
                    }
                });
    }
}

