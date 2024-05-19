package com.example.raksha;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    public String mapusername;
    private List<LatLng> emergencyCallLocations = new ArrayList<>(); // List to store emergency call locations

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        //retriving username from intent
        Intent intent = getIntent();
        mapusername = intent.getStringExtra("username");


        // Seting up bottom navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.home) {
                    Intent communityIntent = new Intent(MapActivity.this, HomeActivity.class);
                    communityIntent.putExtra("username", mapusername);
                    communityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(communityIntent);
                    return true;
                } else if (itemId == R.id.emergency) {
                    startActivity(new Intent(MapActivity.this, EmergencyActivity.class)
                            .putExtra("username", mapusername));
                    return true;
                } else if (itemId == R.id.community) {
                    startActivity(new Intent(MapActivity.this, CommunityActivity.class)
                            .putExtra("username", mapusername));
                    return true;
                }  else if (itemId == R.id.report) {
                    startActivity(new Intent(MapActivity.this, ReportActivity.class)
                            .putExtra("username", mapusername));
                    return true;
                } else {
                    return false;
                }
            }
        });

        // Retrieve emergency call locations from the database
        retrieveEmergencyCallLocations();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void retrieveEmergencyCallLocations() {
        FirebaseDatabase.getInstance().getReference("emergency_calls")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            // Get latitude and longitude from each emergency call
                            double latitude = snapshot.child("latitude").getValue(Double.class);
                            double longitude = snapshot.child("longitude").getValue(Double.class);
                            LatLng location = new LatLng(latitude, longitude);
                            emergencyCallLocations.add(location);
                        }
                        // Once all locations are retrieved, update the map
                        if (mMap != null) {
                            updateMap();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle error
                    }
                });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // If emergency call locations are already retrieved, update the map
        if (!emergencyCallLocations.isEmpty()) {
            updateMap();
        }
    }

    private void updateMap() {
        // Add markers for each emergency call location
        for (LatLng location : emergencyCallLocations) {
            mMap.addMarker(new MarkerOptions().position(location).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        }

        // Zoom and center the map camera to fit all markers
        zoomAndCenterMap();
    }

    private void zoomAndCenterMap() {
        // Create bounds builder to include all markers
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (LatLng location : emergencyCallLocations) {
            builder.include(location);
        }

        // Set camera position to fit all markers
        LatLngBounds bounds = builder.build();
        int padding = 50; // Padding around markers in pixels
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, padding);
        mMap.moveCamera(cameraUpdate);
    }
}

