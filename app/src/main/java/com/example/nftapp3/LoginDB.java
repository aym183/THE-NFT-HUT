package com.example.nftapp3;

import android.database.sqlite.SQLiteDatabase;

public class LoginDB {

    public static final String KEY_ROWID = "_id";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";

    public static final String LOG_TAG = "UsersDB";
    public static final String SQLITE_TABLE = "Users";
    public static final String TABLE_CREATE =
            "CREATE TABLE IF NOT EXISTS " + SQLITE_TABLE + " (" +
                    KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    KEY_USERNAME + " VARCHAR(50) NOT NULL UNIQUE," +
                    KEY_PASSWORD + " VARCHAR(50) NOT NULL);";

    public static void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

        db.execSQL("DROP TABLE IF EXISTS " + SQLITE_TABLE);
        onCreate(db);
    }

}
