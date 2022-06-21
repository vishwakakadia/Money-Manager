package com.example.incomeexpense.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.incomeexpense.Activities.HomeActivity;
import com.example.incomeexpense.Activities.resetPassActivity;
import com.example.incomeexpense.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends Fragment {

    TextView email;
    TextView password;
    TextView login;
    TextView forgotPass;
    FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login, container, false);

        email = view.findViewById(R.id.login_username);
        password = view.findViewById(R.id.login_password);
        login = view.findViewById(R.id.login_button);
        forgotPass = view.findViewById(R.id.forgot);

        mAuth = FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
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
                mAuth.signInWithEmailAndPassword(ans_email, ans_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(getContext(), "Login successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getContext(), HomeActivity.class);
                            startActivity(intent);

                        }else {
                            Toast.makeText(getContext(), task.getException().toString(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });

        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), resetPassActivity.class);
                startActivity(intent);
            }
        });

        return view;

    }
}