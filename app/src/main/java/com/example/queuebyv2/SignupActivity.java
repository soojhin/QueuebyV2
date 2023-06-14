package com.example.queuebyv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {
    TextView tvLogin;
    Button asSignupbtn;
    EditText etEmail, etPassword, etRepassword;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.[a-z]+";
    ProgressDialog progressDialog;
    FirebaseAuth mAuth;
    FirebaseUser mUser;

    private CurrentUser CurrentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        CurrentUser = CurrentUser.getInstance();

        tvLogin = findViewById(R.id.tvLogin);
        asSignupbtn = findViewById(R.id.asSignupbtn);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etRepassword = findViewById(R.id.etRepassword);
        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();


        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);

            }
        });

        asSignupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PerformAuth();
            }
        });
    }

    private void PerformAuth() {
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        String confirmpassword = etRepassword.getText().toString();

        if (!email.matches(emailPattern)) {
            etEmail.setError("email invalid");
        } else if (password.isEmpty() || password.length() < 6) {
            etPassword.setError("Password too short");
        } else if (!password.equals(confirmpassword)) {
            etRepassword.setError("Password not match");
        } else {
            progressDialog.setMessage("Registration in Progress...");
            progressDialog.setTitle("Registration");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        CurrentUser.setEmail(email);
                        sendverificationEmail();
                    }
                }

            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    if(e instanceof FirebaseAuthUserCollisionException){ //Email already registered
                        etEmail.setError("Email is already registered");
                    }
                    else{
                        Toast.makeText(SignupActivity.this, "Di ko alam ano ilalagay haha XD", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void sendverificationEmail() {
        if(mAuth.getCurrentUser()!=null){
            mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(SignupActivity.this, "Verification link has been sent to your email address", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SignupActivity.this, SignupActivityPage2.class);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(SignupActivity.this, "Error sending verification link", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

}