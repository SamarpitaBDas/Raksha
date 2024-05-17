package com.example.raksha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EmergencyActivity extends AppCompatActivity {

    private Map<String, Location> facultyLocations = new HashMap<>();
    private DatabaseReference databaseReference;

    //TODO logic for emergency calling the nearest faculty

    //TODO loginpage-error, intents handle , white navbar, call , emergency nearest faculty, community tab beautify create

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


        // Initialize Firebase Realtime Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");

        // Populate faculty locations from Firebase Realtime Database
        populateFacultyLocations();

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
                Location currentUserLocation = getCurrentUserLocation();
                if (currentUserLocation != null) {
                    String nearestFaculty = findNearestFaculty(currentUserLocation);
                    if (nearestFaculty != null) {
                        makePhoneCall(nearestFaculty);
                    } else {
                        Toast.makeText(EmergencyActivity.this, "No faculty found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(EmergencyActivity.this, "Unable to retrieve user location", Toast.LENGTH_SHORT).show();
                }
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

    // Method to populate facultyLocations map with faculty members' locations
    // Method to populate facultyLocations map with faculty members' locations
    private void populateFacultyLocations() {
        // Add listener to retrieve data from Firebase Realtime Database
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    // Get user data
                    Map<String, Object> userData = (Map<String, Object>) userSnapshot.getValue();
                    if (userData != null && "faculty".equals(userData.get("role"))) {
                        String username = userSnapshot.getKey();
                        double latitude = (double) userData.get("latitude");
                        double longitude = (double) userData.get("longitude");
                        // Add the faculty member's location to the map
                        facultyLocations.put(username, new Location(username) {{
                            setLatitude(latitude);
                            setLongitude(longitude);
                        }});
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
                Toast.makeText(EmergencyActivity.this, "Failed to retrieve data: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    // Method to get current user location
    private Location getCurrentUserLocation() {
        // Implement logic to retrieve current user location
        // You can use LocationManager or other location APIs
        // For demonstration purposes, returning a mock location
        Location mockLocation = new Location("mock");
        mockLocation.setLatitude(28.659);
        mockLocation.setLongitude(77.340);
        return mockLocation;
    }

    // Method to find the nearest faculty member
    private String findNearestFaculty(Location currentUserLocation) {
        String nearestFaculty = null;
        float minDistance = Float.MAX_VALUE;

        // Iterate through facultyLocations map to find the nearest faculty
        for (Map.Entry<String, Location> entry : facultyLocations.entrySet()) {
            Location facultyLocation = entry.getValue();
            float distance = currentUserLocation.distanceTo(facultyLocation);
            if (distance < minDistance) {
                minDistance = distance;
                nearestFaculty = entry.getKey();
            }
        }

        return nearestFaculty;
    }
}

