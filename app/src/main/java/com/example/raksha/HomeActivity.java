package com.example.raksha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends AppCompatActivity {

    private final int FINE_PERMISSION_CODE = 1;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private DatabaseReference locationRef;

    private DatabaseReference emergencyCallsRef;
    private TextView emergencyCallsCountTextView;

    private DatabaseReference reportsRef;
    private TextView reportsCountTextView;

    private DatabaseReference casesRef;
    private TextView casesCountTextView;

    public String homeusername;
    private double latitude;
    private double longitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

//        // Retrieve username and password from intent
//        Intent intent = getIntent();
//
//        homeusername = intent.getStringExtra("username");
//        homepassword = intent.getStringExtra("password");
//        Log.d("LoginActivity", "Username: " + homeusername);
//        Log.d("LoginActivity", "password: " + homepassword);


        // Initialize Firebase Database reference
        emergencyCallsRef = FirebaseDatabase.getInstance().getReference("emergency_calls");

        // Initialize TextView for emergency calls count
        emergencyCallsCountTextView = findViewById(R.id.emergencyCallsCountTextView);

        // Retrieve emergency calls count from Firebase
        retrieveEmergencyCallsCount();


        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        locationRef = FirebaseDatabase.getInstance().getReference("users");

        getLastLocation();

        // Initialize Firebase Database reference for reports
        reportsRef = FirebaseDatabase.getInstance().getReference("reports");

        // Initialize TextView for reports count
        reportsCountTextView = findViewById(R.id.reportsCountTextView);

        // Retrieve reports count from Firebase
        retrieveReportsCount();

        casesRef = FirebaseDatabase.getInstance().getReference("cases");
        casesCountTextView = findViewById(R.id.casesCountTextView);
        retrieveCasesCount();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                // Retrieve username and password from intent
                Intent intent = getIntent();

                homeusername = intent.getStringExtra("username");
                Log.d("LoginActivity", "Username: " + homeusername);
                if (itemId == R.id.emergency) {
                    Log.d("LoginActivity", "Username: " + homeusername);
                    startActivity(new Intent(HomeActivity.this, EmergencyActivity.class)
                            .putExtra("username", homeusername));
                    return true;
                } else if (itemId == R.id.report) {
                    Log.d("LoginActivity", "Username: " + homeusername);
                    startActivity(new Intent(HomeActivity.this, ReportActivity.class)
                            .putExtra("username", homeusername));
                    return true;
                } else if (itemId == R.id.community) {
                    Log.d("LoginActivity", "Username: " + homeusername);
                    startActivity(new Intent(HomeActivity.this, CommunityActivity.class)
                            .putExtra("username", homeusername));
                    return true;
                }else if(itemId == R.id.map){
                    Log.d("LoginActivity","username" + homeusername);
                    startActivity(new Intent(HomeActivity.this, MapActivity.class)
                            .putExtra("username", homeusername));
                    return true;
                }
                else {
                    return false;
                }

//                switch (item.getItemId()) {
//                    case R.id.home:
//                        // Handles Home menu item click
//                        startActivity(new Intent(HomeActivity.this, HomeActivity.class));
//                        return true;
//                    case R.id.emergency:
//                        // Handles Emergency menu item click
//                        startActivity(new Intent(HomeActivity.this, EmergencyActivity.class));
//                        return true;
//                    case R.id.report:
//                        // Handles Report menu item click
//                        startActivity(new Intent(HomeActivity.this, ReportActivity.class));
//                        return true;
//                    case R.id.community:
//                        // Handles Community menu item click
//                        startActivity(new Intent(HomeActivity.this, CommunityActivity.class));
//                        return true;
//                    default:
//                        return false;
//                }
            }
        });
    }

    private void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Request location permission if not granted
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, FINE_PERMISSION_CODE);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                if (task.isSuccessful() && task.getResult() != null) {
                    Location location = task.getResult();
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                    Log.e("Location", "Longitude: " + longitude + ", Latitude: " + latitude);
                    // Save location data to Firebase Realtime Database
                    saveLocationToDatabase(latitude, longitude);
                } else {
                    Toast.makeText(HomeActivity.this, "Unable to get location", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void saveLocationToDatabase(double latitude, double longitude) {
        // Retrieve username from Intent extra
        String username = getIntent().getStringExtra("username");

        // Check if the username is not null
        if (username != null) {
            // Update the user's data in the database
            DatabaseReference userRef = locationRef.child(username);
            userRef.child("latitude").setValue(latitude);
            userRef.child("longitude").setValue(longitude);
            Toast.makeText(this, "Location saved to database", Toast.LENGTH_SHORT).show();

            // Start EmergencyActivity and pass username, password, and location
            Intent intent = new Intent(HomeActivity.this, EmergencyActivity.class);
            intent.putExtra("username", username);
            intent.putExtra("latitude", latitude);
            intent.putExtra("longitude", longitude);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Username not found", Toast.LENGTH_SHORT).show();
        }
    }

    // Handle the result of the location permission request
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == FINE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            } else {
                Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void retrieveEmergencyCallsCount() {
        emergencyCallsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Get total number of emergency calls
                long emergencyCallsCount = dataSnapshot.getChildrenCount();

                // Update TextView with the count
                emergencyCallsCountTextView.setText(String.valueOf(emergencyCallsCount));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
                Toast.makeText(HomeActivity.this, "Failed to retrieve emergency calls count", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void retrieveReportsCount() {
        reportsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Get total number of reports
                long reportsCount = dataSnapshot.getChildrenCount();

                // Update TextView with the count
                reportsCountTextView.setText(String.valueOf(reportsCount));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
                Toast.makeText(HomeActivity.this, "Failed to retrieve reports count", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void retrieveCasesCount() {
        casesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Get total number of cases
                long casesCount = dataSnapshot.getChildrenCount();

                // Update TextView with the count
                casesCountTextView.setText(String.valueOf(casesCount));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
                Toast.makeText(HomeActivity.this, "Failed to retrieve cases count", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void addDescriptionToCardView(TextView textView, String description) {
        if (description != null) {
            textView.setVisibility(View.VISIBLE);
            textView.setText(description);
        } else {
            textView.setVisibility(View.GONE);
        }
    }
}
