package com.androidapp.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Damien on 5/3/2017.
 */

public class Item {
    @SerializedName("_id")
    public String mId;

    @SerializedName("authorId")
    public String mAuthorId;

    @SerializedName("alias")
    public String mAlias;

    @SerializedName("dateBuy")
    public String mDateBuy;

    @SerializedName("dateEnd")
    public String mDateEnd;

    @SerializedName("dateNotification")
    public String mDateNotification;

    @SerializedName("dateCreation")
    public String mDateCreation;

    @SerializedName("duration")
    public String mDuration;

    @SerializedName("emailNotification")
    public Boolean mEmailNotification;

    @SerializedName("smsNotification")
    public Boolean mSmsNotification;

    @SerializedName("images")
    public List<String> mImages;

    public Item(String alias, String dateBuy, String dateEnd, String dateNotification,
                Boolean emailNotification, Boolean smsNotification) {
        this.mAlias = alias;
        this.mDateBuy = dateBuy;
        this.mDateEnd = dateEnd;
        this.mDateNotification = dateNotification;
        this.mEmailNotification = emailNotification;
        this.mSmsNotification = smsNotification;
    }
}
