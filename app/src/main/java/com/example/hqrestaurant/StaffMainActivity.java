package com.example.hqrestaurant;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class StaffMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.staffmain);

        // Welcome message
        TextView welcomeText = findViewById(R.id.welcomeText);
        String staffName = getIntent().getStringExtra("staff_name");
        if (welcomeText != null && staffName != null && !staffName.trim().isEmpty()) {
            welcomeText.setText("Welcome, " + staffName);
        }

        Button viewReservations = findViewById(R.id.viewReservations);
        Button manageMenu = findViewById(R.id.manageMenu);
        Button settings = findViewById(R.id.settings);
        Button logout = findViewById(R.id.logout);

        if (viewReservations == null || manageMenu == null || settings == null || logout == null) {
            Toast.makeText(this, "Button IDs missing in staffmain.xml", Toast.LENGTH_LONG).show();
            return;
        }

        // ✅ STAFF: View ALL reservations (with delete)
        viewReservations.setOnClickListener(v ->
                startActivity(new Intent(
                        StaffMainActivity.this,
                        StaffReservationActivity.class
                ))
        );

        // Manage menu
        manageMenu.setOnClickListener(v ->
                startActivity(new Intent(
                        StaffMainActivity.this,
                        StaffManageMenuActivity.class
                ))
        );

        // Settings
        settings.setOnClickListener(v ->
                startActivity(new Intent(
                        StaffMainActivity.this,
                        StaffSettings.class
                ))
        );

        // Logout
        logout.setOnClickListener(v -> {
            startActivity(new Intent(
                    StaffMainActivity.this,
                    StaffLoginActivity.class
            ));
            finish();
        });
    }
}
