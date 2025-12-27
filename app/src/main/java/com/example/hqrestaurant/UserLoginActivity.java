package com.example.hqrestaurant;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class UserLoginActivity extends AppCompatActivity {

    private EditText usernameInput, passwordInput;
    private Button loginButton;
    private ImageView backButton;

    // TEMP user login (change later to database/firebase)
    private static final String USER_USER = "user1";
    private static final String USER_PASS = "1234";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_log); // ✅  user login XML file

        usernameInput = findViewById(R.id.usernameInput);
        passwordInput = findViewById(R.id.passwordInput);
        loginButton = findViewById(R.id.loginButton);

        // Only if you added a back arrow ImageView with id backButton
        backButton = findViewById(R.id.backButton);
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

            if (username.equals(USER_USER) && password.equals(USER_PASS)) {
                Intent i = new Intent(UserLoginActivity.this, UserHomeActivity.class);
                i.putExtra("username", username);
                startActivity(i);
                finish();
            } else {
                Toast.makeText(UserLoginActivity.this, "Invalid user login details", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
