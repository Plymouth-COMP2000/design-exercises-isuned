

// local staff login Database  with staff details
package com.example.hqrestaurant;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class StaffDbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "hqrestaurant_local.db";
    private static final int DB_VERSION = 1;

    public static final String TABLE_STAFF = "staff";
    public static final String COL_ID = "_id";
    public static final String COL_USERNAME = "username";
    public static final String COL_PASSWORD = "password";
    public static final String COL_FULLNAME = "fullname";

    public StaffDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createStaff = "CREATE TABLE " + TABLE_STAFF + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_USERNAME + " TEXT UNIQUE NOT NULL, " +
                COL_PASSWORD + " TEXT NOT NULL, " +
                COL_FULLNAME + " TEXT NOT NULL" +
                ");";
        db.execSQL(createStaff);

        // Seed 3 staff accounts ONCE (when DB is first created)
        insertStaff(db, "staff1", "pass1234", "Staff One");
        insertStaff(db, "staff2", "pass1234", "Staff Two");
        insertStaff(db, "manager", "admin1234", "Restaurant Manager");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // simple for coursework
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STAFF);
        onCreate(db);
    }

    private void insertStaff(SQLiteDatabase db, String username, String password, String fullname) {
        ContentValues cv = new ContentValues();
        cv.put(COL_USERNAME, username);
        cv.put(COL_PASSWORD, password);
        cv.put(COL_FULLNAME, fullname);
        db.insert(TABLE_STAFF, null, cv);
    }

    /** Returns staff fullname if login is valid, otherwise null */
    public String loginStaff(String username, String password) {
        SQLiteDatabase db = getReadableDatabase();

        Cursor c = db.query(
                TABLE_STAFF,
                new String[]{COL_FULLNAME},
                COL_USERNAME + "=? AND " + COL_PASSWORD + "=?",
                new String[]{username, password},
                null, null, null
        );

        String fullname = null;
        if (c.moveToFirst()) {
            fullname = c.getString(c.getColumnIndexOrThrow(COL_FULLNAME));
        }
        c.close();
        return fullname;
    }
}
