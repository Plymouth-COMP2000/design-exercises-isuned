package com.example.hqrestaurant;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class GuestSettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guest_settings);

        // Back arrow
        View backButton = findViewById(R.id.backButton);
        if (backButton != null) {
            backButton.setOnClickListener(v -> finish());
        }

        // OPTIONAL: hook buttons if you want later
        // View logoutButton = findViewById(R.id.logoutButton);
        // View resetPasswordButton = findViewById(R.id.resetPasswordButton);
    }
}


