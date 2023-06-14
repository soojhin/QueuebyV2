package com.example.queuebyv2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class RequestSuccess extends AppCompatActivity {

    Button finish;
    TextView seedetails;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_success);

        finish = findViewById(R.id.btnFinish);
        seedetails = findViewById(R.id.btnreqseedetails);
        intent = new Intent(this, HomepageActivity.class);


        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });

        seedetails.setPaintFlags(seedetails.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        seedetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an instance of your Fragment
                RequestFragment fragment = new RequestFragment ();

                // Get the FragmentManager and start a FragmentTransaction
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                // Replace the existing content with the new Fragment
                //fragmentTransaction.replace(R.id.RequestSuccess, fragment);

                // Add the transaction to the back stack (optional)
                fragmentTransaction.addToBackStack(null);

                // Commit the FragmentTransaction
                fragmentTransaction.commit();
            }
        });


    }

}