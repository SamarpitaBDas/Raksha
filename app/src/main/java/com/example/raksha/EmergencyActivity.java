package com.example.raksha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class EmergencyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.home){
                    startActivity(new Intent(EmergencyActivity.this, HomeActivity.class));
                    return true;
                } else if (itemId == R.id.emergency) {
                    startActivity(new Intent(EmergencyActivity.this, EmergencyActivity.class));
                    return true;
                } else if (itemId == R.id.report) {
                    startActivity(new Intent(EmergencyActivity.this, ReportActivity.class));
                    return true;
                } else if (itemId == R.id.community) {
                    startActivity(new Intent(EmergencyActivity.this, CommunityActivity.class));
                    return true;
                }else {
                    return false;
                }

            }
        });
    }
}
