package com.example.queuebyv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    TextView tvSignup,tv_Forgotpass;

    public static String PREFS_NAME = "myPrefsFile";
    EditText loginEmail, loginPassword;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.[a-z]+";
    Button alLoginbtn;
    ProgressDialog progressDialog;
    FirebaseAuth mAuth;
    FirebaseUser mUser;

    private CurrentUser CurrentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        CurrentUser = CurrentUser.getInstance();

        tvSignup = findViewById(R.id.tvSignup);
        loginEmail = findViewById(R.id.loginEmail);
        loginPassword = findViewById(R.id.loginPassword);
        alLoginbtn = findViewById(R.id.alLoginbtn);
        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        tv_Forgotpass = findViewById(R.id.tv_Forgotpass);

        tv_Forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ForgotPassword.class);
                startActivity(intent);
            }
        });

        tvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });

        alLoginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performlogin();
            }
        });
    }

    private void performlogin() {
        String email = loginEmail.getText().toString();
        String password = loginPassword.getText().toString();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User");

        if (!email.matches(emailPattern)) {
            loginEmail.setError("email invalid");
        }
        if (password.isEmpty() || password.length() < 6) {
            loginPassword.setError("Password too short");
        } else {
            progressDialog.setMessage("Login in Progress...");
            progressDialog.setTitle("Login");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        progressDialog.dismiss();
                        mUser = mAuth.getCurrentUser();
                        if (mUser.isEmailVerified()) {
                            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                public void onDataChange(@NonNull DataSnapshot snapshot){
                                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                        if (dataSnapshot.child("email").getValue(String.class).equals(email)) {
                                            String ageFromDB = dataSnapshot.child("age").getValue(String.class);
                                            String barangayFromDB = dataSnapshot.child("barangay").getValue(String.class);
                                            String cityFromDB = dataSnapshot.child("city").getValue(String.class);
                                            String CNFromDB = dataSnapshot.child("contactnumber").getValue(String.class);
                                            String emailFromDB = dataSnapshot.child("email").getValue(String.class);
                                            String FNFromDB = dataSnapshot.child("firstname").getValue(String.class);
                                            String LNFromDB = dataSnapshot.child("lastname").getValue(String.class);
                                            String GenderFromDB = dataSnapshot.child("gender").getValue(String.class);
                                            String MNFromDB = dataSnapshot.child("middlename").getValue(String.class);
                                            String streetFromDB = dataSnapshot.child("street").getValue(String.class);
                                            String suffixFromDB = dataSnapshot.child("suffix").getValue(String.class);
                                            String userIDFromDB = dataSnapshot.child("userID").getValue(String.class);
                                            String birthdateFromDB = dataSnapshot.child("birthdate").getValue(String.class);
                                            String birthplaceFromDB = dataSnapshot.child("birthplace").getValue(String.class);


                                            CurrentUser.setAge(ageFromDB);
                                            CurrentUser.setBarangay(barangayFromDB);
                                            CurrentUser.setCity(cityFromDB);
                                            CurrentUser.setCN(CNFromDB);
                                            CurrentUser.setEmail(emailFromDB);
                                            CurrentUser.setFN(FNFromDB);
                                            CurrentUser.setGender(GenderFromDB);
                                            CurrentUser.setLN(LNFromDB);
                                            CurrentUser.setMN(MNFromDB);
                                            CurrentUser.setStreet(streetFromDB);
                                            CurrentUser.setSuffix(suffixFromDB);
                                            CurrentUser.setUserID(userIDFromDB);
                                            CurrentUser.setBirthdate(birthdateFromDB);
                                            CurrentUser.setBirthplace(birthplaceFromDB);


                                            sendUsertoNextActivity();
                                            Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                            SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, 0);
                                            SharedPreferences.Editor editor = sharedPreferences.edit();

                                            editor.putBoolean("hasLoggedIn", true);
                                            editor.apply();

                                            finish();
                                            return;
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });


                        } else {
                            progressDialog.dismiss();
                            mAuth.signOut(); // User is signed out since email is not verified
                            Toast.makeText(LoginActivity.this, "Please verify your email address first", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void sendUsertoNextActivity() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("age", CurrentUser.getAge());
        editor.putString("barangay", CurrentUser.getBarangay());
        editor.putString("city", CurrentUser.getCity());
        editor.putString("CN", CurrentUser.getCN());
        editor.putString("email", CurrentUser.getEmail());
        editor.putString("FN", CurrentUser.getFN());
        editor.putString("gender", CurrentUser.getGender());
        editor.putString("LN", CurrentUser.getLN());
        editor.putString("MN", CurrentUser.getMN());
        editor.putString("street", CurrentUser.getStreet());
        editor.putString("suffix", CurrentUser.getSuffix());
        editor.putString("userID", CurrentUser.getUserID());
        editor.putString("birthdate", CurrentUser.getBirthdate());
        editor.putString("birthplace", CurrentUser.getBirthplace());
        editor.apply();

        Intent intent = new Intent(LoginActivity.this, HomepageActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}