package com.example.raksha;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    String[] roles = {"student","faculty","others"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create an array adapter using the string array "role"
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.dropdown_item,roles);

        // Inflate the layout containing AutoCompleteTextView
        View signUpTabFragment = getLayoutInflater().inflate(R.layout.sign_up_tab_fragment, null);

        // Get reference to the AutoCompleteTextView within the inflated layout
        AutoCompleteTextView autoCompleteTextView = signUpTabFragment.findViewById(R.id.autoCompleteTextView);

        // Set adapter to the autoCompleteTextView and specify the dropdown item layout
        autoCompleteTextView.setAdapter(arrayAdapter);
        autoCompleteTextView.setDropDownBackgroundResource(R.color.white); // Optional: Set background color of dropdown

        // Ensure the dropdown list appears below the AutoCompleteTextView
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(MainActivity.this,"role" + roles,Toast.LENGTH_SHORT).show();
            }
        });
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
