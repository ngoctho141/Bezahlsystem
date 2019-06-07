package com.example.maddin.bluepay;

import android.util.Log;

public class Data {
    private String userID;
    private String username;
    private String password;
    private String telNumber;
    private String birthday;
    private String email;
    private String name;
    private String street;
    private String city;
    private String country;
    private double value;
    private boolean flag = false;

    public Data () { }

    public Data (String n, String p) {
        username = n;
        password = p;
    }

    public void saveStats (String n, String p) {
        username = n;
        password = p;
        flag = true;
    }

    public void setValue (double value) {
        this.value = value;
    }

    public boolean statsSaved() {
        return flag;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    void setPassword(String password)
    {
        this.password = password;
    }

    public double getValue() { return value; }

    public void setName(String n) {
        name = n;
    }


    public void registry(String ln, String n, String s, String t, String c) {
        username = ln;
        name = n;
        street = s;
        city = t;
        country = c;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getTelNumber() {
        return telNumber;
    }

    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
