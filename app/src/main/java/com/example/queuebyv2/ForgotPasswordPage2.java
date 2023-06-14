package com.example.queuebyv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ForgotPasswordPage2 extends AppCompatActivity {
    Button fp2_nextbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_page2);

        fp2_nextbtn = findViewById(R.id.fp2_nextbtn);

        fp2_nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ForgotPasswordPage2.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}