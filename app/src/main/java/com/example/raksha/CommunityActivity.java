package com.example.raksha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CommunityActivity extends AppCompatActivity {
    public String communityusername;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                Intent intent = getIntent();

                communityusername = intent.getStringExtra("username");
                Log.d("LoginActivity", "Username: " + communityusername);
                if (itemId == R.id.home){

                    Log.d("LoginActivity", "Username: " + communityusername);
                    startActivity(new Intent(CommunityActivity.this, HomeActivity.class)
                            .putExtra("username", communityusername));
                    return true;
                } else if (itemId == R.id.emergency) {

                    Log.d("LoginActivity", "Username: " + communityusername);
                    startActivity(new Intent(CommunityActivity.this, EmergencyActivity.class)
                            .putExtra("username", communityusername));
                    return true;
                } else if (itemId == R.id.report) {

                    Log.d("LoginActivity", "Username: " + communityusername);
                    startActivity(new Intent(CommunityActivity.this, ReportActivity.class)
                            .putExtra("username", communityusername));
                    return true;
                }else {
                    return false;
                }

            }
        });
    }
}
