package com.example.projek3.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import static android.provider.BaseColumns._ID;
import static com.example.projek3.database.dbcontract.DataColumns.AVATAR;
import static com.example.projek3.database.dbcontract.DataColumns.URLUSER;
import static com.example.projek3.database.dbcontract.DataColumns.USERNAME;
import static com.example.projek3.database.dbcontract.TABLE_NAME;

public class UserfavoritHelppers {
    private static final String DATABASE_TABLE = TABLE_NAME;
    private static dbhelpers Dbhelpers;
    private static UserfavoritHelppers INSTANCE;
    private static SQLiteDatabase databases;

    private UserfavoritHelppers(Context context) {
        Dbhelpers = new dbhelpers(context);
    }

    public static UserfavoritHelppers getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new UserfavoritHelppers(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        databases = Dbhelpers.getWritableDatabase();
    }

    public void close() {
        Dbhelpers.close();

        if (databases.isOpen()) {
            databases.close();
        }
    }

    public Cursor queryAll() {
        return databases.query(
                DATABASE_TABLE,
                null,
                null,
                null,
                null,
                null,
                _ID + " ASC"
        );
    }

    public Cursor queryByUsername(String username){
        return databases.query(
                DATABASE_TABLE,
                null,
                USERNAME + " = ?",
                new String[]{username},
                null,
                null,
                null,
                null
        );
    }

    public long insert(ContentValues value){
        return databases.insert(DATABASE_TABLE,
                null,
                value);
    }

    public int deleteByUsername(String username){
        Log.d("TAG", "deleteById: " + username);
        return databases.delete(DATABASE_TABLE, USERNAME + " = ?",
                new String[]{username});
    }

}
