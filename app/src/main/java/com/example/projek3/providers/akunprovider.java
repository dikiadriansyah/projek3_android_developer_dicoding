package com.example.projek3.providers;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import com.example.projek3.database.UserfavoritHelppers;

import java.util.Objects;

import static com.example.projek3.database.dbcontract.AUTHORITY;
import static com.example.projek3.database.dbcontract.DataColumns.CONTENT_URI;
import static com.example.projek3.database.dbcontract.TABLE_NAME;

public class akunprovider extends ContentProvider {
    private static final int FAVORITE = 1;
    private static final int FAVORITE_ID = 2;
    private UserfavoritHelppers userfavoritHelppers;

    private static final UriMatcher uriMatchers = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatchers.addURI(AUTHORITY, TABLE_NAME, FAVORITE);
        uriMatchers.addURI(AUTHORITY, TABLE_NAME+"/*", FAVORITE_ID);
    }

    public akunprovider(){

    }

    public boolean onCreate() {
        userfavoritHelppers = UserfavoritHelppers.getInstance(getContext());
        userfavoritHelppers.open();
        return true;
    }

    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder){

        Cursor cursor;
        switch (uriMatchers.match(uri)){
            case FAVORITE :
                cursor = userfavoritHelppers.queryAll();
                break;
            case  FAVORITE_ID :
                cursor = userfavoritHelppers.queryByUsername(uri.getLastPathSegment());
                break;
            default :
                cursor = null;
        }
        return cursor;

    }
@Override
    public Uri insert(Uri uri, ContentValues values){
        long added;
        if (uriMatchers.match(uri) == FAVORITE){
            added = userfavoritHelppers.insert(values);
        }else {
            added = 0;
        }
        Objects.requireNonNull(getContext()).getContentResolver().notifyChange(CONTENT_URI, null);
        return Uri.parse(CONTENT_URI+"/"+added);
    }

    public int delete(Uri uri, String selection, String[] selectionArgs){
        int deleted;
        if (uriMatchers.match(uri) == FAVORITE_ID){
            deleted = userfavoritHelppers.deleteByUsername(uri.getLastPathSegment());
        }else {
            deleted = 0;
        }
        return deleted;
    }

    public String getType(Uri uri){
        return null;
    }

    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs){
        return 0;
    }

}
