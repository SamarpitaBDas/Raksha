package com.example.raksha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class CommunityActivity extends AppCompatActivity {
    public String communityusername;
    private TextView usernameTextView, timestampTextView, postContentTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);

        //initializing textview
        usernameTextView = findViewById(R.id.usernameTextView);
        timestampTextView = findViewById(R.id.timestampTextView);
        postContentTextView = findViewById(R.id.postContentTextView);

        //retriving username from intent
        Intent intent = getIntent();
        communityusername = intent.getStringExtra("username");

        //fetching users post content data from database
        fetchUserContentFromDatabase(communityusername);


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                Intent intent = getIntent();

                communityusername = intent.getStringExtra("username");
//                Log.d("LoginActivity", "Username: " + communityusername);
                if (itemId == R.id.home){
                    Intent communityIntent = new Intent(CommunityActivity.this, HomeActivity.class);
                    communityIntent.putExtra("username", communityusername);
                    communityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(communityIntent);
                    return true;
                } else if (itemId == R.id.emergency) {

//                    Log.d("LoginActivity", "Username: " + communityusername);
                    startActivity(new Intent(CommunityActivity.this, EmergencyActivity.class)
                            .putExtra("username", communityusername));
                    return true;
                } else if (itemId == R.id.report) {

//                    Log.d("LoginActivity", "Username: " + communityusername);
                    startActivity(new Intent(CommunityActivity.this, ReportActivity.class)
                            .putExtra("username", communityusername));
                    return true;
                }else {
                    return false;
                }

            }
        });
    }

    private void fetchUserContentFromDatabase(String communityusername) {
        FirebaseDatabase.getInstance().getReference("posts")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            String username = postSnapshot.child("username").getValue(String.class);
                            String description = postSnapshot.child("description").getValue(String.class);
                            String timerstamp = postSnapshot.child("timestamp").getValue(String.class);

                            if (username != null && description != null) {
                                usernameTextView.append(username);
                                postContentTextView.append(description + "\n\n");
                                timestampTextView.append(timerstamp );
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Handle error
                        Log.e("Firebase", "Error fetching user content: " + error.getMessage());
                    }
                });
    }

//    TODO create community tab to store the posts etc
}
