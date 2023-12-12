package com.example.projek3.database;

import android.database.Cursor;

import com.example.projek3.model.User;

import java.util.ArrayList;

import static com.example.projek3.database.dbcontract.DataColumns.AVATAR;
import static com.example.projek3.database.dbcontract.DataColumns.URLUSER;
import static com.example.projek3.database.dbcontract.DataColumns.USERNAME;


public class MapHelppers {
    public static ArrayList<User> mapCursorToArrayList(Cursor userCursor){
        ArrayList<User> useritemList = new ArrayList<>();

        while (userCursor.moveToNext()){
            String usernames = userCursor.getString(userCursor.getColumnIndexOrThrow(USERNAME));
            String avatars = userCursor.getString(userCursor.getColumnIndexOrThrow(AVATAR));
            String urlUser = userCursor.getString(userCursor.getColumnIndexOrThrow(URLUSER));

            useritemList.add(new User(usernames, avatars, urlUser));
        }
        return useritemList;
    }
}
