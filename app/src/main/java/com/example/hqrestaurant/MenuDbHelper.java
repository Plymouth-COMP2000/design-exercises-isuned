package com.example.hqrestaurant;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class MenuDbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "hqrestaurant.db";

    // ✅ IMPORTANT: bump this to reseed if DB already exists
    private static final int DB_VERSION = 2;

    public static final String TABLE_MENU = "menu";
    public static final String COL_ID = "id";
    public static final String COL_NAME = "name";
    public static final String COL_PRICE = "price";
    public static final String COL_IMAGE = "image"; // drawable name e.g. "burger"

    public MenuDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_MENU + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_NAME + " TEXT NOT NULL, " +
                COL_PRICE + " TEXT NOT NULL, " +
                COL_IMAGE + " TEXT NOT NULL" +
                ")");
        seedMenu(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MENU);
        onCreate(db);
    }

    private void seedMenu(SQLiteDatabase db) {
        insertItem(db, "Burger", "£6.99", "burger");
        insertItem(db, "Pizza", "£8.50", "pizza");
        insertItem(db, "Pasta", "£7.25", "pasta");
        insertItem(db, "Salad", "£5.00", "salad");
        insertItem(db, "Fries", "£2.99", "fries");
        insertItem(db, "Milkshake", "£3.50", "milkshake");
    }

    private void insertItem(SQLiteDatabase db, String name, String price, String imageName) {
        ContentValues cv = new ContentValues();
        cv.put(COL_NAME, name);
        cv.put(COL_PRICE, price);
        cv.put(COL_IMAGE, imageName);
        db.insert(TABLE_MENU, null, cv);
    }

    // ✅ STAFF: add/delete
    public long addMenuItem(String name, String price, String imageName) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_NAME, name);
        cv.put(COL_PRICE, price);
        cv.put(COL_IMAGE, imageName);
        return db.insert(TABLE_MENU, null, cv);
    }

    public int deleteMenuItem(int id) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(TABLE_MENU, COL_ID + "=?", new String[]{String.valueOf(id)});
    }

    public List<MenuItem> getAllMenuItems(Context context) {
        List<MenuItem> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_MENU + " ORDER BY " + COL_ID + " DESC", null);
        while (c.moveToNext()) {
            int id = c.getInt(c.getColumnIndexOrThrow(COL_ID));
            String name = c.getString(c.getColumnIndexOrThrow(COL_NAME));
            String price = c.getString(c.getColumnIndexOrThrow(COL_PRICE));
            String imageName = c.getString(c.getColumnIndexOrThrow(COL_IMAGE));

            int resId = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
            if (resId == 0) resId = R.drawable.logo;

            // ✅ now matches your MenuItem constructor
            list.add(new MenuItem(id, name, price, imageName, resId));
        }
        c.close();
        return list;
    }
}


