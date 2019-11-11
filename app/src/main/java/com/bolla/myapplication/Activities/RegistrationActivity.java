package com.bolla.myapplication.Activities;

import android.app.Activity;
import android.app.DirectAction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;

import com.bolla.myapplication.R;
import com.bolla.myapplication.Classes.SessionManager;
import com.bolla.myapplication.models.Profile;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.List;
import java.util.function.Consumer;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class RegistrationActivity extends AppCompatActivity {

    private static final int GALLERY_REQUEST_CODE = 100;
    private FirebaseAuth mAuth;
    SessionManager sessionManager;
    EditText etEmail;
    EditText etPassword;
    EditText etPhone;
    EditText etName;

    Profile profile;
    Uri photoUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);
        profile = new Profile();

        mAuth = FirebaseAuth.getInstance();
        sessionManager = new SessionManager(RegistrationActivity.this);

        TextView login = (TextView) findViewById(R.id.lnkLogin);
        login.setText(Html.fromHtml(getString(R.string.sigunUp)));

        Button btnSignUp = findViewById(R.id.btnSignUp);

        login.setMovementMethod(LinkMovementMethod.getInstance());
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etEmail = findViewById(R.id.txtEmail);
                etPassword = findViewById(R.id.txtPwd);
                //etPhone = findViewById(R.id.txtMobile);
                EditText etName = findViewById(R.id.txtName);
                String name = etName.getText().toString();

                validate();

                profile.setEmail(etEmail.getText().toString());
                profile.setPassword(etPassword.getText().toString());
               // profile.setPhone(etPhone.getText().toString());
                profile.setName(etName.getText().toString());

                if(profile.getName().isEmpty() || profile.getEmail().isEmpty() || profile.getPassword().isEmpty()){
                    Toast.makeText(RegistrationActivity.this, "Kindly check your data", Toast.LENGTH_SHORT).show();

                }else{
                    Register(profile);
                }

            }
        });

        ImageButton ib = findViewById(R.id.ib_photo);
        ib.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                pickFromGallery();
            }
        });
    }
    private void validate() {
        etEmail = findViewById(R.id.txtEmail);

        profile.setEmail(etEmail.getText().toString().trim());

        if (!profile.getEmail().matches("[a-zA-Z0-9._-]+@[a-z]+.[a-z]+") || profile.getEmail() == null) {

            etEmail.setError("Invalid Email Address");
        }

        etPassword = findViewById(R.id.txtPwd);


        profile.setPassword(etPassword.getText().toString());

        if (profile.getPassword() == null || profile.getPassword().length() <= 6) {

            etPassword.setError("Invalid password");
        }

        etName =

                findViewById(R.id.txtName);

        profile.setName(etName.getText().toString());

        if (profile.getName() == null || profile.getName().length() <= 3) {

            etName.setError("Invalid name");
        }

    }

    private void Register(final Profile profile) {
        mAuth.createUserWithEmailAndPassword(profile.getEmail(), profile.getPassword())
                .addOnCompleteListener(RegistrationActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");

                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(profile.getName()).setPhotoUri(photoUri)
                                    .build();

                            FirebaseUser user = mAuth.getCurrentUser();
                            user.updateProfile(profileUpdates)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Log.d(TAG, "User profile updated.");
                                            }
                                        }
                                    });

                            sessionManager.createLoginSessionWithName(profile.getEmail(), profile.getPassword(), profile.getName());
                            //updateUI(user);
                            Intent intent = new Intent(RegistrationActivity.this, HomeActivity.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegistrationActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });


    }

    private void pickFromGallery(){
        //Create an Intent with action as ACTION_PICK
        Intent intent=new Intent(Intent.ACTION_PICK);
        // Sets the type as image/*. This ensures only components of type image are selected
        intent.setType("image/*");
        //We pass an extra array with the accepted mime types. This will ensure only components with these MIME types as targeted.
        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);
        // Launching the Intent
        startActivityForResult(intent,GALLERY_REQUEST_CODE);
        Log.d("image", intent.toString());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result code is RESULT_OK only if the user selects an Image
        if (resultCode == Activity.RESULT_OK)
            switch (requestCode){
                case GALLERY_REQUEST_CODE:
                    //data.getData returns the content URI for the selected Image
                    Uri selectedImage = data.getData();
                    photoUri = selectedImage;
                    Log.d(TAG, photoUri.toString());



                    break;


            }

    }

}