package com.example.hqrestaurant;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
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

        db = new MenuDbHelper(this);

        View backButton = findViewById(R.id.backButton);
        if (backButton != null) backButton.setOnClickListener(v -> finish());

        recycler = findViewById(R.id.staffMenuRecycler);
        emptyText = findViewById(R.id.emptyText);

        recycler.setLayoutManager(new LinearLayoutManager(this));

        // Manage Menu button (optional: just reload)
        View btnManageMenu = findViewById(R.id.btnManageMenu);
        if (btnManageMenu != null) {
            btnManageMenu.setOnClickListener(v -> loadMenu());
        }

        // ✅ Add item button
        View btnAddItem = findViewById(R.id.btnAddItem);
        if (btnAddItem != null) {
            btnAddItem.setOnClickListener(v -> showAddItemDialog());
        }

        loadMenu();
    }

    private void loadMenu() {
        List<MenuItem> items = db.getAllMenuItems(this);

        if (items == null || items.isEmpty()) {
            if (emptyText != null) emptyText.setVisibility(View.VISIBLE);
            recycler.setVisibility(View.GONE);
            return;
        }

        if (emptyText != null) emptyText.setVisibility(View.GONE);
        recycler.setVisibility(View.VISIBLE);

        StaffMenuAdapter adapter = new StaffMenuAdapter(items, item -> {
            db.deleteMenuItem(item.getId());
            Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
            loadMenu();
        });

        recycler.setAdapter(adapter);
    }

    private void showAddItemDialog() {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_menu_item, null);

        EditText etName = dialogView.findViewById(R.id.etItemName);
        EditText etPrice = dialogView.findViewById(R.id.etItemPrice);
        EditText etImage = dialogView.findViewById(R.id.etImageName);

        new AlertDialog.Builder(this)
                .setTitle("Add Menu Item")
                .setView(dialogView)
                .setNegativeButton("Cancel", (d, w) -> d.dismiss())
                .setPositiveButton("Add", (d, w) -> {
                    String name = etName.getText().toString().trim();
                    String price = etPrice.getText().toString().trim();
                    String imageName = etImage.getText().toString().trim().toLowerCase();

                    if (TextUtils.isEmpty(name) || TextUtils.isEmpty(price) || TextUtils.isEmpty(imageName)) {
                        Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // Make price consistent
                    if (!price.startsWith("£")) price = "£" + price;

                    long id = db.addMenuItem(name, price, imageName);
                    if (id > 0) {
                        Toast.makeText(this, "Added!", Toast.LENGTH_SHORT).show();
                        loadMenu();
                    } else {
                        Toast.makeText(this, "Failed to add", Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
    }
}



