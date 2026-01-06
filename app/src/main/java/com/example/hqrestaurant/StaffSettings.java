package com.example.hqrestaurant;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class StaffSettings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.staff_settings);

        // Back button
        View backButton = findViewById(R.id.backButton);
        if (backButton != null) {
            backButton.setOnClickListener(v -> finish());
        }

        // Logout button
        Button logoutButton = findViewById(R.id.logoutButton);
        if (logoutButton != null) {
            logoutButton.setOnClickListener(v -> logoutStaff());
        }
    }

    private void logoutStaff() {
        // Clear saved session / login info
        SharedPreferences prefs =
                getSharedPreferences("HQRestaurantPrefs", MODE_PRIVATE);
        prefs.edit().clear().apply();

        // Go back to login screen
        Intent intent = new Intent(
                StaffSettings.this,
                UserLoginActivity.class // change if staff uses different login
        );
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

        finish();
    }
}


