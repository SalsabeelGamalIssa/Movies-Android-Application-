package com.bolla.myapplication.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bolla.myapplication.R;
import com.bolla.myapplication.Classes.SessionManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    Button btnLogin;
    SessionManager sessionManager;
    EditText etEmail;
    EditText etPassword;
    String email;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        sessionManager = new SessionManager(LoginActivity.this);

        TextView register = (TextView) findViewById(R.id.lnkRegister);
        register.setText(Html.fromHtml(getString(R.string.login)));

        btnLogin = findViewById(R.id.btnLogin);
        register.setMovementMethod(LinkMovementMethod.getInstance());
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });



        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                etEmail = findViewById(R.id.txtEmail);
                etPassword = findViewById(R.id.txtPwd);
                email = etEmail.getText().toString();
                password = etPassword.getText().toString();
                validate();

                if (password.isEmpty() || email.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Empty email or Password", Toast.LENGTH_SHORT).show();

                }else{
                    Login(email, password);
                }

            }
        });

    }

    private void validate() {
        etEmail = findViewById(R.id.txtEmail);


        email = etEmail.getText().toString().trim();

        if (!email.matches("[a-zA-Z0-9._-]+@[a-z]+.[a-z]+") || email == null) {

            etEmail.setError("Invalid Email Address");
        }


        etPassword = findViewById(R.id.txtPwd);


        if (password == null || password.length() <= 6) {

            etPassword.setError("Invalid password");
        }

    }


    public void OnChecked(View view) {
        CheckBox checkBox = view.findViewById(R.id.chkBox);

        if (checkBox.isChecked())
            btnLogin.setEnabled(true);
        else
            btnLogin.setEnabled(false);

    }


    private void Login(final String email, final String password) {


        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            sessionManager.createLoginSessionWithName(email, password, user.getDisplayName());
                            // to be Implemented
                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });
    }

}
