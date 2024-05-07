package com.example.securecipher;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "combined_db.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_USERS = "users";
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";

    private static final String TABLE_DETAILS = "SavedDetails";
    private static final String COLUMN_DETAILS_ID = "details_id";
    private static final String COLUMN_PASSWORD_DETAILS = "password";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_REF_NAME = "ref_name";

    private static final String CREATE_TABLE_USERS = "CREATE TABLE " + TABLE_USERS +
            "(" +
            COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_USERNAME + " TEXT," +
            COLUMN_PASSWORD + " TEXT" +
            ")";

    private static final String CREATE_TABLE_DETAILS = "CREATE TABLE " + TABLE_DETAILS + " (" +
            COLUMN_DETAILS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_PASSWORD_DETAILS + " TEXT, " +
            COLUMN_EMAIL + " TEXT, " +
            COLUMN_REF_NAME + " TEXT)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_DETAILS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DETAILS);
        onCreate(db);
    }

    // Method to add a user to the users table
    public long addUser(String username, String password) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_PASSWORD, password);
        return db.insert(TABLE_USERS, null, values);
    }

    // Method to insert details into the SavedDetails table
    public long insertDetails(String encryptedPassword, String email, String refName) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PASSWORD_DETAILS, encryptedPassword);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_REF_NAME, refName);
        return db.insert(TABLE_DETAILS, null, values);
    }

    // Method to retrieve all saved details from the SavedDetails table
    public ArrayList<String> getAllSavedDetails() {
        ArrayList<String> savedDetailsList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String[] columns = {COLUMN_PASSWORD_DETAILS, COLUMN_EMAIL, COLUMN_REF_NAME};
        Cursor cursor = db.query(TABLE_DETAILS, columns, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String encryptedPassword = cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD_DETAILS));
                String email = cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL));
                String refName = cursor.getString(cursor.getColumnIndex(COLUMN_REF_NAME));
                savedDetailsList.add("Encrypted Password: " + encryptedPassword + ", Email: " + email + ", Reference Name: " + refName);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return savedDetailsList;
    }

    // Method to retrieve encrypted password by reference name from the SavedDetails table
    public String getEncryptedPasswordByReferenceName(String referenceName) {
        SQLiteDatabase db = getReadableDatabase();
        String[] columns = {COLUMN_PASSWORD_DETAILS};
        String selection = COLUMN_REF_NAME + " = ?";
        String[] selectionArgs = {referenceName};
        Cursor cursor = db.query(TABLE_DETAILS, columns, selection, selectionArgs, null, null, null);
        String encryptedPassword = null;
        if (cursor.moveToFirst()) {
            encryptedPassword = cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD_DETAILS));
        }
        cursor.close();
        return encryptedPassword;
    }
}
