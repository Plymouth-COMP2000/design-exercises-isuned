// user home page

package com.example.hqrestaurant;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class UserHomeActivity extends AppCompatActivity {

    private BottomNavigationView bottomNav;
    private RecyclerView menuRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userhome);

        // Welcome text
        TextView welcomeText = findViewById(R.id.welcomeText);
        if (welcomeText != null) {
            String username = getIntent().getStringExtra("username");
            if (username == null || username.isEmpty()) username = "Guest";
            welcomeText.setText("Welcome, " + username);
        }

        // RecyclerView
        menuRecycler = findViewById(R.id.menuRecycler);
        menuRecycler.setLayoutManager(new GridLayoutManager(this, 1)); // 1 item per row (like your screenshot)

        // Load from SQLite
        MenuDbHelper db = new MenuDbHelper(this);
        List<MenuItem> items = db.getAllMenuItems(this);

        MenuAdapter adapter = new MenuAdapter(items);
        menuRecycler.setAdapter(adapter);

        // Bottom Navigation
        bottomNav = findViewById(R.id.bottomNavigationView);
        bottomNav.setSelectedItemId(R.id.guest_nav_home);

        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.guest_nav_home) return true;

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

    @Override
    protected void onResume() {
        super.onResume();
        if (bottomNav != null) bottomNav.setSelectedItemId(R.id.guest_nav_home);
    }
}

