
// staff login process
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

    private StaffDbHelper staffDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.staff); // your staff login XML

        usernameInput = findViewById(R.id.usernameInput);
        passwordInput = findViewById(R.id.passwordInput);
        loginButton   = findViewById(R.id.loginButton);

        // Back arrow
        View backButton = findViewById(R.id.backButton);
        if (backButton != null) backButton.setOnClickListener(v -> finish());

        // Init local DB (this will create DB + seed staff users on first run)
        staffDb = new StaffDbHelper(this);

        loginButton.setOnClickListener(v -> attemptLogin());
    }

    private void attemptLogin() {
        String username = usernameInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

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

        // Check local SQLite staff table
        String fullname = staffDb.loginStaff(username, password);

        if (fullname != null) {
            Intent intent = new Intent(StaffLoginActivity.this, StaffMainActivity.class);
            intent.putExtra("staff_name", fullname);
            intent.putExtra("staff_username", username);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Invalid staff username or password", Toast.LENGTH_SHORT).show();
        }
    }
}

