package com.example.raksha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {
    EditText signupPhoneNumber, signupUsername, signupEmail, signupPassword,signupinput;
    Button signupButton;
    FirebaseDatabase database;
    TextView loginRedirectText; // This was missing initialization
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_tab_fragment);

        signupPhoneNumber = findViewById(R.id.signupphone_number);
        signupEmail = findViewById(R.id.signupEmail);
        signupUsername = findViewById(R.id.signupUsername);
        signupPassword = findViewById(R.id.signupPassword);
        signupButton = findViewById(R.id.signupbutton);
        loginRedirectText = findViewById(R.id.loginRedirectText); // Initialize loginRedirectText
        signupinput = findViewById(R.id.signupinputTV);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database = FirebaseDatabase.getInstance();
                reference = database.getReference("users");
                String name = signupUsername.getText().toString();
                String email = signupEmail.getText().toString();
                String username = signupUsername.getText().toString();
                String password = signupPassword.getText().toString();
                String role = signupinput.getText().toString(); // Get the selected role
                HelperClass helperClass = new HelperClass(name, email, username, password, role);
                reference.child(username).setValue(helperClass);
                Toast.makeText(SignupActivity.this, "You have signed up successfully!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });



        loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        // Correct initialization for TextInputLayout and MaterialAutoCompleteTextView
        TextInputLayout textInputLayout = findViewById(R.id.inputLayout);
        MaterialAutoCompleteTextView autoCompleteTextView = findViewById(R.id.signupinputTV);
        MaterialButton button = findViewById(R.id.signupbutton);
    }
}
