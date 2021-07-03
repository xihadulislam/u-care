package com.project.ucare.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.auth.User;
import com.project.ucare.R;
import com.project.ucare.splash.SplashActivity;
import com.xihad.androidutils.AndroidUtils;

import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {


    private static final String TAG = "SignUpActivity";
    EditText name, email, password;
    Button signUpButton;

    ProgressBar progressBar;


    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().hide();

        AndroidUtils.Companion.init(SignUpActivity.this);
        AndroidUtils.Companion.setStatusBarColor(R.color.white);

        name = findViewById(R.id.et_su_name);
        email = findViewById(R.id.et_su_email);
        password = findViewById(R.id.et_su_password);
        signUpButton = findViewById(R.id.bt_su_signUp);
        progressBar = findViewById(R.id.Pb_SignUp);


        firebaseAuth = FirebaseAuth.getInstance();


        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String nam = name.getText().toString().trim();
                String mail = email.getText().toString().trim();
                String pass = password.getText().toString().trim();

                if (nam.isEmpty()) {
                    name.setError("This field can not be blank");
                    return;

                }
                if (!Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
                    email.setError("Please Provide valid email");
                    return;

                }

                if (mail.isEmpty()) {
                    email.setError("This field can not be blank");
                    return;

                }
                if (pass.isEmpty()) {
                    password.setError("This field can not be blank");
                    return;

                }
                if (pass.length() < 6) {
                    password.setError("must have more than 6 character");
                    return;

                }


                progressBar.setVisibility(View.VISIBLE);
                signUpButton.setVisibility(View.INVISIBLE);


                Log.d("email", "mail: " + mail);
                Log.d("pass", "password: " + pass);


                firebaseAuth.createUserWithEmailAndPassword(mail, pass).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {


                        if (task.isSuccessful()) {
                            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            Log.d("useId", "onComplete: " + userId);

                            progressBar.setVisibility(View.GONE);
                            signUpButton.setVisibility(View.VISIBLE);


                            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                            startActivity(intent);
                        } else {
                            progressBar.setVisibility(View.GONE);
                            signUpButton.setVisibility(View.VISIBLE);
                            Toast.makeText(SignUpActivity.this, task.toString(), Toast.LENGTH_SHORT).show();
                            Toast.makeText(SignUpActivity.this, "Failed To register ,try again", Toast.LENGTH_SHORT).show();
                        }

                    }
                });


            }
        });


    }
}