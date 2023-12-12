package com.example.clientapp;

import android.database.Cursor;

import static com.example.clientapp.db_contract.DataColumn.AVATAR;
import static com.example.clientapp.db_contract.DataColumn.URLUSER;
import static com.example.clientapp.db_contract.DataColumn.USERNAME;


import java.util.ArrayList;

public class MappingHelper {
    public static ArrayList<User> mapCursorToArrayList(Cursor userCursor){
        ArrayList<User> userlist = new ArrayList<>();

        while (userCursor.moveToNext()){
            String username = userCursor.getString(userCursor.getColumnIndexOrThrow(USERNAME));
            String avatar = userCursor.getString(userCursor.getColumnIndexOrThrow(AVATAR));
            String urlUser = userCursor.getString(userCursor.getColumnIndexOrThrow(URLUSER));
            userlist.add(new User(username, avatar, urlUser));
        }
        return userlist;
    }
}
