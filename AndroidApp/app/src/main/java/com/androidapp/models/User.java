package com.androidapp.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Damien on 5/3/2017.
 */

public class User {
    @SerializedName("_id")
    public String mId;

    @SerializedName("username")
    public String mUsername;

    @SerializedName("password")
    public String mPassword;

    @SerializedName("email")
    public String mEmail;

    public User(String email, String username, String password){
        this.mEmail = email;
        this.mUsername = username;
        this.mPassword = password;
    }

    public User(String username, String password){
        this.mUsername = username;
        this.mPassword = password;
    }
}
