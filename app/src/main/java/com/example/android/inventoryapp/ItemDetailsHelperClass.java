package com.example.android.inventoryapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ItemDetailsHelperClass extends SQLiteOpenHelper {

    public static String TABLE_NAME = "products";
    public static String COLUMN_ID = "_id";
    public static String COLUMN_PRODUCT_NAME = "name";
    public static String COLUMN_PRODUCT_DESC = "description";
    public static String COLUMN_PRODUCT_PRICE = "price";
    public static String COLUMN_PRODUCT_QUANTITY = "quantity";
    public static String COLUMN_PRODUCT_IMAGE = "image";

    public static String DATABASE_NAME = "ItemDetails.db";
    public static int DATABASE_VERSION = 1;

    public ItemDetailsHelperClass(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +
                "( " + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_PRODUCT_NAME + " STRING," +
                COLUMN_PRODUCT_DESC + " STRING, " +
                COLUMN_PRODUCT_PRICE + " INT, " +
                COLUMN_PRODUCT_QUANTITY + " INT," +
                COLUMN_PRODUCT_IMAGE + " STRING);";
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void write(String prodName, String prodDesc, int price, int quantity, String img) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_PRODUCT_NAME, prodName);
        cv.put(COLUMN_PRODUCT_DESC, prodDesc);
        cv.put(COLUMN_PRODUCT_PRICE, price);
        cv.put(COLUMN_PRODUCT_QUANTITY, quantity);
        cv.put(COLUMN_PRODUCT_IMAGE, img);
        db.insert(TABLE_NAME, null, cv);
        db.close();
    }

    public int readColumnQuantity() {
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        int val = 0;
        if (cursor.moveToFirst()) {
            do {
                val = cursor.getInt(4);
            } while (cursor.moveToNext());
        }
        db.close();
        return val;
    }

    public String readString(long rowId, int col) {
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = " + rowId;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        String val = null;
        if (cursor.moveToFirst()) {
            do {
                val = cursor.getString(col);

            } while (cursor.moveToNext());
        }
        db.close();
        return val;
    }

    public void delete(long rowId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + " = " + rowId, null);
        db.close();
    }

    public void update(long rowdId, int quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_PRODUCT_QUANTITY, quantity);
        db.update(TABLE_NAME, cv, COLUMN_ID + " = " + rowdId, null);
        db.close();
    }

    public void updateQuantity(int quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_PRODUCT_QUANTITY, quantity);
        db.update(TABLE_NAME, cv, null, null);
        db.close();
    }

    public Cursor readValues() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_NAME, new String[]{COLUMN_ID, COLUMN_PRODUCT_NAME,
                COLUMN_PRODUCT_PRICE, COLUMN_PRODUCT_QUANTITY}, null, null, null, null, null);
    }
}
