package com.example.hqrestaurant;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class UserHomeActivity extends AppCompatActivity {

    private static final int REQ_POST_NOTIFICATIONS = 1001;

    private BottomNavigationView bottomNav;
    private RecyclerView menuRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userhome);

        // ✅ Ask notification permission (Android 13+)
        requestNotificationPermission();

        // Welcome text
        TextView welcomeText = findViewById(R.id.welcomeText);
        if (welcomeText != null) {
            String username = getIntent().getStringExtra("username");
            if (username == null || username.isEmpty()) username = "Guest";
            welcomeText.setText("Welcome, " + username);
        }

        // RecyclerView
        menuRecycler = findViewById(R.id.menuRecycler);
        menuRecycler.setLayoutManager(new GridLayoutManager(this, 1));

        // Load menu from SQLite
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
                startActivity(new Intent(this, GuestReservationsActivity.class));
                return true;
            }

            if (id == R.id.guest_nav_settings) {
                startActivity(new Intent(this, GuestSettingsActivity.class));
                return true;
            }

            return false;
        });
    }

    // -------------------------
    // Notification permission
    // -------------------------
    private void requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(
                        this,
                        new String[]{Manifest.permission.POST_NOTIFICATIONS},
                        REQ_POST_NOTIFICATIONS
                );
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (bottomNav != null) bottomNav.setSelectedItemId(R.id.guest_nav_home);
    }
}


