package com.example.queuebyv2;

import static com.example.queuebyv2.LoginActivity.PREFS_NAME;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class editprofile extends AppCompatActivity {
    Button save;
    ImageView back;
    RadioButton rdMale, rdFemale;
    EditText firstname, middlename, lastname, suffix, bdate, bplace, age, contact, street, brgy, city,email;
    private CurrentUser currentUser;
    private DatabaseReference reference,userdb;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);
        currentUser = CurrentUser.getInstance();
        sharedPreferences = getSharedPreferences(PREFS_NAME, 0);
        reference = FirebaseDatabase.getInstance().getReference("User");
        firstname = findViewById(R.id.ep_firstname);
        middlename = findViewById(R.id.ep_tvmiddlename);
        lastname = findViewById(R.id.ep_lastname);
        email = findViewById(R.id.editEmail);
        suffix = findViewById(R.id.editSuffix);
        bdate = findViewById(R.id.editBirthdate);
        bplace = findViewById(R.id.editBirthplace);
        age = findViewById(R.id.editAge);
        contact = findViewById(R.id.editContact);
        street = findViewById(R.id.editStreet);
        brgy = findViewById(R.id.editBrgy);
        city = findViewById(R.id.editCity);
        save = findViewById(R.id.btnSave);
        back = findViewById(R.id.btneditBack);
        rdMale = findViewById(R.id.rdMale);
        rdFemale = findViewById(R.id.rdFemale);

        firstname.setText(sharedPreferences.getString("FN", ""));
        middlename.setText(sharedPreferences.getString("MN", ""));
        lastname.setText(sharedPreferences.getString("LN", ""));
        suffix.setText(sharedPreferences.getString("suffix", ""));
        email.setText(sharedPreferences.getString("email", ""));
        bdate.setText(sharedPreferences.getString("birthdate", ""));
        age.setText(sharedPreferences.getString("age", ""));
        bplace.setText(sharedPreferences.getString("birthplace", ""));
        String genderValue = sharedPreferences.getString("gender", "");
        if(genderValue.equals("Male"))
        {
            rdMale.setChecked(true);
        }
        else if(genderValue.equals("Female"))
        {
            rdFemale.setChecked(true);
        }
        contact.setText(sharedPreferences.getString("CN", ""));
        street.setText(sharedPreferences.getString("street", ""));
        brgy.setText(sharedPreferences.getString("barangay", ""));
        city.setText(sharedPreferences.getString("city", ""));

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (update()) {
                    Toast.makeText(editprofile.this, "Data has been updated", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(editprofile.this, "No change", Toast.LENGTH_SHORT).show();
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private boolean update() {
        String emailPath = sharedPreferences.getString("userID", "");

        DatabaseReference userRef = reference.child(emailPath);

        // Get the updated values from the EditText fields
        String updatedAge = age.getText().toString();
        String updatedBarangay = brgy.getText().toString();
        String updatedBirthdate = bdate.getText().toString();
        String updatedBirthplace = bplace.getText().toString();
        String updatedCity = city.getText().toString();
        String updatedContactnumber = contact.getText().toString();
        String updatedEmail = email.getText().toString();
        String updatedFirstname = firstname.getText().toString();
        String updatedLastname = lastname.getText().toString();
        String updatedMiddlename = middlename.getText().toString();
        String updatedStreet = street.getText().toString();
        String updatedSuffix = suffix.getText().toString();

        // Create a Map to store the updated user information
        Map<String, Object> userData = new HashMap<>();
        userData.put("age", updatedAge);
        userData.put("barangay", updatedBarangay);
        userData.put("birthdate", updatedBirthdate);
        userData.put("birthplace", updatedBirthplace);
        userData.put("city", updatedCity);
        userData.put("contactnumber", updatedContactnumber);
        userData.put("email", updatedEmail);
        userData.put("firstname", updatedFirstname);
        userData.put("lastname", updatedLastname);
        userData.put("middlename", updatedMiddlename);
        userData.put("street", updatedStreet);
        userData.put("suffix", updatedSuffix);

        // Update the user's profile
        userRef.updateChildren(userData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Profile update successful
                        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, 0);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("age", updatedAge.toString());
                        editor.putString("barangay", updatedBarangay);
                        editor.putString("city", updatedCity);
                        editor.putString("CN", updatedContactnumber);
                        editor.putString("email", updatedEmail);
                        editor.putString("FN", updatedFirstname);
                        //editor.putString("gender", updatedGender);
                        editor.putString("LN", updatedLastname);
                        editor.putString("MN", updatedMiddlename);
                        editor.putString("street", updatedStreet);
                        editor.putString("suffix", updatedSuffix);
                        //editor.putString("userID", CurrentUser.getUserID());
                        editor.putString("birthdate", updatedBirthdate);
                        editor.putString("birthplace", updatedBirthplace);
                        editor.apply();
                        Toast.makeText(editprofile.this, "Data has been updated", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Profile update failed
                        Toast.makeText(editprofile.this, "Failed to update data", Toast.LENGTH_SHORT).show();
                    }
                });

        // Return true if any field was updated, false otherwise
        return true;
    }

}
