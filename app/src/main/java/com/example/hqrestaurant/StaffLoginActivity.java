package com.example.hqrestaurant;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class StaffLoginActivity extends AppCompatActivity {

    private EditText usernameInput, passwordInput;
    private Button loginButton;

    // TEMP credentials (change later)
    private static final String STAFF_USER = "nedu";
    private static final String STAFF_PASS = "123";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.staff); // <-- your staff login XML (staff.xml)

        usernameInput = findViewById(R.id.usernameInput);
        passwordInput = findViewById(R.id.passwordInput);
        loginButton   = findViewById(R.id.loginButton);

        // Back arrow (ONLY if your XML has an ImageView with id backButton)
        ImageView backButton = findViewById(R.id.backButton);
        if (backButton != null) {
            backButton.setOnClickListener(v -> finish());
        }

        loginButton.setOnClickListener(v -> {
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

            if (username.equals(STAFF_USER) && password.equals(STAFF_PASS)) {
                Intent i = new Intent(StaffLoginActivity.this, StaffMainActivity.class);
                i.putExtra("username", username); // optional
                startActivity(i);
                finish();
            } else {
                Toast.makeText(this, "Invalid staff login details", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
