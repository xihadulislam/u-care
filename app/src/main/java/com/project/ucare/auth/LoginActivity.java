package com.project.ucare.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.project.ucare.R;
import com.project.ucare.main.MainActivity;

public class LoginActivity extends AppCompatActivity {
    
    TextView resisterText;
    EditText email,password;
    Button loginButton,googleButton;
    ProgressBar Pb_login;

    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
        resisterText=findViewById(R.id.tv_register);
        email=findViewById(R.id.et_email);
        password=findViewById(R.id.et_password);
        
        loginButton=findViewById(R.id.bt_login);
        googleButton=findViewById(R.id.bt_google_login);

        Pb_login=findViewById(R.id.Pb_login);

        firebaseAuth=FirebaseAuth.getInstance();

        
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String mail=email.getText().toString().trim();
                String pass=password.getText().toString().trim();

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

                Pb_login.setVisibility(View.VISIBLE);
                loginButton.setVisibility(View.INVISIBLE);

                firebaseAuth.signInWithEmailAndPassword(mail,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){

                            Pb_login.setVisibility(View.VISIBLE);
                            loginButton.setVisibility(View.INVISIBLE);
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                        else {
                            Pb_login.setVisibility(View.VISIBLE);
                            loginButton.setVisibility(View.INVISIBLE);
                            Toast.makeText(LoginActivity.this, task.toString(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });










            }
        });
        
        
        
        
        
    }
}