package com.bolla.myapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;

import com.bolla.myapplication.R;
import com.bolla.myapplication.Classes.SessionManager;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    String profileNameTxt;
    TextView tvProfileName;

    String profileEmailTxt;
    TextView tvProfileEmail;

    String profilePhoneTxt;
    TextView tvProfilePhone;

    CircleImageView image;

    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address etc
            String name = user.getDisplayName();
            String email = user.getEmail();
            String phone = user.getPhoneNumber();
            Uri imageUri = user.getPhotoUrl();

            sessionManager = new SessionManager(ProfileActivity.this);
            profileNameTxt = "Name: " + name;
            tvProfileName = findViewById(R.id.tv_name);
            tvProfileName.setText(profileNameTxt);

            profileEmailTxt = "Email: " + email;
            tvProfileEmail = findViewById(R.id.tv_email);
            tvProfileEmail.setText(profileEmailTxt);

            // profilePhoneTxt = "Phone number: " + phone;
            //tvProfilePhone = findViewById(R.id.tv_mobile);
            //tvProfilePhone.setText(profilePhoneTxt);


            image = findViewById(R.id.iv_profiles);
            //image.setImageURI(imageUri);
            Glide.with(ProfileActivity.this).
                    load(imageUri).into(image);


        }
    }

    //  @Override
   /* public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {

        sessionManager = new SessionManager(ProfileActivity.this);
        profileName= sessionManager.getUserDetails().get(sessionManager.KEY_NAME);
        profileTxt="This is "+profileName+" profile";
        profile = parent.findViewById(R.id.tv_profile);
        profile.setText(profileTxt);
        return parent;

    }*/

}
