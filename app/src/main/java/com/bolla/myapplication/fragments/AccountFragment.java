package com.bolla.myapplication.fragments;


import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bolla.myapplication.Activities.ProfileActivity;
import com.bolla.myapplication.Classes.SessionManager;
import com.bolla.myapplication.R;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import de.hdodenhof.circleimageview.CircleImageView;

import static androidx.constraintlayout.widget.Constraints.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment {
    String profileNameTxt;
    TextView tvProfileName;

    String profileEmailTxt;
    TextView tvProfileEmail;

    EditText etName;
    String name;

    Button button;
    ImageView imageView;

    SessionManager sessionManager;

    public AccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.fragment_account2, container, false);

        etName = view.findViewById(R.id.et_account_name);
        button = view.findViewById(R.id.btn_account_submit);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address etc
            String name = user.getDisplayName();
            String email = user.getEmail();
            String phone = user.getPhoneNumber();
            Uri imageUri = user.getPhotoUrl();
            // Inflate the layout for this fragment
            sessionManager = new SessionManager(getContext());
            profileNameTxt = "Name: " + name;
            tvProfileName = view.findViewById(R.id.tv_account_name);
            tvProfileName.setText(profileNameTxt);

            profileEmailTxt = "Email: " + email;
            tvProfileEmail = view.findViewById(R.id.tv_account_email);
            tvProfileEmail.setText(profileEmailTxt);

            //imageView = view.findViewById(R.id.iv_profiles);
            //image.setImageURI(imageUri);
            /*Glide.with(AccountFragment.this).
                    load(imageUri).into(imageView);*/
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = etName.getText().toString();

                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                        .setDisplayName(name.toString())
                        .build();

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                user.updateProfile(profileUpdates)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "User profile updated.");
                                    profileNameTxt = "Name: " + name;
                                    tvProfileName.setText(profileNameTxt);

                                }
                            }
                        });
            }
        });

        return view;

    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

   /* public void submitButtonClicked(View view) {
        etName=findViewById(R.id.et_account_name);
        name=etName.getText();

        etEmail=findViewById(R.id.et_account_email);
        email=etEmail.getText();

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(name.toString())
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
                });}*/
}
