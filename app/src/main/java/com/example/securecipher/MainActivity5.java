package com.example.securecipher;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity5 extends AppCompatActivity {

    EditText etEmail, etRefName;
    Button btnSave;
    DatabaseHelper databaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

        databaseHelper = new DatabaseHelper(this);
        etEmail = findViewById(R.id.etEmail);
        etRefName = findViewById(R.id.etRefName);
        btnSave = findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etEmail.getText().toString();
                String refName = etRefName.getText().toString();
                String encryptedPassword = getIntent().getStringExtra("ENCRYPTED_PASSWORD_EXTRA");

                if (email.isEmpty() || refName.isEmpty()) {
                    Toast.makeText(MainActivity5.this, "Please fill in both Email and Reference Name", Toast.LENGTH_SHORT).show();
                } else {
                    // Save the details to the database
                    long result = databaseHelper.insertDetails(encryptedPassword, email, refName);
                    if (result != -1) {
                        Toast.makeText(MainActivity5.this, "Details saved successfully", Toast.LENGTH_SHORT).show();
                        // Clear input fields after successful save if needed
                        etEmail.setText("");
                        etRefName.setText("");
                    } else {
                        Toast.makeText(MainActivity5.this, "Failed to save details", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}