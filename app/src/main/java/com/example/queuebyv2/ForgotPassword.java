package com.example.queuebyv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {
    Button fp_resetpassword;
    EditText fp_email;
    ProgressDialog progressDialog;
    FirebaseAuth mAuth;
    String strEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        fp_resetpassword = findViewById(R.id.fp_resetpassword);
        fp_email = findViewById(R.id.fp_email);
        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();

        fp_resetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                strEmail = fp_email.getText().toString().trim();
                if (!TextUtils.isEmpty(strEmail)) {
                    ResetPassword();
                } else {
                    fp_email.setError("Field can't be empty");
                }
            }
        });
    }

    private void ResetPassword() {
        progressDialog.setMessage("Sending reset password email...");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);

        mAuth.sendPasswordResetEmail(strEmail).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                progressDialog.dismiss();
                Toast.makeText(ForgotPassword.this, "Reset password link has been sent to your email", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ForgotPassword.this, ForgotPasswordPage2.class);
                startActivity(intent);
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(ForgotPassword.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}
