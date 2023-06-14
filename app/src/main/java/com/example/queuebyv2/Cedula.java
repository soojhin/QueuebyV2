package com.example.queuebyv2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;

public class Cedula extends AppCompatActivity {

    Toolbar toolbar;
    EditText etFN, etMN, etLN, etHouseno, etBarangay, etCity, etPurpose,etDate,etBirthdate,etBirthplace,etGrosspay;
    RadioGroup radioGroup;
    certs certs;
    Intent intent;
    long maxid = 0;
    private CurrentUser CurrentUser;
    DatabaseReference certdb;

    Button btnSubmit;
    RadioButton rbBarangayClearance, rbBusinessPermit, rbCedula;
    String currentDateTimeString = java.text.DateFormat.getDateInstance().format(new Date());

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cedula);
        etFN = findViewById(R.id.etFN);
        etMN = findViewById(R.id.etMN);
        etLN = findViewById(R.id.etLN);
        etHouseno = findViewById(R.id.etHouseno);
        etBarangay = findViewById(R.id.etasBarangay);
        etCity = findViewById(R.id.etCity);
        etPurpose = findViewById(R.id.etPurpose);
        etDate = findViewById(R.id.etDate);
        etBirthdate = findViewById(R.id.etBirthdate);
        etBirthplace = findViewById(R.id.etBirthplace);
        etGrosspay = findViewById(R.id.etGrosspay);
        btnSubmit = findViewById(R.id.btnSubmit);
        radioGroup = findViewById(R.id.radioGroup);
        rbBarangayClearance = findViewById(R.id.rbBarangayClearance);
        rbBusinessPermit = findViewById(R.id.rbBusinessPermit);
        rbCedula = findViewById(R.id.rbCedula);
        toolbar = findViewById(R.id.toolbar);
        intent = new Intent(this, RequestSuccess.class);
        CurrentUser = CurrentUser.getInstance();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        certdb = FirebaseDatabase.getInstance().getReference().child("Documents");
        certs = new certs();
        etDate.setText(currentDateTimeString);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Check which radio button is selected
                if (checkedId == R.id.rbBarangayClearance) {
                    // Navigate to Activity1
                    Intent intent = new Intent(Cedula.this, Request.class);
                    startActivity(intent);
                } else if (checkedId == R.id.rbBusinessPermit) {
                    // Navigate to Activity2
                    Intent intent = new Intent(Cedula.this, BusinessPermit.class);
                    startActivity(intent);
                }
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
                    Toast.makeText(Cedula.this, "The requested document is on process", Toast.LENGTH_SHORT).show();
                } else if ((hour >= startHour2 && hour <= endHour2) || (hour == endHour2 && minute <= endMinute2)) {
                    // Time falls within the second interval (12:00 PM to 5:00 PM)
                    Toast.makeText(Cedula.this, "All requests from 12:00 PM to 5:00 PM will be processed on the next day", Toast.LENGTH_SHORT).show();
                } else {
                    // Time falls outside the specified intervals (5:01 PM to 7:59 AM)
                    Toast.makeText(Cedula.this, "Requesting of Document is available during office hours", Toast.LENGTH_SHORT).show();
                    return; // Stop further execution
                }

                // If the time is valid, continue with the submission
                String firstname = CurrentUser.getFN();
                String middleinitial = CurrentUser.getMN();
                String lastname = CurrentUser.getLN();
                String houseno = CurrentUser.getStreet();
                String barangay = CurrentUser.getBarangay();
                String city = CurrentUser.getCity();


                if (rbBarangayClearance.isChecked()) {
                    certs.setCertificates(rbBarangayClearance.getText().toString().trim());
                } else if (rbCedula.isChecked()) {
                    certs.setCertificates(rbCedula.getText().toString().trim());
                } else {
                    certs.setCertificates(rbBusinessPermit.getText().toString().trim());
                }

                certs.setDate(etDate.getText().toString().trim());
                certs.setFirstname(firstname.trim());
                certs.setMiddleinitial(middleinitial.trim());
                certs.setLastname(lastname.trim());
                certs.setEmail(CurrentUser.getEmail());
                certs.setHouseno(houseno.trim());
                certs.setBarangay(barangay.trim());
                certs.setCity(city.trim());
                certs.setPurpose(etPurpose.getText().toString().trim());
                certs.setStatus("Pending");
                certdb.child(String.valueOf("CB" + maxid)).setValue(certs);

                Toast.makeText(Cedula.this, "Registered Successfully", Toast.LENGTH_LONG).show();
                startActivity(intent);
            }
        });
    }
}