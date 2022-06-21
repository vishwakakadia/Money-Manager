package com.example.incomeexpense.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.incomeexpense.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class resetPassActivity extends AppCompatActivity {

    Button reset;
    EditText emailText;
    private FirebaseAuth mAuth;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pass);

        mAuth = FirebaseAuth.getInstance();
        emailText = findViewById(R.id.email);
        reset = findViewById(R.id.reset_pass);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validatedata();
            }
        });
    }

    private void validatedata() {
        email = emailText.getText().toString().trim();
        if (email.isEmpty()) {
            emailText.setError("Required...");
        } else {
            forgetpass();
        }
    }

    private void forgetpass() {
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Check your email", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), LoginRegistrationActivity.class));
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Error : " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}