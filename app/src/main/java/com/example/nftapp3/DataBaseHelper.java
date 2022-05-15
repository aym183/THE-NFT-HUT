package com.example.nftapp3;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import androidx.annotation.Nullable;


/**
 * This DatabaseHelper class is used to create and delete db tables
 */
public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String userTable = "users";
    public static final String userid_column = "user_id";
    public static final String username_column = "username";
    public static final String password_column = "password";
    public static final String DATABASE_NAME = "nftDB";
    public static final int DATABASE_VERSION = 1;
    private SQLiteDatabase db;

    /* Query that creates db table */
    static final String CREATE_DB_TABLE =
            " CREATE TABLE " + userTable +
                    " (user_id INTEGER PRIMARY KEY, " +
                    " username VARCHAR(50) NOT NULL UNIQUE, " +
                    " password VARCHAR(50) NOT NULL);";

    public DataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_DB_TABLE);
    }

    /* Query that deletes db table */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + userTable);
        onCreate(db);
    }
}
