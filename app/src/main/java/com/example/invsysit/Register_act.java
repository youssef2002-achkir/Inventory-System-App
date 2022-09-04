package com.example.invsysit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register_act extends AppCompatActivity {

    TextInputEditText RegEmail;
    TextInputEditText RegPassword;
    TextView LoginHere;
    Button btnRegister;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        RegEmail = findViewById(R.id.RegEmail);
        RegPassword = findViewById(R.id.RegPass);
        LoginHere = findViewById(R.id.LoginHere);
        btnRegister = findViewById(R.id.btnRegister);

        mAuth = FirebaseAuth.getInstance();

        btnRegister.setOnClickListener(view ->{
            createUser();
        });

        LoginHere.setOnClickListener(view ->{
            startActivity(new Intent(Register_act.this, MainActivity.class));
        });
    }

    private void createUser(){
        String email = RegEmail.getText().toString();
        String password = RegPassword.getText().toString();

        if (TextUtils.isEmpty(email)){
            RegEmail.setError("Email ne peut pas etre vide");
            RegEmail.requestFocus();
        }else if (TextUtils.isEmpty(password)){
            RegPassword.setError("Password ne peut pas etre vide");
            RegPassword.requestFocus();
        }else{
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(Register_act.this, "Registration avec succees", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Register_act.this, MainActivity.class));
                    }else{
                        Toast.makeText(Register_act.this, "Erreur de registration: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}