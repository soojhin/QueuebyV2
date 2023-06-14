package com.example.queuebyv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

public class BusinessPermit extends AppCompatActivity {
    Toolbar toolbar;
    RadioGroup radioGroup;
    certs certs;
    Button btnBack,btnSubmitBP;
    RadioButton rbBarangayClearance, rbBusinessPermit, rbCedula;
    EditText etPurposeBP,etDateBP;
    Intent intent;
    DatabaseReference certdb;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Button btnSelectImage,btnSelectBusiness;
    long maxid = 0;
    private CurrentUser CurrentUser;
    private ImageView imageView,imageView1;
    String currentDateTimeString = java.text.DateFormat.getDateInstance().format(new Date());

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_permint);

        etPurposeBP = findViewById(R.id.etPurpose);
        etDateBP = findViewById(R.id.etDate);
        radioGroup = findViewById(R.id.radioGroup);
        rbBarangayClearance = findViewById(R.id.rbBarangayClearance);
        rbBusinessPermit = findViewById(R.id.rbBusinessPermit);
        rbCedula = findViewById(R.id.rbCedula);
        toolbar = findViewById(R.id.toolbar);
        btnBack = findViewById(R.id.bPermitBackbtn);
        btnSelectImage = findViewById(R.id.btnID);
        imageView = findViewById(R.id.imageView);
        btnSelectBusiness = findViewById(R.id.btnBusiness);
        imageView1 = findViewById(R.id.imageView1);
        btnSubmitBP = findViewById(R.id.btnSubmit);
        setSupportActionBar(toolbar);
        intent = new Intent(this, RequestSuccess.class);
        CurrentUser = CurrentUser.getInstance();
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        certdb = FirebaseDatabase.getInstance().getReference().child("Documents");
        certs = new certs();
        etDateBP.setText(currentDateTimeString);



        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Check which radio button is selected
                if (checkedId == R.id.rbBarangayClearance) {
                    // Navigate to Activity1
                    Intent intent = new Intent(BusinessPermit.this, Request.class);
                    startActivity(intent);

                } else if (checkedId == R.id.rbCedula) {
                    // Navigate to Activity2
                    Intent intent = new Intent(BusinessPermit.this, Cedula.class);
                    startActivity(intent);
                }
            }
        });

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

        btnSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery(1);
            }
        });

        btnSelectBusiness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery(2);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnSubmitBP.setOnClickListener(new View.OnClickListener() {
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
                    Toast.makeText(BusinessPermit.this, "The requested document is on process", Toast.LENGTH_SHORT).show();
                } else if ((hour >= startHour2 && hour <= endHour2) || (hour == endHour2 && minute <= endMinute2)) {
                    // Time falls within the second interval (12:00 PM to 5:00 PM)
                    Toast.makeText(BusinessPermit.this, "All requests from 12:00 PM to 5:00 PM will be processed on the next day", Toast.LENGTH_SHORT).show();
                } else {
                    // Time falls outside the specified intervals (5:01 PM to 7:59 AM)
                    Toast.makeText(BusinessPermit.this, "Requesting of Document is available during office hours", Toast.LENGTH_SHORT).show();
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

                certs.setDate(etDateBP.getText().toString().trim());
                certs.setFirstname(firstname.trim());
                certs.setMiddleinitial(middleinitial.trim());
                certs.setLastname(lastname.trim());
                certs.setEmail(CurrentUser.getEmail());
                certs.setHouseno(houseno.trim());
                certs.setBarangay(barangay.trim());
                certs.setCity(city.trim());
                certs.setPurpose(etPurposeBP.getText().toString().trim());
                certs.setStatus("Pending");
                certdb.child(String.valueOf("CB" + maxid)).setValue(certs);

                Toast.makeText(BusinessPermit.this, "Registered Successfully", Toast.LENGTH_LONG).show();
                startActivity(intent);
            }
        });
    }
    private void openGallery(int requestCode) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, requestCode);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, dpToPx(280), dpToPx(250), true);

                if (requestCode == 1) {
                    imageView.setImageBitmap(resizedBitmap);
                } else if (requestCode == 2) {
                    imageView1.setImageBitmap(resizedBitmap);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }
}