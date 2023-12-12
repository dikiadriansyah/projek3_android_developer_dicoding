package com.example.projek3.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static com.example.projek3.database.dbcontract.DataColumns.AVATAR;
import static com.example.projek3.database.dbcontract.DataColumns.URLUSER;
import static com.example.projek3.database.dbcontract.DataColumns.USERNAME;
import static com.example.projek3.database.dbcontract.DataColumns.URLUSER;
import static com.example.projek3.database.dbcontract.TABLE_NAME;

public class dbhelpers extends SQLiteOpenHelper {
    public static String DATABASE_NAME = "databasegithubuser";
private static final int DATABASE_VERSION = 1;
private static final String SQL_CREATE_TABLE = String.format("CREATE TABLE %s"
                + "(%s INTEGER PRIMARY KEY AUTOINCREMENT, " + "%s TEXT NOT NULL,"
      +"%s TEXT NOT NULL,"  +"%s TEXT NULL)",
        dbcontract.TABLE_NAME,
        dbcontract.DataColumns._ID,
        USERNAME,
        AVATAR,
        URLUSER);

public dbhelpers(Context context){
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
}

@Override
public void onCreate(SQLiteDatabase database){

    database.execSQL(SQL_CREATE_TABLE);
}

@Override
public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion){
    database.execSQL("DROP TABLE IF EXISTS " + dbcontract.TABLE_NAME);
    onCreate(database);
}

}
