package com.example.raksha;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import android.widget.Toast;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class SignuptabActivity extends AppCompatActivity {
    EditText signupPhoneNumber, signupUsername, signupEmail, signupPassword;
    Button signupButton;
    FirebaseDatabase database;
    TextView loginRedirectText;
    DatabaseReference reference;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_tab_fragment);

        signupPhoneNumber = findViewById(R.id.signupphone_number);
        signupEmail = findViewById(R.id.signupEmail);
        signupUsername = findViewById(R.id.signupUsername);
        signupPassword = findViewById(R.id.signupPassword);
        signupButton = findViewById(R.id.signupbutton);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database = FirebaseDatabase.getInstance();
                reference = database.getReference("users");
                String name = signupUsername.getText().toString();
                String email = signupEmail.getText().toString();
                String username = signupUsername.getText().toString();
                String password = signupPassword.getText().toString();
                HelperClass helperClass = new HelperClass(name, email, username, password);
                reference.child(username).setValue(helperClass);
                Toast.makeText(SignuptabActivity.this, "You have signup successfully!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SignuptabActivity.this, login_activity.class);
                startActivity(intent);
            }
        });
        loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignuptabActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        TextInputLayout textInputLayout = findViewById(R.id.inputLayout);
        MaterialAutoCompleteTextView autoCompleteTextView = findViewById(R.id.signupinputTV);
        MaterialButton button = findViewById(R.id.signupbutton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (autoCompleteTextView.getText().toString().isEmpty()) {
                    textInputLayout.setError("Select an option");
                } else {
                    Toast.makeText(SignuptabActivity.this, autoCompleteTextView.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



}
