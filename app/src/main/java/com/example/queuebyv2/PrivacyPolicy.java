package com.example.queuebyv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class PrivacyPolicy extends AppCompatActivity {

    TextView fpp_tvBackbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);

        fpp_tvBackbtn = findViewById(R.id.fpp_tvBackbtn);

        fpp_tvBackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirect to SignupActivityPage4
                finish();
            }
        });

    }
}