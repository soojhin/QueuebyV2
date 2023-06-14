package com.example.queuebyv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button MainSignupbtn, MainLoginbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainSignupbtn = findViewById(R.id.MainSignupbtn);
        MainLoginbtn = findViewById(R.id.MainLoginbtn);

        SharedPreferences sharedPreferences = getSharedPreferences(LoginActivity.PREFS_NAME, 0);
        boolean hasLoggedIn = sharedPreferences.getBoolean("hasLoggedIn", false);

        if (restorePrefData()){
            Intent LoginActivity = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(LoginActivity);
            finish();
            if (hasLoggedIn) {
                Intent intent = new Intent(MainActivity.this, HomepageActivity.class);
                startActivity(intent);
                finish();
            } else {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }

        MainSignupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,SignupActivity.class);
                startActivity(intent);
            }
        });

        MainLoginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
                savePrefsData();
                finish();
            }
        });
    }

    private boolean restorePrefData() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs", MODE_PRIVATE);
        Boolean isIntroActivityOpenedBefore = pref.getBoolean("isIntroOpened", false);
        return isIntroActivityOpenedBefore;
    }

    private void savePrefsData(){
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("isIntroOpened", true);
        editor.commit();
    }
}