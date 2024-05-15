package com.example.raksha;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.Toast;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputLayout;



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

    public void HomeActivity(View view){
        Intent i = new Intent(this, HomeActivity.class);
        Bundle b= ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
        startActivity(i);
    }
}
