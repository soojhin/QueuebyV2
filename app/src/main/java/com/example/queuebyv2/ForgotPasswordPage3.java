package com.example.queuebyv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ForgotPasswordPage3 extends AppCompatActivity {
    Button fp_ResetPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_page3);

        fp_ResetPass = findViewById(R.id.fp_ResetPass);

        fp_ResetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ForgotPasswordPage3.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}