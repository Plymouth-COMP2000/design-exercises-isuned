package com.example.hqrestaurant;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class UserHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userhome);

        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView);

        // Home selected
        bottomNav.setSelectedItemId(R.id.guest_nav_home);

        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.guest_nav_home) {
                return true; // stay here
            }

            if (id == R.id.guest_nav_reservations) {
                startActivity(new Intent(UserHomeActivity.this, GuestReservationsActivity.class));
                return true;
            }

            if (id == R.id.guest_nav_settings) {
                startActivity(new Intent(UserHomeActivity.this, GuestSettingsActivity.class));
                return true;
            }

            return false;
        });
    }

    // When you come back to home, always re-select Home
    @Override
    protected void onResume() {
        super.onResume();
        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView);
        if (bottomNav != null) bottomNav.setSelectedItemId(R.id.guest_nav_home);
    }
}
