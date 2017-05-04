package com.androidapp.interfaces;

import com.androidapp.models.Item;
import com.androidapp.models.Token;
import com.androidapp.models.User;
import com.androidapp.models.ValidToken;
import com.squareup.okhttp.RequestBody;

import java.util.List;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Part;
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

    @GET("/api/auth/logout")
    Observable<Void> disconnectUser(@Header("Authorization") String token);

    @GET("/api/auth/token")
    Observable<ValidToken> checkToken(@Header("Authorization") String token);

    @POST("/api/image/")
    Observable<Item> addImage(@Header("Authorization") String token, @Part("image")RequestBody image,
                              @Part("itemId")RequestBody itemId);

    @POST("/api/item/")
    Observable<Item> createItem(@Header("Authorization") String token, @Body Item item);

    @GET("/api/item/")
    Observable<List<Item>> getItemList(@Header("Authorization") String token);

    @GET("/api/item/{id}/")
    Observable<Item> getItem(@Header("Authorization") String token, @Path("id") String itemId);
}
