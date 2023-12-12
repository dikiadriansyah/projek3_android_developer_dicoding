package com.example.clientapp;

import android.net.Uri;
import android.provider.BaseColumns;

public class db_contract {
    public static final String AUTHORITY = "com.example.projek3";
    public static final String SCHEME = "content";
    public static String TABLE_NAME = "datagithub";

    public static final class DataColumn implements BaseColumns {
        public static String USERNAME = "username";
        public static String AVATAR = "avatar";
        public static String URLUSER = "urluser";

        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build();
    }
}
