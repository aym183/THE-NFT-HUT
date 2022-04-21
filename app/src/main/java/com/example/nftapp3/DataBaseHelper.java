package com.example.nftapp3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String userTable = "users";
    public static final String userid_column = "user_id";
    public static final String username_column = "username";
    public static final String password_column = "password";
    public static final String DATABASE_NAME = "nftDB";
    public static final int DATABASE_VERSION = 1;

    private SQLiteDatabase db;

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

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + userTable);
        onCreate(db);
    }

//    public boolean addOne(UserDetails userDetails){
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues cv = new ContentValues();
//        cv.put(userid_column, userDetails.getUser_id());
//        cv.put(username_column, userDetails.getUsername());
//        cv.put(password_column, userDetails.getPassword());
//        long insert = db.insert(userTable, null, cv);
//
//        if(insert == -1) {
//            return false;
//        }else{
//            return true;
//        }
//    }
//
//    public List<UserDetails> getEveryone(String usernameInput, String passwordInput){
//        List<UserDetails> returnList = new ArrayList<>();
//        String queryString = "SELECT * FROM " + userTable + " WHERE " + username_column + " = ? AND " + password_column + " = ?";
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(queryString, new String[] {usernameInput, passwordInput});
//
//
//        if(cursor.moveToFirst()){
//            do{
//                int userid = cursor.getInt(0);
//                String userUsername = cursor.getString(1);
//                String userPassword = cursor.getString(2);
//                UserDetails newUser = new UserDetails(userid, userUsername, userPassword);
//                returnList.add(newUser);
//            }while(cursor.moveToNext());
//
//        }
//        else{
//            System.out.println("INCORRECT");
//        }
//        return returnList;
//    }
}
