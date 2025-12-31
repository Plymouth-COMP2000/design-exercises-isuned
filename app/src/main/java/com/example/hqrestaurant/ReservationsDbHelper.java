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

    public static final String COL_ID = "id";
    public static final String COL_USERNAME = "username";
    public static final String COL_DATE = "date";
    public static final String COL_TIME = "time";
    public static final String COL_GUESTS = "guests";

    public ReservationsDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE + " ("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_USERNAME + " TEXT NOT NULL, "
                + COL_DATE + " TEXT NOT NULL, "
                + COL_TIME + " TEXT NOT NULL, "
                + COL_GUESTS + " INTEGER NOT NULL"
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
        cv.put(COL_USERNAME, username);
        cv.put(COL_DATE, date);
        cv.put(COL_TIME, time);
        cv.put(COL_GUESTS, guests);
        return db.insert(TABLE, null, cv);
    }

    // ✅ Guest: only their reservations
    public List<ReservationsModel> getReservationsForUser(String username) {
        SQLiteDatabase db = getReadableDatabase();
        List<ReservationsModel> list = new ArrayList<>();

        Cursor c = db.query(TABLE,
                null,
                COL_USERNAME + "=?",
                new String[]{username},
                null, null,
                COL_ID + " DESC"
        );

        while (c.moveToNext()) {
            int id = c.getInt(c.getColumnIndexOrThrow(COL_ID));
            String date = c.getString(c.getColumnIndexOrThrow(COL_DATE));
            String time = c.getString(c.getColumnIndexOrThrow(COL_TIME));
            int guests = c.getInt(c.getColumnIndexOrThrow(COL_GUESTS));

            list.add(new ReservationsModel(id, username, date, time, guests));
        }
        c.close();
        return list;
    }

    // ✅ Staff: ALL reservations
    public List<ReservationsModel> getAllReservations() {
        SQLiteDatabase db = getReadableDatabase();
        List<ReservationsModel> list = new ArrayList<>();

        Cursor c = db.query(TABLE,
                null,
                null,
                null,
                null, null,
                COL_ID + " DESC"
        );

        while (c.moveToNext()) {
            int id = c.getInt(c.getColumnIndexOrThrow(COL_ID));
            String username = c.getString(c.getColumnIndexOrThrow(COL_USERNAME));
            String date = c.getString(c.getColumnIndexOrThrow(COL_DATE));
            String time = c.getString(c.getColumnIndexOrThrow(COL_TIME));
            int guests = c.getInt(c.getColumnIndexOrThrow(COL_GUESTS));

            list.add(new ReservationsModel(id, username, date, time, guests));
        }
        c.close();
        return list;
    }

    public int deleteReservation(int id) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(TABLE, COL_ID + "=?", new String[]{String.valueOf(id)});
    }
}
