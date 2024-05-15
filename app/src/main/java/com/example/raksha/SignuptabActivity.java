package com.example.raksha;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import android.widget.Toast;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputLayout;
public class SignuptabActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_tab_fragment);

        TextInputLayout textInputLayout = findViewById(R.id.inputLayout);
        MaterialAutoCompleteTextView autoCompleteTextView = findViewById(R.id.inputTV);
        MaterialButton button = findViewById(R.id.button);

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
