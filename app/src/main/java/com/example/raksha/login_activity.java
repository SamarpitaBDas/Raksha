package com.example.raksha;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

public class login_activity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    FloatingActionButton fb,google,twitter;
    float v=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tabLayout= findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);
//        fb = findViewById(R.id.fab_fb);
//        google = findViewById(R.id.fab_google);
//        twitter = findViewById(R.id.fab_twitter);


        tabLayout.addTab((tabLayout.newTab().setText("Login")));
        tabLayout.addTab((tabLayout.newTab().setText("Signup")));
        tabLayout.setTabGravity(tabLayout.GRAVITY_FILL);

        final LoginAdapter adapter = new LoginAdapter(getSupportFragmentManager(),this, tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener((new TabLayout.TabLayoutOnPageChangeListener(tabLayout)));

//        fb.setTranslationY((300));
//        google.setTranslationY(300);
//        twitter.setTranslationY(300);
        tabLayout.setTranslationY(300);

//        fb.setAlpha(v);
//        twitter.setAlpha(v);
//        google.setAlpha(v);
        tabLayout.setAlpha(v);

//        fb.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(400).start();
//        twitter.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(600).start();
//        google.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(800).start();

    }

}
