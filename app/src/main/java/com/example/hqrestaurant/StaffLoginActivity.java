package com.example.hqrestaurant;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class StaffLoginActivity extends AppCompatActivity {

    private EditText usernameInput, passwordInput;
    private Button loginButton;

    // TEMP credentials (change later)
    private static final String STAFF_USER = "nedu";
    private static final String STAFF_PASS = "1234";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.staff); // staff login XML

        usernameInput = findViewById(R.id.usernameInput);
        passwordInput = findViewById(R.id.passwordInput);
        loginButton = findViewById(R.id.loginButton);

        // Back arrow (works for ImageView or ImageButton)
        View backButton = findViewById(R.id.backButton);
        if (backButton != null) {
            backButton.setOnClickListener(v -> finish());
        }

        loginButton.setOnClickListener(v -> attemptLogin());
    }

    private void attemptLogin() {
        String username = usernameInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        // Validate input
        if (TextUtils.isEmpty(username)) {
            usernameInput.setError("Username required");
            usernameInput.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            passwordInput.setError("Password required");
            passwordInput.requestFocus();
            return;
        }

        // Check credentials
        if (username.equals(STAFF_USER) && password.equals(STAFF_PASS)) {
            Intent intent = new Intent(StaffLoginActivity.this, StaffMainActivity.class);
            intent.putExtra("staff_name", username); // show on staff homepage
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Invalid staff username or password", Toast.LENGTH_SHORT).show();
        }
    }
}

