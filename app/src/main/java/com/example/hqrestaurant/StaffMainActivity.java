// staff main page processes

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

        // ✅ IMPORTANT: use the layout file that contains the staff homepage buttons
        // You have staffmain.xml in res/layout, so:
        setContentView(R.layout.staffmain);

        // Welcome text (make sure this id exists in staffmain.xml)
        TextView welcomeText = findViewById(R.id.welcomeText);
        String staffName = getIntent().getStringExtra("staff_name");
        if (welcomeText != null && staffName != null && !staffName.trim().isEmpty()) {
            welcomeText.setText("Welcome, " + staffName);
        }

        Button viewReservations = findViewById(R.id.viewReservations);
        Button manageMenu = findViewById(R.id.manageMenu);
        Button settings = findViewById(R.id.settings);
        Button logout = findViewById(R.id.logout);

        if (manageMenu == null || settings == null || logout == null) {
            Toast.makeText(this, "IDs not found. Check staffmain.xml button ids.", Toast.LENGTH_LONG).show();
            return;
        }

        if (viewReservations != null) {
            viewReservations.setOnClickListener(v ->
                    Toast.makeText(this, "Reservations page coming soon", Toast.LENGTH_SHORT).show()
            );
        }

        // ✅ Open your existing pages
        manageMenu.setOnClickListener(v ->
                startActivity(new Intent(StaffMainActivity.this, StaffManageMenuActivity.class))
        );

        settings.setOnClickListener(v ->
                startActivity(new Intent(StaffMainActivity.this, StaffSettings.class))
        );

        logout.setOnClickListener(v -> {
            startActivity(new Intent(StaffMainActivity.this, StaffLoginActivity.class));
            finish();
        });
    }
}

