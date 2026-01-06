package com.example.hqrestaurant;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class GuestReservationsActivity extends AppCompatActivity {

    private ReservationsDbHelper db;

    private Button btnMyReservation, btnNewReservation, btnConfirm, btnCancel;
    private TextView txtPickDate, txtPickTime;
    private EditText guestCountInput;

    private String pickedDate = "";
    private String pickedTime = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guest_reservations);

        db = new ReservationsDbHelper(this);

        ImageButton backButton = findViewById(R.id.backButton);
        if (backButton != null) backButton.setOnClickListener(v -> finish());

        btnMyReservation = findViewById(R.id.btnMyReservation);
        btnNewReservation = findViewById(R.id.btnNewReservation);

        txtPickDate = findViewById(R.id.txtPickDate);
        txtPickTime = findViewById(R.id.txtPickTime);
        guestCountInput = findViewById(R.id.guestCountInput);

        btnConfirm = findViewById(R.id.btnConfirm);
        btnCancel = findViewById(R.id.btnCancel);

        // Switch buttons
        btnMyReservation.setOnClickListener(v ->
                startActivity(new Intent(this, MyReservationsActivity.class))
        );

        btnNewReservation.setOnClickListener(v ->
                Toast.makeText(this, "Fill the form below to create a reservation.", Toast.LENGTH_SHORT).show()
        );

        txtPickDate.setOnClickListener(v -> pickDate());
        txtPickTime.setOnClickListener(v -> pickTime());

        btnCancel.setOnClickListener(v -> clearForm());

        btnConfirm.setOnClickListener(v -> saveReservation());
    }

    private void pickDate() {
        Calendar cal = Calendar.getInstance();
        new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            pickedDate = String.format("%02d/%02d/%04d", dayOfMonth, (month + 1), year);
            txtPickDate.setText("  " + pickedDate);
        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void pickTime() {
        Calendar cal = Calendar.getInstance();
        new TimePickerDialog(this, (view, hourOfDay, minute) -> {
            pickedTime = String.format("%02d:%02d", hourOfDay, minute);
            txtPickTime.setText("  " + pickedTime);
        }, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show();
    }

    private void saveReservation() {
        String username = getSharedPreferences("session", MODE_PRIVATE).getString("username", "Guest");

        String guestsStr = guestCountInput.getText() == null ? "" : guestCountInput.getText().toString().trim();

        if (TextUtils.isEmpty(pickedDate)) {
            Toast.makeText(this, "Please pick a date", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(pickedTime)) {
            Toast.makeText(this, "Please pick a time", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(guestsStr)) {
            guestCountInput.setError("Enter guests");
            guestCountInput.requestFocus();
            return;
        }

        int guests;
        try {
            guests = Integer.parseInt(guestsStr);
        } catch (NumberFormatException e) {
            guestCountInput.setError("Guests must be a number");
            guestCountInput.requestFocus();
            return;
        }

        if (guests <= 0) {
            guestCountInput.setError("Guests must be 1+");
            guestCountInput.requestFocus();
            return;
        }

        long id = db.insertReservation(username, pickedDate, pickedTime, guests);
        if (id > 0) {

            // ✅ TRIGGER STAFF NOTIFICATION (same phone)
            // (This assumes you already created NotificationHelper.showNewReservation)
            NotificationHelper.showNewReservation(this, username, pickedDate, pickedTime);

            Toast.makeText(this, "Reservation saved!", Toast.LENGTH_SHORT).show();
            clearForm();

        } else {
            Toast.makeText(this, "Failed to save reservation", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearForm() {
        pickedDate = "";
        pickedTime = "";
        txtPickDate.setText("  (pick date)");
        txtPickTime.setText("  [PICK TIME]");
        guestCountInput.setText("");
    }
}

