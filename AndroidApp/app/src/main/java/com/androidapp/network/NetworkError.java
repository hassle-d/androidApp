package com.androidapp.network;

import android.util.Log;

import java.io.IOException;

import retrofit.HttpException;

/**
 * Created by Damien on 5/3/2017.
 */

public class NetworkError {
    public String mMessage;

    public NetworkError(Throwable e) {
        if (e instanceof HttpException) {
            try {
                mMessage = new String(((HttpException) e).response().errorBody().bytes());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        else {
            mMessage =  e.getMessage();
        }
    }
}
