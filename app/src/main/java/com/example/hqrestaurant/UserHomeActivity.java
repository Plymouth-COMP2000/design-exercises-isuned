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

        // ✅ Make sure "Home" is selected on this page
        bottomNav.setSelectedItemId(R.id.guest_nav_home);

        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.guest_nav_home) {
                // ✅ Stay here (DO NOT open MainActivity)
                return true;
            } else if (id == R.id.guest_nav_reservations) {
                startActivity(new Intent(this, GuestReservationsActivity.class));
                return true;
            } else if (id == R.id.guest_nav_settings) {
                startActivity(new Intent(this, GuestSettingsActivity.class));
                return true;
            }

            return false;
        });
    }
}
