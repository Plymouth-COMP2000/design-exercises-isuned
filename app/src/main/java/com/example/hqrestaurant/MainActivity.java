package com.example.hqrestaurant;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // ---- Button navigation ----
        Button staffButton = findViewById(R.id.staffButton);
        Button guestButton = findViewById(R.id.guestButton);

        staffButton.setOnClickListener(v -> {
            // CHANGE StaffLoginActivity to your real staff login activity class name
            startActivity(new Intent(MainActivity.this, StaffLoginActivity.class));
        });

        guestButton.setOnClickListener(v -> {
            // CHANGE UserLoginActivity to your real user login activity class name
            startActivity(new Intent(MainActivity.this, UserLoginActivity.class));
        });
    }
}
