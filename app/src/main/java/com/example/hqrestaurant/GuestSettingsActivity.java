package com.example.hqrestaurant;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GuestSettingsActivity extends AppCompatActivity {

    private TextView nameValue, emailValue;
    private Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guest_settings);

        View backButton = findViewById(R.id.backButton);
        if (backButton != null) backButton.setOnClickListener(v -> finish());

        nameValue = findViewById(R.id.nameValue);
        emailValue = findViewById(R.id.emailValue);
        logoutButton = findViewById(R.id.logoutButton);

        // ✅ Load logged-in user info from SharedPreferences
        SharedPreferences sp = getSharedPreferences("session", MODE_PRIVATE);
        String username = sp.getString("username", "Guest");
        String email = sp.getString("email", "guest@email.com");

        if (nameValue != null) nameValue.setText(username);
        if (emailValue != null) emailValue.setText(email);

        // ✅ Logout clears session and returns to login screen
        if (logoutButton != null) {
            logoutButton.setOnClickListener(v -> {
                sp.edit().clear().apply();

                Intent i = new Intent(GuestSettingsActivity.this, UserLoginActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                finish();
            });
        }
    }
}
