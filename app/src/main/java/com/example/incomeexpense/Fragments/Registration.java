package com.example.incomeexpense.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.incomeexpense.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class Registration extends Fragment {

    private EditText email;
    EditText password;
    TextView registration;
    TextView login;
    FirebaseAuth mAuth;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_registration, container, false);

        email = view.findViewById(R.id.username);
        password = view.findViewById(R.id.password);
        registration = view.findViewById(R.id.rbutton);
        login = view.findViewById(R.id.loginbutton);

        mAuth = FirebaseAuth.getInstance();

        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ans_email = email.getText().toString().trim();
                String ans_password = password.getText().toString().trim();


                if (TextUtils.isEmpty(ans_email)) {
                    email.setError("username is required");
                }
                if (TextUtils.isEmpty(ans_password)){
                    password.setError("Password is required");
                }
                if (ans_password.length() < 4) {
                    password.setError("Length of password is more than 4");
                    password.requestFocus();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(ans_email, ans_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getContext(), "You are successfully Registered", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "You are not Registered! Try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });


        return view;
    }

}