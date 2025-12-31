package com.example.hqrestaurant;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StaffReservationActivity extends AppCompatActivity {

    private ReservationsDbHelper db;
    private RecyclerView recycler;
    private TextView emptyText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.staff_reservations);

        db = new ReservationsDbHelper(this);

        recycler = findViewById(R.id.reservationsRecycler);
        emptyText = findViewById(R.id.emptyText);

        recycler.setLayoutManager(new LinearLayoutManager(this));

        ImageButton back = findViewById(R.id.backButton);
        if (back != null) back.setOnClickListener(v -> finish());

        load();
    }

    private void load() {
        List<ReservationsModel> list = db.getAllReservations();

        if (list.isEmpty()) {
            emptyText.setVisibility(View.VISIBLE);
            recycler.setVisibility(View.GONE);
            return;
        }

        emptyText.setVisibility(View.GONE);
        recycler.setVisibility(View.VISIBLE);

        ReservationsAdapter adapter = new ReservationsAdapter(list, model -> {
            db.deleteReservation(model.getId()); // ✅ staff deletes any reservation
            Toast.makeText(this, "Reservation deleted", Toast.LENGTH_SHORT).show();
            load();
        });

        recycler.setAdapter(adapter);
    }
}
