package com.example.securecipher;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {

    EditText etUsername, etPassword;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        dbHelper = new DatabaseHelper(this);
    }

    public void loginUser(View view) {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE username=? AND password=?", new String[]{username, password});

        if (cursor.moveToFirst()) {
            // Login successful
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            // Login failed
            Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show();
        }

        cursor.close();
    }

    public void goToRegister(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}