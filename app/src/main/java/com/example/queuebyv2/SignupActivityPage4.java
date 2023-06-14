package com.example.queuebyv2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignupActivityPage4 extends AppCompatActivity {
    Button asp4Backbtn, asp4Nextbtn;

    CheckBox cb_agreement;

    TextView email, fn, mn, ln, suffix, birthdate, age, birthplace, gender, cn, street, barangay, city;
    Toolbar toolbar;

    DatabaseReference userdb;

    user user;

    long maxid = 0;

    private CurrentUser CurrentUser;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_page4);

        init();



        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

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

        asp4Backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupActivityPage4.this,SignupActivityPage3.class);
                startActivity(intent);
            }
        });

        asp4Nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cb_agreement.isChecked()) {
                    insertUserData();
                } else {
                    Toast.makeText(SignupActivityPage4.this, "Please agree to the terms and conditions", Toast.LENGTH_SHORT).show();
                }
            }
        });


        String message = "I agree to Barangay Comembo Mobile Application's Privacy Policy and Terms and Conditions";

        SpannableString spannableString = new SpannableString(message);

// Find the indices of the words "Privacy Policy" and "Terms and Conditions"
        int privacyPolicyStartIndex = message.indexOf("Privacy Policy");
        int privacyPolicyEndIndex = privacyPolicyStartIndex + "Privacy Policy".length();

        int termsStartIndex = message.indexOf("Terms and Conditions");
        int termsEndIndex = termsStartIndex + "Terms and Conditions".length();

// Create a ClickableSpan for the "Privacy Policy"
        ClickableSpan privacyPolicyClickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupActivityPage4.this, PrivacyPolicy.class);
                startActivity(intent);
            }
        };

// Create a ClickableSpan for the "Terms and Conditions"
        ClickableSpan termsClickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupActivityPage4.this, TermsConditions.class);
                startActivity(intent);
            }
        };

// Set the color for the underlined words
        spannableString.setSpan(new ForegroundColorSpan(0xFF3BACF1), privacyPolicyStartIndex, privacyPolicyEndIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(0xFF3BACF1), termsStartIndex, termsEndIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

// Set the ClickableSpans
        spannableString.setSpan(privacyPolicyClickableSpan, privacyPolicyStartIndex, privacyPolicyEndIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(termsClickableSpan, termsStartIndex, termsEndIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        cb_agreement.setText(spannableString);
        cb_agreement.setMovementMethod(LinkMovementMethod.getInstance());


    }

    private void init(){
        CurrentUser = CurrentUser.getInstance();

        asp4Backbtn = findViewById(R.id.asp4Backbtn);
        asp4Nextbtn = findViewById(R.id.asp4Nextbtn);
        toolbar = findViewById(R.id.toolbar);

        email = findViewById(R.id.tvEmail);
        fn = findViewById(R.id.tvFirstname);
        mn = findViewById(R.id.tvMiddlename);
        ln = findViewById(R.id.tvLastname);
        suffix = findViewById(R.id.tvSuffix);
        age = findViewById(R.id.tvAge);
        birthdate = findViewById(R.id.tvBirthdate);
        birthplace = findViewById(R.id.tvBirthplace);
        gender = findViewById(R.id.tvGender);
        cn = findViewById(R.id.tvContactnumber);
        street = findViewById(R.id.tvStreet);
        barangay = findViewById(R.id.tvBarangay);
        city = findViewById(R.id.tvCity);
        cb_agreement = findViewById(R.id.cb_agreement);

        email.setText(CurrentUser.getEmail());
        fn.setText( CurrentUser.getFN());
        mn.setText( CurrentUser.getMN());
        ln.setText( CurrentUser.getLN());
        suffix.setText( CurrentUser.getSuffix());
        age.setText( CurrentUser.getAge());
        birthdate.setText(CurrentUser.getBirthdate());
        birthplace.setText( CurrentUser.getBirthplace());
        gender.setText( CurrentUser.getGender());
        cn.setText( CurrentUser.getCN());
        street.setText(CurrentUser.getStreet());
        barangay.setText( CurrentUser.getBarangay());
        city.setText(CurrentUser.getCity());
    }
    private void insertUserData() {

        String  email = CurrentUser.getEmail();
        String fn  = CurrentUser.getFN();
        String mn = CurrentUser.getMN();
        String ln   = CurrentUser.getLN();
        String suffix = CurrentUser.getSuffix();
        String age = CurrentUser.getAge();
        String birthplace = CurrentUser.getBirthplace();
        String cn = CurrentUser.getCN();
        String street = CurrentUser.getStreet();
        String barangay = CurrentUser.getBarangay();
        String city = CurrentUser.getCity();
        String birthdate = CurrentUser.getBirthdate();
        String gender = CurrentUser.getGender();


        String userId = "CB" + maxid;
        String userID = userId;
        user user = new user(userID,email,fn,mn,ln,suffix,age,birthdate,birthplace,cn,street,barangay,city,gender);
        userdb.child(userId).setValue(user);
        Toast.makeText(this, "Registered Successfully", Toast.LENGTH_SHORT).show();
        userdb.child(String.valueOf("CB" + maxid)).setValue(user);

        Intent intent = new Intent(SignupActivityPage4.this,SignupActivityPage5.class);
        startActivity(intent);

    }
}