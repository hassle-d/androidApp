package com.androidapp.interfaces;

import com.androidapp.models.Item;
import com.androidapp.models.Token;
import com.androidapp.models.User;

import java.util.List;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by Damien on 5/3/2017.
 */

public interface EndpointInterface {
    @POST("/api/user/")
    Observable<User> createUser(@Body User user);

    @POST("/api/auth/login/")
    Observable<Token> connectUser(@Body User user);

    @POST("/api/item/")
    Observable<Item> createItem(@Header("Authorization") String token, @Body Item item);

    @GET("/api/item/")
    Observable<List<Item>> getItemList(@Header("Authorization") String token);

    @GET("/api/item/{id}")
    Observable<Item> getItem(@Header("Authorization") String token, @Path("id") String itemId);
}
