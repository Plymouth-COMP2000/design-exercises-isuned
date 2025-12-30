package com.example.hqrestaurant;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StaffManageMenuActivity extends AppCompatActivity {

    private MenuDbHelper db;
    private RecyclerView recycler;
    private TextView emptyText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.staff_menu);

        View backButton = findViewById(R.id.backButton);
        if (backButton != null) backButton.setOnClickListener(v -> finish());

        recycler = findViewById(R.id.staffMenuRecycler);
        emptyText = findViewById(R.id.emptyText);

        if (recycler == null) {
            Toast.makeText(this,
                    "staff_menu.xml missing RecyclerView with id staffMenuRecycler",
                    Toast.LENGTH_LONG).show();
            return;
        }

        db = new MenuDbHelper(this);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        loadMenu();
    }

    private void loadMenu() {
        List<MenuItem> items = db.getAllMenuItems(this);

        if (items == null || items.isEmpty()) {
            if (emptyText != null) emptyText.setVisibility(View.VISIBLE);
            recycler.setVisibility(View.GONE);
            return;
        } else {
            if (emptyText != null) emptyText.setVisibility(View.GONE);
            recycler.setVisibility(View.VISIBLE);
        }

        StaffMenuAdapter adapter = new StaffMenuAdapter(items, item -> {
            db.deleteMenuItem(item.getId());
            Toast.makeText(this, "Deleted: " + item.getName(), Toast.LENGTH_SHORT).show();
            loadMenu();
        });

        recycler.setAdapter(adapter);
    }
}


