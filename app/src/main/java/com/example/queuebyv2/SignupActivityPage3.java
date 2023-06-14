package com.example.queuebyv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class SignupActivityPage3 extends AppCompatActivity {

    Button asp3Nextbtn;
    Toolbar toolbar;

    TextView as3_tvgender, as3_tvaddress;

    EditText etFirstname, etMiddlename, etLastname, etSuffix, etBirthdate, etAge, etBirthplace, etContactnumber, etStreet, etasBarangay, etasCity;
    RadioButton rbMale, rbFemale;


    DatabaseReference userdb;

    user user;

    String gender;

    long maxid = 0;

    private CurrentUser CurrentUser;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_page3);

        CurrentUser = CurrentUser.getInstance();

        etFirstname = findViewById(R.id.etFirstname);
        etMiddlename = findViewById(R.id.etMiddlename);
        etLastname = findViewById(R.id.etLastname);
        etSuffix = findViewById(R.id.etSuffix);
        etBirthdate = findViewById(R.id.etBirthdate);
        etAge = findViewById(R.id.etAge);
        etBirthplace = findViewById(R.id.etBirthplace);
        etContactnumber = findViewById(R.id.etContactnumber);
        etStreet = findViewById(R.id.etStreet);
        etasBarangay = findViewById(R.id.etasBarangay);
        etasCity = findViewById(R.id.etasCity);
        rbMale = findViewById(R.id.rbMale);
        rbFemale = findViewById(R.id.rbFemale);
        asp3Nextbtn = findViewById(R.id.asp3Nextbtn);
        as3_tvgender = findViewById(R.id.as3_tvgender);
        as3_tvaddress = findViewById(R.id.as3_tvaddress);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        // Set the hints with red asterisks
        SpannableString firstNameHint = new SpannableString("First Name*");
        SpannableString lastNameHint = new SpannableString("Last Name*");
        SpannableString birthdateHint = new SpannableString("Birthdate*");
        SpannableString ageHint = new SpannableString("Age*");
        SpannableString birthplaceHint = new SpannableString("Birthplace*");
        SpannableString contactNumberHint = new SpannableString("Contact Number*");
        SpannableString genderHint = new SpannableString("Gender*");
        SpannableString addressHint = new SpannableString("Address*");

        ForegroundColorSpan redColorSpan = new ForegroundColorSpan(Color.RED);

        firstNameHint.setSpan(redColorSpan, firstNameHint.length() - 1, firstNameHint.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        lastNameHint.setSpan(redColorSpan, lastNameHint.length() - 1, lastNameHint.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        birthdateHint.setSpan(redColorSpan, birthdateHint.length() - 1, birthdateHint.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ageHint.setSpan(redColorSpan, ageHint.length() - 1, ageHint.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        birthplaceHint.setSpan(redColorSpan, birthplaceHint.length() - 1, birthplaceHint.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        contactNumberHint.setSpan(redColorSpan, contactNumberHint.length() - 1, contactNumberHint.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        genderHint.setSpan(redColorSpan, genderHint.length() - 1, genderHint.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        addressHint.setSpan(redColorSpan, addressHint.length() - 1, addressHint.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        etFirstname.setHint(firstNameHint);
        etLastname.setHint(lastNameHint);
        etBirthdate.setHint(birthdateHint);
        etAge.setHint(ageHint);
        etBirthplace.setHint(birthplaceHint);
        etContactnumber.setHint(contactNumberHint);
        as3_tvgender.setText(genderHint);
        as3_tvaddress.setText(addressHint);

        userdb = FirebaseDatabase.getInstance().getReference().child("User");

        userdb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                    maxid=(snapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        asp3Nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertUserData();
            }
        });

        etBirthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the current date
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                // Create a new DatePickerDialog instance
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        SignupActivityPage3.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                // Update the EditText view with the selected date
                                etBirthdate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        },
                        year, month, day);

                // Show the date picker dialog
                datePickerDialog.show();
            }
        });
    }



    private void insertUserData() {

        String firstname = etFirstname.getText().toString();
        String middlename = etMiddlename.getText().toString();
        String lastname = etLastname.getText().toString();
        String suffix = etSuffix.getText().toString();
        String age = etAge.getText().toString();
        String birthplace = etBirthplace.getText().toString();
        String contactnumber = etContactnumber.getText().toString();
        String street = etStreet.getText().toString();
        String barangay = etasBarangay.getText().toString();
        String city = etasCity.getText().toString();
        String birthdate = etBirthdate.getText().toString();

        if (firstname.isEmpty() || lastname.isEmpty() || age.isEmpty() || birthplace.isEmpty() ||
                contactnumber.isEmpty() || street.isEmpty() || barangay.isEmpty() || city.isEmpty() || birthdate.isEmpty()) {
            Toast.makeText(this, "Required fields cannot be empty", Toast.LENGTH_SHORT).show();
        } else {
            if (suffix.isEmpty()) {
                etSuffix.setText("--");
            }
            if (rbMale.isChecked()) {
                rbMale.getText().toString();
                gender = rbMale.getText().toString();
            } else if (rbFemale.isChecked()) {
                rbFemale.getText().toString();
                gender = rbFemale.getText().toString();
            }

            CurrentUser.setAge(age);
            CurrentUser.setBarangay(barangay);
            CurrentUser.setCity(city);
            CurrentUser.setCN(contactnumber);
            CurrentUser.setFN(firstname);
            CurrentUser.setLN(lastname);
            CurrentUser.setMN(middlename);
            CurrentUser.setStreet(street);
            CurrentUser.setSuffix(suffix);
            CurrentUser.setBirthplace(birthplace);
            CurrentUser.setBirthdate(birthdate);
            CurrentUser.setGender(gender);

            Intent intent = new Intent(SignupActivityPage3.this, SignupActivityPage4.class);
            startActivity(intent);

        }
    }
}