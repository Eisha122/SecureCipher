package com.example.securecipher;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MainActivity4 extends AppCompatActivity {

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        databaseHelper = new DatabaseHelper(this);

        // Retrieve saved details from the database
        ArrayList<String> savedDetails = databaseHelper.getAllSavedDetails();

        // Get the TableLayout to display the details
        TableLayout tableLayout = findViewById(R.id.tableLayout);

        // Display saved details in table format
        for (String detail : savedDetails) {
            // Create a new row for each detail
            TableRow tableRow = new TableRow(this);
            TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            tableRow.setLayoutParams(layoutParams);

            // Create TextView to display detail in each column
            TextView textView = new TextView(this);
            textView.setText(detail);
            tableRow.addView(textView);

            // Add the row to the table layout
            tableLayout.addView(tableRow);
        }
    }
}