package com.androidapp;

import android.app.Application;
import android.content.Context;

public class App extends Application{
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        mContext = this;
    }
    public static Context getContext(){
        return mContext;
    }
}
