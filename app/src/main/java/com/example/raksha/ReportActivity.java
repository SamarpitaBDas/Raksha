package com.example.raksha;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

//TODO closing of report activity as soon as the report is registered
public class ReportActivity extends AppCompatActivity {
    private EditText locationEditText, descriptionEditText, accusedEditText;
    private String reportusername;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        // initializing EditText
        locationEditText = findViewById(R.id.locationEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        accusedEditText = findViewById(R.id.accusedEditText);

        //retriving username from intent
        Intent intent = getIntent();
        reportusername = intent.getStringExtra("username");

        //fetching users location data from database
        fetchUserLocationFromDatabase(reportusername);

        // Seting up bottom navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.home) {
                    Intent communityIntent = new Intent(ReportActivity.this, HomeActivity.class);
                    communityIntent.putExtra("username", reportusername);
                    communityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(communityIntent);
                    return true;
                } else if (itemId == R.id.emergency) {
                    startActivity(new Intent(ReportActivity.this, EmergencyActivity.class)
                            .putExtra("username", reportusername));
                    return true;
                } else if (itemId == R.id.community) {
                    startActivity(new Intent(ReportActivity.this, CommunityActivity.class)
                            .putExtra("username", reportusername));
                    return true;
                } else {
                    return false;
                }
            }
        });

        // Set up Submit Report button click listener
        Button submitReportButton = findViewById(R.id.submitReportButton);
        submitReportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitReportToDatabase();
            }
        });
    }

    private void fetchUserLocationFromDatabase(String username) {
        FirebaseDatabase.getInstance().getReference("users").child(username)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            // Retrieve user's location data
                            Double latitude = dataSnapshot.child("latitude").getValue(Double.class);
                            Double longitude = dataSnapshot.child("longitude").getValue(Double.class);

                            // Set location text in the EditText field
                            if (latitude != null && longitude != null) {
                                locationEditText.setText(String.format("Latitude: %f, Longitude: %f", latitude, longitude));
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle database error
                    }
                });
    }
    private void submitReportToDatabase() {
        // Retrieve data from EditText fields
        String location = locationEditText.getText().toString();
        String description = descriptionEditText.getText().toString();
        String accused = accusedEditText.getText().toString();

        // Generates timestamp
        String timestamp = getCurrentTimestamp();

        // Creates a unique key for the report
        String reportKey = "report" + System.currentTimeMillis(); // You can use any unique identifier method here

        // Creates a Report object
        Report report = new Report(accused, description, location, timestamp, reportusername);

        // Gets a reference to the "reports" node in your Firebase database
        DatabaseReference reportsRef = FirebaseDatabase.getInstance().getReference("reports");

        // Pushse the report object to the Firebase database under "reports" node with the custom key
        reportsRef.child(reportKey).setValue(report);

        // Finishes the activity
        finish();
    }
    private String getCurrentTimestamp() {
        // Gets the current time in milliseconds
        long currentTimeMillis = System.currentTimeMillis();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
        Date currentDate = new Date(currentTimeMillis);
        return dateFormat.format(currentDate);
    }

}

