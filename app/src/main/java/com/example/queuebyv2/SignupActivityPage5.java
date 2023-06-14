package com.example.queuebyv2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SignupActivityPage5 extends AppCompatActivity {
 Button as5loginbtn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_page5);

        as5loginbtn = findViewById(R.id.as5loginbtn);

        as5loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupActivityPage5.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}