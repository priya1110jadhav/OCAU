package com.example.collageapp.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.collageapp.MainActivity;
import com.example.collageapp.R;
import com.example.collageapp.databinding.ActivityRegisterBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    EditText regName, regPass, regEmail;
    Button register;
    private String name, email, password;
    private FirebaseAuth auth;
    private DatabaseReference reference;
    private DatabaseReference dbref;
    private TextView openLog;

    String mToken;

    ActivityRegisterBinding binding;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference();

        regEmail = binding.regEmail;
        regName = binding.regName;
        regPass = binding.regPass;
        register = binding.register;
        openLog = binding.openLogin;


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


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateData();
            }
        });

        openLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLogin();
            }
        });

    }

    private void openLogin() {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (auth.getCurrentUser() != null){
            openMain();
        }
    }

    private void openMain(){
        startActivity(new Intent(this, MainActivity.class));
        finishAffinity();

    }

        private void validateData(){

        name = regName.getText().toString();
        email = regEmail.getText().toString();
        password = regPass.getText().toString();

        if (name.isEmpty()){
            regName.setError("Required");
            regName.requestFocus();
        }
        if (password.isEmpty()){
            regPass.setError("Required");
            regPass.requestFocus();
        }
        if (email.isEmpty()){
            regEmail.setError("Required");
            regEmail.requestFocus();
        }
        else{
            createUser();
        }


    }

    private void createUser(){
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){

                    FirebaseMessaging.getInstance().subscribeToTopic("Notice");

                    uploadData();

                }
                else{
                    Toast.makeText(RegisterActivity.this, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegisterActivity.this, "Error" + e.getMessage() , Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void uploadData() {

        dbref = reference.child("users");

        HashMap<String, String> user = new HashMap<>();
        user.put("token", mToken);
        user.put("name", name );
        user.put("email", email);

        dbref.child(auth.getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()){
                    Toast.makeText(RegisterActivity.this, "User Created", Toast.LENGTH_SHORT).show();
                    openMain();

                }else {

                    Toast.makeText(RegisterActivity.this, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegisterActivity.this, "Error" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}