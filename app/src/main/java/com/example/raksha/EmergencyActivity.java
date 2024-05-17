package com.example.raksha;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

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
import android.util.Log;
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

//TODO storing total number of emergency calls recieved and the location of the emergency call recieved in the database

public class EmergencyActivity extends AppCompatActivity {

    private Map<String, Location> facultyLocations = new HashMap<>();
    private DatabaseReference databaseReference;

    public static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;

    public String emergencyusername;
    Button faculty_call;
    Button call_police;
    Button call_hospital;
    Button call_womenhp;
    Button call_womencommision;
    Button call_women_honor_call;
    private double latitude_em;
    private double longitude_em;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);

        // Initialize buttons
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

        // Retrieve username from intent
        Intent intent = getIntent();
        emergencyusername = intent.getStringExtra("username");

        // Fetch user's location data from database
        fetchUserLocationFromDatabase(emergencyusername);

        // Set up bottom navigation view listener
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                Intent intent = getIntent();

                emergencyusername = intent.getStringExtra("username");
                if (itemId == R.id.home) {
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

        // Set click listener for faculty call button
        faculty_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Location currentUserLocation = getCurrentUserLocation(latitude_em, longitude_em);
                if (currentUserLocation != null) {
                    String nearestFaculty = findNearestFaculty(currentUserLocation);
                    if (nearestFaculty != null) {
                        getfacultynumber(nearestFaculty, new FacultyNumberCallback() {
                            @Override
                            public void onCallback(String phoneNumber) {
                                if (phoneNumber != null) {
                                    makePhoneCall(phoneNumber);
                                } else {
                                    Toast.makeText(EmergencyActivity.this, "Unable to retrieve faculty phone number", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } else {
                        Toast.makeText(EmergencyActivity.this, "No faculty found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(EmergencyActivity.this, "Unable to retrieve user location", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Set click listeners for other emergency call buttons
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

    // Method to retrieve the nearest faculty phone number
    private void getfacultynumber(String nearestFaculty, FacultyNumberCallback callback) {
        FirebaseDatabase.getInstance().getReference("users").child(nearestFaculty)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            // Retrieve user's phone number
                            String phoneNumber = dataSnapshot.child("phone").getValue(String.class);
                            callback.onCallback(phoneNumber);
                            Log.d(TAG, "Faculty phone number retrieved: " + phoneNumber);
                        } else {
                            callback.onCallback(null); // User data doesn't exist
                            Log.d(TAG, "No faculty data found for: " + nearestFaculty);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        callback.onCallback(null); // Handle cancellation or error
                        Log.d(TAG, "Error retrieving faculty data: " + databaseError.getMessage());
                    }
                });
    }

    // Method to make a phone call
    private void makePhoneCall(String phoneNumber) {
        if (ContextCompat.checkSelfPermission(EmergencyActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(EmergencyActivity.this, new String[]{Manifest.permission.CALL_PHONE}, MY_PERMISSIONS_REQUEST_CALL_PHONE);
        } else {
            String dial = "tel:" + phoneNumber;
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            Log.d(TAG, "Making phone call to: " + phoneNumber);
        }
    }

    // Handle call permission request result
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSIONS_REQUEST_CALL_PHONE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, you can make the phone call
                Log.d(TAG, "CALL_PHONE permission granted.");
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "CALL_PHONE permission denied.");
            }
        }
    }

    // Fetch user's location data from the database
    private void fetchUserLocationFromDatabase(String username) {
        FirebaseDatabase.getInstance().getReference("users").child(username)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            // Retrieve user's location data
                            latitude_em = dataSnapshot.child("latitude").getValue(Double.class);
                            longitude_em = dataSnapshot.child("longitude").getValue(Double.class);
                            Log.d(TAG, "User location retrieved: Latitude = " + latitude_em + ", Longitude = " + longitude_em);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle error
                        Log.d(TAG, "Error retrieving user location: " + databaseError.getMessage());
                    }
                });
    }

    // Populate faculty locations from Firebase Realtime Database
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
                        Log.d(TAG, "Faculty location added: Username = " + username + ", Latitude = " + latitude + ", Longitude = " + longitude);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
                Toast.makeText(EmergencyActivity.this, "Failed to retrieve data: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Error retrieving faculty locations: " + databaseError.getMessage());
            }
        });
    }

    // Method to get the current user's location
    private Location getCurrentUserLocation(double latitude, double longitude) {
        Location mockLocation = new Location("mock");
        mockLocation.setLatitude(latitude);
        mockLocation.setLongitude(longitude);
        Log.d(TAG, "Current user location: Latitude = " + latitude + ", Longitude = " + longitude);
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
        Log.d(TAG, "Nearest faculty: " + nearestFaculty);
        return nearestFaculty;
    }

    // Interface for the faculty number callback
    public interface FacultyNumberCallback {
        void onCallback(String phoneNumber);
    }
}
