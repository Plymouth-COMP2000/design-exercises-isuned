package com.example.hqrestaurant;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyReservationsActivity extends AppCompatActivity {

    private ReservationsDbHelper db;
    private RecyclerView recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_reservations);

        db = new ReservationsDbHelper(this);
        recycler = findViewById(R.id.reservationsRecycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        ImageButton backButton = findViewById(R.id.backButton);
        if (backButton != null) backButton.setOnClickListener(v -> finish());

        load();
    }

    private void load() {
        String username = getSharedPreferences("session", MODE_PRIVATE).getString("username", "Guest");

        List<ReservationsModel> list = db.getReservationsForUser(username);

        ReservationsAdapter adapter = new ReservationsAdapter(list, model -> {
            db.deleteReservation(model.id);
            Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
            load();
        });

        recycler.setAdapter(adapter);
    }
}
