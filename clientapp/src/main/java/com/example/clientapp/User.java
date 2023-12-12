package com.example.clientapp;

public class User {
    private String username;
    private String avatar;
    private String urlUser;

    public  User(String username, String avatar, String urlUser){
        this.username = username;
        this.avatar = avatar;
        this.urlUser = urlUser;

    }

    public String getAvatar(){
        return avatar;
    }

    public void setAvatar(String avatar){
        this.avatar = avatar;
    }


    public String getUsername(){
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }


    public String getUrlUser(){
        return urlUser;
    }

    public void setUrlUser(String urlUser){
        this.urlUser = urlUser;
    }

}
