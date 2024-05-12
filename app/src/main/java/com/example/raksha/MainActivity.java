package com.example.raksha;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void login_activity(View view){
        Intent i = new Intent(this, login_activity.class);
        Bundle b= ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
        startActivity(i);

    }
}
