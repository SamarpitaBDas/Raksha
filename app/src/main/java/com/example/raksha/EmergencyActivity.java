package com.example.raksha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class EmergencyActivity extends AppCompatActivity {

//    TODO logic for emergency calling the nearest faculty

//    TODO loginpage-error, intents handle , white navbar, call , emergency nearest faculty, community tab beautify create

    //TODO bug fix opens by itself after login and doesn't let the homepage open up
    public static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;

    public String emergencyusername;
    Button faculty_call;
    Button call_police;
    Button call_hospital;
    Button call_womenhp;
    Button call_womencommision;
    Button call_women_honor_call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);

        faculty_call = findViewById(R.id.faculty_call);
        call_police = findViewById(R.id.call_police);
        call_hospital = findViewById(R.id.call_hospital);
        call_womenhp = findViewById(R.id.call_womenhp);
        call_womencommision = findViewById(R.id.call_womencommision);
        call_women_honor_call = findViewById(R.id.call_women_honor_call);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                Intent intent = getIntent();

                emergencyusername = intent.getStringExtra("username");
                if (itemId == R.id.home){
                    startActivity(new Intent(EmergencyActivity.this, HomeActivity.class)
                            .putExtra("username", emergencyusername));

                    Intent homeIntent = new Intent(EmergencyActivity.this, HomeActivity.class);
                    homeIntent.putExtra("username", emergencyusername);
                    homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(homeIntent);
                    return true;
                } else if (itemId == R.id.report) {
                    startActivity(new Intent(EmergencyActivity.this, ReportActivity.class)
                            .putExtra("username", emergencyusername));
                    return true;
                } else if (itemId == R.id.community) {
                    startActivity(new Intent(EmergencyActivity.this, CommunityActivity.class)
                            .putExtra("username", emergencyusername));
                    return true;
                } else {
                    return false;
                }

            }
        });

        faculty_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makePhoneCall("1234567890");
            }
        });

        call_police.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makePhoneCall("100");
            }
        });

        call_hospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makePhoneCall("112");
            }
        });

        call_womenhp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makePhoneCall("1090");
            }
        });

        call_womencommision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makePhoneCall("9454401122");
            }
        });

        call_women_honor_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makePhoneCall("5222614978");
            }
        });
    }

    private void makePhoneCall(String phoneNumber) {
        if (ContextCompat.checkSelfPermission(EmergencyActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(EmergencyActivity.this, new String[]{Manifest.permission.CALL_PHONE}, MY_PERMISSIONS_REQUEST_CALL_PHONE);
        } else {
            String dial = "tel:" + phoneNumber;
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSIONS_REQUEST_CALL_PHONE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

