package com.example.queuebyv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Date;

public class Request extends AppCompatActivity {


    EditText etFN, etMN, etLN, etHouseno, etBarangay, etCity, etPurpose,etDate;
    String tvStatus;
    RadioGroup radioGroup;
    Button btnSubmit, reqBackbtn;
    DatabaseReference certdb;
    Intent intent;
    certs certs;
    long maxid = 0;
    private SharedPreferences sharedPreferences;
    RadioButton rbBarangayClearance, rbBusinessPermit, rbCedula;
    Toolbar toolbar;
    private CurrentUser CurrentUser;
    String currentDateTimeString = java.text.DateFormat.getDateInstance().format(new Date());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
        CurrentUser = CurrentUser.getInstance();
        sharedPreferences = getSharedPreferences(LoginActivity.PREFS_NAME, 0);
        etFN = findViewById(R.id.etFN);
        etMN = findViewById(R.id.etMN);
        etLN = findViewById(R.id.etLN);
        etHouseno = findViewById(R.id.etHouseno);
        etBarangay = findViewById(R.id.etasBarangay);
        etCity = findViewById(R.id.etCity);
        etPurpose = findViewById(R.id.etPurpose);
        btnSubmit = findViewById(R.id.btnSubmit);
        reqBackbtn = findViewById(R.id.reqBackbtn);
        radioGroup = findViewById(R.id.radioGroup);
        rbBarangayClearance = findViewById(R.id.rbBarangayClearance);
        rbBusinessPermit = findViewById(R.id.rbBusinessPermit);
        rbCedula = findViewById(R.id.rbCedula);
        toolbar = findViewById(R.id.toolbar);
        etDate = findViewById(R.id.etDate);
        intent = new Intent(this, RequestSuccess.class);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);



        etDate.setText(currentDateTimeString);
        certdb = FirebaseDatabase.getInstance().getReference().child("Documents");
        certs = new certs();

        autofill();

        certdb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                    maxid=(snapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Check which radio button is selected
                if (checkedId == R.id.rbBusinessPermit) {
                    // Navigate to Activity1
                    Intent intent = new Intent(Request.this, BusinessPermit.class);
                    startActivity(intent);
                } else if (checkedId == R.id.rbCedula) {
                    // Navigate to Activity2
                    Intent intent = new Intent(Request.this, Cedula.class);
                    startActivity(intent);
                }
            }
        });


        reqBackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                int startHour1 = 8;
                int startMinute1 = 0;
                int endHour1 = 11;
                int endMinute1 = 59;

                int startHour2 = 12;
                int startMinute2 = 0;
                int endHour2 = 17;
                int endMinute2 = 0;

                if ((hour >= startHour1 && hour <= endHour1) || (hour == endHour1 && minute <= endMinute1)) {
                    // Time falls within the first interval (8:00 AM to 11:59 AM)
                    Toast.makeText(Request.this, "The requested document is on process", Toast.LENGTH_SHORT).show();
                } else if ((hour >= startHour2 && hour <= endHour2) || (hour == endHour2 && minute <= endMinute2)) {
                    // Time falls within the second interval (12:00 PM to 5:00 PM)
                    Toast.makeText(Request.this, "All requests from 12:00 PM to 5:00 PM will be processed on the next day", Toast.LENGTH_SHORT).show();
                } else {
                    // Time falls outside the specified intervals (5:01 PM to 7:59 AM)
                    Toast.makeText(Request.this, "Requesting of Document is available during office hours", Toast.LENGTH_SHORT).show();
                    return; // Stop further execution
                }

                // If the time is valid, continue with the submission
                String firstname = etFN.getText().toString();
                String middleinitial = etMN.getText().toString();
                String lastname = etLN.getText().toString();
                String houseno = etHouseno.getText().toString();
                String barangay = etBarangay.getText().toString();
                String city = etCity.getText().toString();
                String purpose = etPurpose.getText().toString();

                if (rbBarangayClearance.isChecked()) {
                    certs.setCertificates(rbBarangayClearance.getText().toString().trim());
                } else if (rbCedula.isChecked()) {
                    certs.setCertificates(rbCedula.getText().toString().trim());
                } else {
                    certs.setCertificates(rbBusinessPermit.getText().toString().trim());
                }

                certs.setDate(etDate.getText().toString().trim());
                certs.setFirstname(etFN.getText().toString().trim());
                certs.setMiddleinitial(etMN.getText().toString().trim());
                certs.setLastname(etLN.getText().toString().trim());
                certs.setEmail(CurrentUser.getEmail());
                certs.setHouseno(etHouseno.getText().toString().trim());
                certs.setBarangay(etBarangay.getText().toString().trim());
                certs.setCity(etCity.getText().toString().trim());
                certs.setPurpose(etPurpose.getText().toString().trim());
                certs.setStatus("Pending");
                certdb.child(String.valueOf("CB" + maxid)).setValue(certs);

                Toast.makeText(Request.this, "Registered Successfully", Toast.LENGTH_LONG).show();
                startActivity(intent);
            }
        });


    }
    private void autofill() {

        etFN.setText(sharedPreferences.getString("FN", ""));
        etMN.setText(sharedPreferences.getString("MN", ""));
        etLN.setText(sharedPreferences.getString("LN", ""));
        etHouseno.setText(sharedPreferences.getString("street", ""));
        etBarangay.setText(sharedPreferences.getString("barangay", ""));
        etCity.setText(sharedPreferences.getString("city", ""));
    }

}