package com.example.hqrestaurant;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class ReservationsDbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "hq_local.db";
    private static final int DB_VERSION = 1;

    public static final String TABLE = "reservations";

    public ReservationsDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE + " ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "username TEXT NOT NULL, "
                + "date TEXT NOT NULL, "
                + "time TEXT NOT NULL, "
                + "guests INTEGER NOT NULL"
                + ")";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(db);
    }

    public long insertReservation(String username, String date, String time, int guests) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("username", username);
        cv.put("date", date);
        cv.put("time", time);
        cv.put("guests", guests);
        return db.insert(TABLE, null, cv);
    }

    public List<ReservationsModel> getReservationsForUser(String username) {
        SQLiteDatabase db = getReadableDatabase();
        List<ReservationsModel> list = new ArrayList<>();

        Cursor c = db.query(TABLE,
                null,
                "username=?",
                new String[]{username},
                null, null,
                "id DESC"
        );

        while (c.moveToNext()) {
            int id = c.getInt(c.getColumnIndexOrThrow("id"));
            String date = c.getString(c.getColumnIndexOrThrow("date"));
            String time = c.getString(c.getColumnIndexOrThrow("time"));
            int guests = c.getInt(c.getColumnIndexOrThrow("guests"));
            list.add(new ReservationsModel(id, username, date, time, guests));
        }
        c.close();
        return list;
    }

    public int deleteReservation(int id) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(TABLE, "id=?", new String[]{String.valueOf(id)});
    }
}
