package com.example.projek3.model;

public class User {
    private String username;
    private String avatar;
    private String name;
    private String company;
    private String location;
    private String urluser;
    private int followers;
    private int followings;
    private int repositories;


    public User() {
    }

    public User(String username, String avatar, String urluser) {
        this.username = username;
        this.avatar = avatar;
        this.urluser = urluser;


    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public int getRepositories() {
        return repositories;
    }

    public void setRepository(int repositories) {

        this.repositories = repositories;
    }

    public int getFollowers() {

        return followers;
    }

    public void setFollowers(int followers) {

        this.followers = followers;
    }

    public int getFollowings() {

        return followings;
    }

    public void setFollowings(int followings) {
        this.followings = followings;
    }


    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    public String getBlog() {
        return blog;
    }

    public void setBlog(String blog) {
        this.blog = blog;
    }

    private String blog;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUrluser() {
        return urluser;
    }

    public void setUrluser(String urluser) {
        this.urluser = urluser;
    }


}
