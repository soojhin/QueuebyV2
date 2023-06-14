package com.example.queuebyv2;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Context;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {
    Button btnRequest;
    ImageSlider homefragmentSlider;
    TextView homeSeeFulldetails, userFirstname, tvGmail;
    Toolbar toolbar;
    ImageView imgOfficials, imgFb, imgGmail ;
    ImageSlider mainslider, officialSlider;
    private CurrentUser CurrentUser;
    private SharedPreferences sharedPreferences;

    private static final String EMAIL_ADDRESS = "barangaycomembo28@gmail.com";

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        btnRequest = view.findViewById(R.id.btnRequest);
        homeSeeFulldetails = view.findViewById(R.id.homeSeeFulldetails);
        toolbar = view.findViewById(R.id.toolbar);
        officialSlider = view.findViewById(R.id.officialSlider);
        imgOfficials =view.findViewById(R.id.imgOfficials);
        userFirstname = view.findViewById(R.id.userFN);
        imgFb = view.findViewById(R.id.imgFacebook);
        imgGmail = view.findViewById(R.id.imgGmail);
        tvGmail = view.findViewById(R.id.tvGmail);
        CurrentUser = CurrentUser.getInstance();
        sharedPreferences = getActivity().getSharedPreferences(LoginActivity.PREFS_NAME, 0);
        mainslider = view.findViewById(R.id.officialfragmentSlider);
        homeSeeFulldetails.setPaintFlags(homeSeeFulldetails.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        HomeFragment context = this;
        PackageManager packageManager = getActivity().getPackageManager();

        // Set the toolbar as the activity's action bar
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        userFirstname.setText(sharedPreferences.getString("FN", ""));

        homeSeeFulldetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showdialog();
            }
        });

        final List<SlideModel> remoteimages = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference().child("Slider").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data:snapshot.getChildren())

                    remoteimages.add(new SlideModel(data.child("url").getValue().toString(), ScaleTypes.FIT));
                mainslider.setImageList(remoteimages,ScaleTypes.FIT);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Request.class);
                startActivity(intent);
            }
        });

        imgFb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotUrl("https://www.facebook.com/UnitedComembo2018");
            }
        });

        imgGmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open Gmail app
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{EMAIL_ADDRESS});

                // Start the activity if there is an app to handle the intent
                if (intent.resolveActivity(packageManager) != null) { // Updated this line
                    startActivity(intent);
                }
            }
        });

        tvGmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open Gmail app
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{EMAIL_ADDRESS});

                // Start the activity if there is an app to handle the intent
                if (intent.resolveActivity(packageManager) != null) { // Updated this line
                    startActivity(intent);
                }
            }
        });


        return view;
    }
    private void showdialog() {
        final Dialog dialog = new Dialog(getContext());
        final List<SlideModel> remoteimages = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference().child("OfficialSlider").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data:snapshot.getChildren())

                    remoteimages.add(new SlideModel(data.child("link").getValue().toString(), ScaleTypes.FIT));
                // Use findViewById() to get a reference to officialSlider
                officialSlider = dialog.findViewById(R.id.officialSlider);
                officialSlider.setImageList(remoteimages,ScaleTypes.FIT);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.officialsheetlayout);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.show();

    }

    private void gotUrl(String s) {
        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));
    }


}