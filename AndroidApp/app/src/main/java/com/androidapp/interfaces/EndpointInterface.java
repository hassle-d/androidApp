package com.androidapp.interfaces;

import com.androidapp.models.Token;
import com.androidapp.models.User;

import retrofit.http.Body;
import retrofit.http.POST;
import rx.Observable;

/**
 * Created by Damien on 5/3/2017.
 */

public interface EndpointInterface {
    @POST("/api/auth/signin/")
    Observable<User> createUser(@Body User user);

    @POST("/api/auth/login/")
    Observable<Token> connectUser(@Body User user);
}
