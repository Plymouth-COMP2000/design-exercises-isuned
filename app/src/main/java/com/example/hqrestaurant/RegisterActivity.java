package com.example.hqrestaurant;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity {

    private static final String STUDENT_ID = "10897390";
    private static final String BASE_URL = "http://10.240.72.69/comp2000/coursework";
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    private EditText usernameInput, firstNameInput, lastNameInput, emailInput, contactInput, passwordInput;
    private Button signUpButton;
    private TextView loginLink;

    private final OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        View backButton = findViewById(R.id.backButton);
        if (backButton != null) backButton.setOnClickListener(v -> finish());

        usernameInput  = findViewById(R.id.usernameInput);
        firstNameInput = findViewById(R.id.firstNameInput);
        lastNameInput  = findViewById(R.id.lastNameInput);
        emailInput     = findViewById(R.id.emailInput);
        contactInput   = findViewById(R.id.contactInput);
        passwordInput  = findViewById(R.id.passwordInput);

        loginLink = findViewById(R.id.loginLink);
        signUpButton = findViewById(R.id.signUpButton);

        loginLink.setOnClickListener(v -> {
            startActivity(new Intent(RegisterActivity.this, UserLoginActivity.class));
            finish();
        });

        signUpButton.setOnClickListener(v -> createAccount());
    }

    private void createAccount() {
        String username  = safe(usernameInput);
        String firstname = safe(firstNameInput);
        String lastname  = safe(lastNameInput);
        String email     = safe(emailInput);
        String contact   = safe(contactInput);
        String password  = safe(passwordInput);

        if (username.isEmpty() || firstname.isEmpty() || lastname.isEmpty() ||
                email.isEmpty() || contact.isEmpty() || password.isEmpty()) {
            toast("Please fill in all fields.");
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailInput.setError("Enter a valid email");
            return;
        }

        if (password.length() < 6) {
            passwordInput.setError("Password must be at least 6 characters");
            return;
        }

        signUpButton.setEnabled(false);

        // Create DB first (or continue if it already exists)
        createStudentDb(() ->
                createUser(username, firstname, lastname, email, contact, password)
        );
    }

    // 🔹 FIXED VERSION — continues when DB already exists
    private void createStudentDb(Runnable onDone) {
        String url = BASE_URL + "/create_student/" + STUDENT_ID;

        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create("{}", JSON))
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> {
                    signUpButton.setEnabled(true);
                    toast("Student DB network error: " + e.getMessage());
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String resp = response.body() != null ? response.body().string() : "";

                runOnUiThread(() -> {

                    // 👍 DB created successfully
                    if (response.isSuccessful()) {
                        if (onDone != null) onDone.run();
                        return;
                    }

                    // 👍 DB already exists → continue anyway
                    if (response.code() == 400 && resp.contains("already exists")) {
                        if (onDone != null) onDone.run();
                        return;
                    }

                    // ❌ Any other error = stop
                    signUpButton.setEnabled(true);
                    toast("Create student failed (" + response.code() + "): " + resp);
                });
            }
        });
    }

    private void createUser(String username, String firstname, String lastname,
                            String email, String contact, String password) {

        try {
            JSONObject body = new JSONObject();
            body.put("username", username);
            body.put("password", password);
            body.put("firstname", firstname);
            body.put("lastname", lastname);
            body.put("email", email);
            body.put("contact", contact);
            body.put("usertype", "user");

            String url = BASE_URL + "/create_user/" + STUDENT_ID;

            Request request = new Request.Builder()
                    .url(url)
                    .post(RequestBody.create(body.toString(), JSON))
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    runOnUiThread(() -> {
                        signUpButton.setEnabled(true);
                        toast("Create user network error: " + e.getMessage());
                    });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String resp = response.body() != null ? response.body().string() : "";

                    runOnUiThread(() -> {
                        signUpButton.setEnabled(true);

                        if (response.isSuccessful()) {
                            toast("Account created successfully!");
                            startActivity(new Intent(RegisterActivity.this, UserLoginActivity.class));
                            finish();
                        } else {
                            toast("Register failed (" + response.code() + "): " + resp);
                        }
                    });
                }
            });

        } catch (Exception ex) {
            signUpButton.setEnabled(true);
            toast("Error: " + ex.getMessage());
        }
    }

    private String safe(EditText et) {
        return et.getText() == null ? "" : et.getText().toString().trim();
    }

    private void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}
