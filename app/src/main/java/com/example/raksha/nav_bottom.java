package com.example.raksha;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.raksha.databinding.ActivityMainBinding;

public class nav_bottom extends AppCompatActivity {
//
//    ActivityMainBinding binding;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        binding = ActivityMainBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//
//        replaceFragment(new HomeFragment());
//        binding.BottomNavigationView.setBackground(null);
//
//        binding.BottomNavigationView.setOnItemSelectedListener(item -> {
//
//            switch (item.getItemId()) {
//                case R.id.home:
//                    replaceFragment(new HomeFragment());
//                    break;
//
//                case R.id.emergency:
//                    replaceFragment(new EmergencyFragment());
//                    break;
//
//                case R.id.community:
//                    replaceFragment(new CommunityFragment());
//                    break;
//
//                case R.id.report:
//                    replaceFragment(new ReportFragment());
//                    break;
//            }
//
//            return true;
//
//        });
//
//    }
//
//    private void replaceFragment(Fragment fragment) {
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.frame_layout, fragment);
//        fragmentTransaction.commit();
//    }
}
