package com.example.maddin.bluepay;

import android.util.Log;

public class Data {
    private String username;
    private String password;
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
        return name;
    }

    public String getPassword() {
        return password;
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
}
