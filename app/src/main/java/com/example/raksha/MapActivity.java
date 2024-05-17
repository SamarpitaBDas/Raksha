package com.example.raksha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

//TODO create query for map activity
//correct logic but incorrect code
public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private List<LatLng> emergencyCallLocations; // List to store emergency call locations

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        // Retrieve emergency call locations from the database
        retrieveEmergencyCallLocations();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void retrieveEmergencyCallLocations() {
        // Fetch emergency call locations from the database and populate the list
        // Implement your database retrieval logic here
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add markers for each emergency call location
        if (emergencyCallLocations != null) {
            for (LatLng location : emergencyCallLocations) {
                mMap.addMarker(new MarkerOptions().position(location).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            }
        }

        // Zoom and center the map camera to fit all markers
        zoomAndCenterMap();
    }

    private void zoomAndCenterMap() {
        // Create bounds builder to include all markers
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        if (emergencyCallLocations != null) {
            for (LatLng location : emergencyCallLocations) {
                builder.include(location);
            }
        }

        // Set camera position to fit all markers
        LatLngBounds bounds = builder.build();
        int padding = 50; // Padding around markers in pixels
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, padding);
        mMap.moveCamera(cameraUpdate);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

    }
}

