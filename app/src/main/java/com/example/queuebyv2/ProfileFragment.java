package com.example.queuebyv2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import de.hdodenhof.circleimageview.CircleImageView;


public class ProfileFragment extends Fragment {

    Button btnLogout,btnEdit,btnPrivacy,btnChangePW;
    TextView fn, mn, ln, age, birthdate, birthplace, street, barangay, city, cn, suffix, gender,email;
    DatabaseReference reference;
    private CurrentUser CurrentUser;
    AlertDialog.Builder builder;
    private SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        reference = FirebaseDatabase.getInstance().getReference("User");
        CurrentUser = CurrentUser.getInstance();
        sharedPreferences = getActivity().getSharedPreferences(LoginActivity.PREFS_NAME, 0);

        fn = view.findViewById(R.id.pf_Firstname);
        mn = view.findViewById(R.id.pf_Middlename);
        ln = view.findViewById(R.id.pf_Lastname);
        age = view.findViewById(R.id.pf_Age);
        birthdate = view.findViewById(R.id.pf_Birthdate);
        birthplace = view.findViewById(R.id.pf_Birthplace);
        street = view.findViewById(R.id.pf_Street);
        barangay = view.findViewById(R.id.pf_Barangay);
        city = view.findViewById(R.id.pf_city);
        cn = view.findViewById(R.id.pf_Contactnumber);
        suffix = view.findViewById(R.id.pf_Suffix);
        email = view.findViewById(R.id.pf_email);
        gender = view.findViewById(R.id.pf_Gender);
        builder = new AlertDialog.Builder(getActivity());
        btnLogout = view.findViewById(R.id.btnLogout);
        btnEdit = view.findViewById(R.id.pf_Editprofile);
        btnPrivacy = view.findViewById(R.id.pf_Oppbtn);
        btnChangePW = view.findViewById(R.id.pf_Changepassword);


        showUserDetails();


        CircleImageView profileView = view.findViewById(R.id.ProfileView);
        Drawable backgroundDrawable = getResources().getDrawable(R.drawable.circle_background);
        profileView.setBackground(backgroundDrawable);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), editprofile.class);
                startActivity(intent);
            }
        });

        btnPrivacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), PrivacyPolicy.class);
                startActivity(intent);
            }
        });

        btnChangePW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), changepassword.class);
                startActivity(intent);
            }
        });


        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("").setMessage("Log out of your account?").setCancelable(true).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(getActivity().getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(LoginActivity.PREFS_NAME, 0);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("hasLoggedIn", false);
                        editor.commit();
                        getActivity().finish();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                }).show();

            }
        });

        return view;
    }

    private void showUserDetails()
    {
        fn.setText(sharedPreferences.getString("FN", ""));
        mn.setText(sharedPreferences.getString("MN", ""));
        ln.setText(sharedPreferences.getString("LN", ""));
        suffix.setText(sharedPreferences.getString("suffix", ""));
        email.setText(sharedPreferences.getString("email", ""));
        birthdate.setText(sharedPreferences.getString("birthdate", ""));
        age.setText(sharedPreferences.getString("age", ""));
        birthplace.setText(sharedPreferences.getString("birthplace", ""));
        gender.setText(sharedPreferences.getString("gender", ""));
        cn.setText(sharedPreferences.getString("CN", ""));
        street.setText(sharedPreferences.getString("street", ""));
        barangay.setText(sharedPreferences.getString("barangay", ""));
        city.setText(sharedPreferences.getString("city", ""));

    }
}
