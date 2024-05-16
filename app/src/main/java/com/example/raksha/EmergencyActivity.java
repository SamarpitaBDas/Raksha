package com.example.raksha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class EmergencyActivity extends AppCompatActivity {
    public String emergencyusername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                Intent intent = getIntent();

                emergencyusername = intent.getStringExtra("username");
//                Log.d("LoginActivity", "Username: " + emergencyusername);
                if (itemId == R.id.home){
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
    }
}
