package com.example.raksha;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.raksha.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
//TODO the white circle appearing on the home
public class nav_bottom extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }

}

