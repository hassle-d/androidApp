package com.androidapp.interfaces;

import java.util.Objects;
import com.androidapp.network.NetworkError;


/**
 * Created by Damien on 5/3/2017.
 */

public interface MyCallback {
    void successCallback(String tag, Object object);
    void errorCallback(String tag, NetworkError error);
}
