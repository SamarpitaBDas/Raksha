package com.example.raksha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CommunityActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.home){
                    startActivity(new Intent(CommunityActivity.this, HomeActivity.class));
                    return true;
                } else if (itemId == R.id.emergency) {
                    startActivity(new Intent(CommunityActivity.this, EmergencyActivity.class));
                    return true;
                } else if (itemId == R.id.report) {
                    startActivity(new Intent(CommunityActivity.this, ReportActivity.class));
                    return true;
                } else if (itemId == R.id.community) {
                    startActivity(new Intent(CommunityActivity.this, CommunityActivity.class));
                    return true;
                }else {
                    return false;
                }

            }
        });
    }
}
