package com.example.collageapp.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.collageapp.R;
import com.example.collageapp.databinding.ActivityForgotPasswordBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    ActivityForgotPasswordBinding binding;
    EditText textEmail;
    String email;
    Button forgotBtn;
    TextView openLog;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        textEmail = binding.forEmail;
        forgotBtn = binding.forgetBtn;
        openLog = binding.openLog;
        auth = FirebaseAuth.getInstance();


        openLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLogin();
            }
        });

        forgotBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateData();
            }
        });


    }

    private void openLogin() {
        startActivity(new Intent(ForgotPasswordActivity.this, LoginActivity.class));
        finish();


    }

    private void validateData() {
        email = textEmail.getText().toString();
        if (email.isEmpty()){
            textEmail.setError("Required");
            textEmail.requestFocus();
            return;
        }
        else{
            forgotPassword();
        }
    }

    private void forgotPassword() {
        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(ForgotPasswordActivity.this, "Check your Email", Toast.LENGTH_SHORT).show();

                            openLogin();
                        }
                        else{
                            Toast.makeText(ForgotPasswordActivity.this, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ForgotPasswordActivity.this, "Error" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

