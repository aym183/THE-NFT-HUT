package com.example.nftapp3;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * The MyContentProvider conducts the operation of creating the content provider
 * to be used by external applications
 */
public class MyContentProvider extends ContentProvider {

    /* Variables containing fields for db tables and URL for content provider */
    private DataBaseHelper dbHelper;
    public static final String PROVIDER_NAME = "com.example.nftapp3.MyContentProvider";
    public static final String URL = "content://" + PROVIDER_NAME + "/users";
    static final Uri CONTENT_URI = Uri.parse(URL);
    public static final String userid_column = "user_id";
    public static final String username_column = "username";
    public static final String password_column = "password";
    private SQLiteDatabase db;
    public static final int USERS = 1;
    public static final int USERS_ID = 2;

    static UriMatcher uriMatcher;
        static{
            uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
            uriMatcher.addURI(PROVIDER_NAME, "users", USERS);
            uriMatcher.addURI(PROVIDER_NAME, "users/#", USERS_ID);
        }

    /**
     * This method that creates the db by calling the DataBaseHelper method
     * @return bool returns true if db not null
     */
    @Override
    public boolean onCreate() {
        Context context = getContext();
        DataBaseHelper dbHelper = new DataBaseHelper(context);
        db = dbHelper.getWritableDatabase();
        return (db==null) ? false:true;
    }

    /**
     * This method that sets the list of tables to query
     * @param uri The url to connect to the db
     * @param projection An array of which columns to return
     * @param selection A filter declaring which rows to return
     * @param selectionArgs The string inputs required for the db
     * @param sortOrder This allows user to order the rows, formatted as an SQL ORDER BY clause
     */
    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(DataBaseHelper.userTable);

        switch(uriMatcher.match(uri)){
            case USERS:
                break;
            case USERS_ID:
                String id = uri.getPathSegments().get(1);
                queryBuilder.appendWhere(DataBaseHelper.userid_column + "=" + uri);
                break;
            default:
        }

        if(sortOrder == "" || sortOrder == null){
            sortOrder = DataBaseHelper.username_column;
        }

        Cursor cursor = queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    /**
     * This method returns the MIME type of data in the content provider
     * @param uri The url to connect to the db
     */
    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {

        switch (uriMatcher.match(uri)){
            case USERS:
                return "vnd.android.cursor.dir/vnd.example.nftapp3";
            case USERS_ID:
                return "vnd.android.cursor.item/vnd.example.nftapp3";
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }

    }

    /**
     * This method inserts records into the db
     * @param uri The url to connect to the db
     * @param values The values to insert into the dnb
     */
    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

       long row_id = db.insert(DataBaseHelper.userTable, "", values);
       if(row_id > 0){
           Uri _uri = ContentUris.withAppendedId(CONTENT_URI, row_id);
           getContext().getContentResolver().notifyChange(_uri, null);
           return _uri;
       }
       throw new SQLException("FAILED TO ADD A RECORD INTO " + uri);
    }

    /**
     * This method deletes the db table
     * @param uri The url to connect to the db
     * @param selection A filter declaring which rows to return
     * @param selectionArgs The string inputs required for the db
     */
    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {

        int count = 0;
        switch (uriMatcher.match(uri)){
            case USERS:
                count = db.delete(DataBaseHelper.userTable, selection, selectionArgs);
                break;

            case USERS_ID:
                String id = uri.getPathSegments().get(1);
                count = db.delete(DataBaseHelper.userTable, DataBaseHelper.userid_column + " = " + id +
                        (!TextUtils.isEmpty(selection)? " AND (" + selection + ")" : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    /**
     * This method is used to handle updating of records in the db
     * @param uri The url to connect to the db
     * @param selection A filter declaring which rows to return
     * @param selectionArgs The string inputs required for the db
     */
    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values,
                      @Nullable String selection, @Nullable String[] selectionArgs) {

        int count = 0;
        switch(uriMatcher.match(uri)){
            case USERS:
                count = db.update(DataBaseHelper.userTable, values, selection, selectionArgs);
                break;

            case USERS_ID:
                String id = uri.getPathSegments().get(1);
                count = db.update(DataBaseHelper.userTable, values, DataBaseHelper.userid_column + "=" + id
                        + (!TextUtils.isEmpty(selection) ?
                        "AND (" + selection + ")" : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count ;
    }
}
