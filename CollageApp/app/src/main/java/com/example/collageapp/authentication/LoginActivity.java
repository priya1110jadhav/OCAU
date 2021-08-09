package com.example.collageapp.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.collageapp.MainActivity;
import com.example.collageapp.databinding.ActivitySigninBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

public class LoginActivity extends AppCompatActivity {

    private TextView openReg, openForgotPassword;
    private EditText logEmail, logPassword;
    private Button loginBtn;
    private String email, password;
    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private String mToken;

    ActivitySigninBinding binding;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySigninBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        openReg = binding.openReg;
        logEmail = binding.logEmail;
        logPassword = binding.logPass;
        loginBtn = binding.login;
        openForgotPassword = binding.openForgotPass;

        auth = FirebaseAuth.getInstance();


        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("TAG", "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        mToken = task.getResult();

                        // Log and toast

                    }
                });

        database = FirebaseDatabase.getInstance();


        openReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRegister();
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateData();
            }
        });

        openForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openForgotPasswordActivity();
            }
        });



    }

    private void openForgotPasswordActivity() {
        startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
    }

    private void validateData() {
        email = logEmail.getText().toString();
        password = logPassword.getText().toString();

        if (email.isEmpty()){
            logEmail.setError("Required");
            logEmail.requestFocus();
            return;
        }
        if (password.isEmpty()){
            logPassword.setError("Required");
            logPassword.requestFocus();
            return;
        }
        else{
            loginUser();
        }

    }

    private void loginUser() {
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){

                    database.getReference().child("users").child(auth.getUid()).child("token").setValue(mToken);

                    openMain();                }
                else{

                    Toast.makeText(LoginActivity.this, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LoginActivity.this, "Error" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openMain() {

        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }

    private void openRegister() {

        startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
        finish();
    }
}
