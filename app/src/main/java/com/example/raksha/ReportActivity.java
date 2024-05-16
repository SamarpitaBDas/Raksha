package com.example.raksha;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ReportActivity extends AppCompatActivity {

    private EditText locationEditText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        // Initialize EditText
        locationEditText = findViewById(R.id.locationEditText);

        // Retrieve location data from intent
        Intent intent = getIntent();
        if (intent != null) {
            double latitude = intent.getDoubleExtra("latitude", 0.0);
            double longitude = intent.getDoubleExtra("longitude", 0.0);

            // Set location text
            locationEditText.setText(String.format("Latitude: %f, Longitude: %f", latitude, longitude));
        }
    }
}

