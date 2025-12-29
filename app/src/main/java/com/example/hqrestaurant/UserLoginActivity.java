// user login page
// UserLoginActivity.java
package com.example.hqrestaurant;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

public class UserLoginActivity extends AppCompatActivity {

    private EditText usernameInput, passwordInput;
    private Button loginButton;
    private ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_log);

        usernameInput = findViewById(R.id.usernameInput);
        passwordInput = findViewById(R.id.passwordInput);
        loginButton   = findViewById(R.id.loginButton);

        backButton = findViewById(R.id.backButton);
        if (backButton != null) backButton.setOnClickListener(v -> finish());

        View registerText = findViewById(R.id.registerLink);
        if (registerText != null) {
            registerText.setOnClickListener(v ->
                    startActivity(new Intent(UserLoginActivity.this, RegisterActivity.class))
            );
        }

        loginButton.setOnClickListener(v -> loginWithApi());
    }

    private void loginWithApi() {
        String username = safe(usernameInput);
        String password = safe(passwordInput);

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

        loginButton.setEnabled(false);

        // ✅ Call API: read_user/{student_id}/{username}
        Api.readUser(this, username,
                response -> {
                    loginButton.setEnabled(true);

                    try {
                        // Some APIs return { "user": { ... } } instead of flat json
                        JSONObject userObj = response.has("user") ? response.optJSONObject("user") : response;
                        if (userObj == null) {
                            Toast.makeText(this, "Login error: user data missing", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        String apiUsername = userObj.optString("username", "");
                        String apiPassword = userObj.optString("password", "");
                        String apiEmail    = userObj.optString("email", "");

                        if (TextUtils.isEmpty(apiPassword)) {
                            Toast.makeText(this, "Login error: password not returned by API", Toast.LENGTH_LONG).show();
                            return;
                        }

                        // Optional: ensure returned username matches what was typed
                        if (!TextUtils.isEmpty(apiUsername) && !username.equals(apiUsername)) {
                            Toast.makeText(this, "User mismatch / not found", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (password.equals(apiPassword)) {

                            // ✅ SAVE SESSION LOCALLY so GuestSettings can show real user info
                            SharedPreferences sp = getSharedPreferences("session", MODE_PRIVATE);
                            sp.edit()
                                    .putString("username", username)
                                    .putString("email", apiEmail)
                                    .apply();

                            Intent i = new Intent(UserLoginActivity.this, UserHomeActivity.class);
                            i.putExtra("username", username);
                            startActivity(i);
                            finish();

                        } else {
                            Toast.makeText(UserLoginActivity.this, "Wrong password", Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception ex) {
                        Toast.makeText(this, "Parse error: " + ex.getMessage(), Toast.LENGTH_LONG).show();
                    }
                },
                error -> {
                    loginButton.setEnabled(true);
                    Toast.makeText(UserLoginActivity.this, "User not found / API error", Toast.LENGTH_SHORT).show();
                }
        );
    }

    private String safe(EditText et) {
        return (et == null || et.getText() == null) ? "" : et.getText().toString().trim();
    }
}
