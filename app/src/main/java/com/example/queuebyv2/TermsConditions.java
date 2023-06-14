package com.example.queuebyv2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class TermsConditions extends AppCompatActivity {
    TextView terms_tvBackbtn;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_conditions);

        terms_tvBackbtn = findViewById(R.id.terms_tvBackbtn);

        terms_tvBackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TermsConditions.this, SignupActivityPage4.class);
                startActivity(intent);
            }
        });
    }
}