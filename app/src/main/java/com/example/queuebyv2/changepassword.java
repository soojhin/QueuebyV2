package com.example.queuebyv2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class changepassword extends AppCompatActivity {
    Button cp_savechangesbtn;

    EditText cp_etOldpass, cp_Newpass, cp_etReenternew;
    ImageView back;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepassword);

        //save = findViewById(R.id.btnSave);
        back = findViewById(R.id.btnChangePassBack);
        cp_etOldpass = findViewById(R.id.etFN);
        cp_Newpass = findViewById(R.id.cp_etNewpass);
        cp_etReenternew = findViewById(R.id.cp_etReenternew);
        cp_savechangesbtn = findViewById(R.id.cp_savechangesbtn);

        firebaseAuth = FirebaseAuth.getInstance();

        cp_savechangesbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePassword();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void changePassword() {
        final FirebaseUser user = firebaseAuth.getCurrentUser();
        final String oldPassword = cp_etOldpass.getText().toString().trim();
        final String newPassword = cp_Newpass.getText().toString().trim();
        final String reenterNewPassword = cp_etReenternew.getText().toString().trim();

        if (user != null) {
            if (oldPassword.isEmpty() || newPassword.isEmpty() || reenterNewPassword.isEmpty()) {
                Toast.makeText(changepassword.this, "Fields cannot be left blank", Toast.LENGTH_SHORT).show();
            } else if (!newPassword.equals(reenterNewPassword)) {
                Toast.makeText(changepassword.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            } else {
                // Prompt the user to re-enter their credentials
                AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), oldPassword);

                // Re-authenticate the user with their credentials
                user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(Task<Void> task) {
                        if (task.isSuccessful()) {
                            // Update the password
                            user.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(changepassword.this, "Password updated successfully", Toast.LENGTH_SHORT).show();
                                        finish();
                                    } else {
                                        Toast.makeText(changepassword.this, "Failed to update password", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(changepassword.this, "Incorrect old password", Toast.LENGTH_SHORT).show();
                            // Retry changing password by recursively calling the changePassword method
                            changePassword();
                        }
                    }
                });
            }
        } else {
            Toast.makeText(changepassword.this, "User is not logged in", Toast.LENGTH_SHORT).show();
        }
    }
}