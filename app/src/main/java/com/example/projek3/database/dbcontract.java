package com.example.projek3.database;

import android.net.Uri;
import android.provider.BaseColumns;

public class dbcontract {
    public static final String AUTHORITY = "com.example.projek3";
    public static final String SCHEME = "content";
    public static String TABLE_NAME = "datagithub";

    public static final class DataColumns implements BaseColumns {
        public static String USERNAME = "username";
        public static String AVATAR = "avatar";
        public static String URLUSER = "urluser";


        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build();

    }
}
