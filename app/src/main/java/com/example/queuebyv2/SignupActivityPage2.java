package com.example.queuebyv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignupActivityPage2 extends AppCompatActivity {
   Button sp2Backbutton, sp2nextbtn;
   Toolbar toolbar;
    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_page2);

        sp2Backbutton = findViewById(R.id.sp2Backbtn);
        sp2nextbtn = findViewById(R.id.sp2nextbtn);
        toolbar = findViewById(R.id.toolbar);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        sp2nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupActivityPage2.this, SignupActivityPage3.class);
                startActivity(intent);
            }
        });
    }
}