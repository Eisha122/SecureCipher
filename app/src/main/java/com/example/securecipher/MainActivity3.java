package com.example.securecipher;
import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

public class MainActivity3 extends AppCompatActivity {

    EditText etEncryptedPassword;
    EditText etDecryptionKey;
    EditText etReferenceName; // New EditText for reference name
    Button btnDecrypt;
    TextView tvOriginalPassword;

    // Declare DatabaseHelper instance
    private DatabaseHelper databaseHelper;

    // HashMap to store reference names and corresponding encrypted passwords
    HashMap<String, String> passwordMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        // Initialize views
        etEncryptedPassword = findViewById(R.id.etEncryptedPassword);
        etDecryptionKey = findViewById(R.id.etDecryptionKey);
        etReferenceName = findViewById(R.id.etReferenceName); // Initialize the new EditText
        btnDecrypt = findViewById(R.id.btnDecrypt);
        tvOriginalPassword = findViewById(R.id.tvOriginalPassword);

        // Initialize the DatabaseHelper instance
        databaseHelper = new DatabaseHelper(this);

        // Initialize the password map and add sample data (you can retrieve this from your database)
        passwordMap = new HashMap<>();
        passwordMap.put("ReferenceName1", "EncryptedPassword1");
        passwordMap.put("ReferenceName2", "EncryptedPassword2");
        // Add more reference names and corresponding passwords as needed

        // Set text change listener for the reference name EditText
        etReferenceName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                // Get the entered reference name
                String referenceName = editable.toString().trim();

                // Retrieve encrypted password from the database based on reference name
                String encryptedPassword = databaseHelper.getEncryptedPasswordByReferenceName(referenceName);

                // If the password exists, fill the corresponding encrypted password field
                if (encryptedPassword != null) {
                    etEncryptedPassword.setText(encryptedPassword);
                } else {
                    // If it doesn't exist, clear the encrypted password field
                    etEncryptedPassword.setText("");
                }
            }
        });

        // Set click listener for the "Decrypt" button
        btnDecrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the encrypted password, decryption key, and reference name entered by the user
                String encryptedPassword = etEncryptedPassword.getText().toString();
                String decryptionKey = etDecryptionKey.getText().toString();
                String referenceName = etReferenceName.getText().toString(); // Retrieve the reference name

                // Perform decryption using the provided algorithm
                String originalPassword = decrypt(encryptedPassword, Integer.parseInt(decryptionKey));

                // Display the original password along with the reference name
                tvOriginalPassword.setText("Reference Name: " + referenceName + "\nOriginal Password: " + originalPassword);
            }
        });
    }

    // Decryption algorithm
    public String decrypt(String encryptedMessage, int shiftKey) {
        // Define the alphabet
        String alpha = "abcdefghijklmnopqrstuvwxyz";

        // Initialize original text
        StringBuilder originalText = new StringBuilder();

        // Decrypt each character in the message
        for (int i = 0; i < encryptedMessage.length(); i++) {
            char c = encryptedMessage.charAt(i);
            // Find the position of the character in the alphabet
            int charPosition = alpha.indexOf(c);
            // Apply the shift key and modulus to wrap around the alphabet
            int keyVal = (charPosition - shiftKey) % 26;
            if (keyVal < 0) {
                keyVal = alpha.length() + keyVal; // Handle negative values
            }
            // Replace the character with the decrypted character
            char replaceVal = alpha.charAt(keyVal);
            originalText.append(replaceVal);
        }

        // Return the original message
        return originalText.toString();
    }
}