package com.androidapp.network;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.androidapp.interfaces.EndpointInterface;
import com.androidapp.interfaces.MyCallback;
import com.androidapp.models.Token;
import com.androidapp.models.User;

import java.io.IOException;

import retrofit.HttpException;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Damien on 5/3/2017.
 */

public class Auth {

    Context     mContext;
    MyCallback mCallback;

    public Auth(Context context) {
        mContext = context;
        mCallback = (MyCallback) context;
    }


    public void register(final String tag, String email, String username, String password) {
        final EndpointInterface service = ServiceGenerator.createService(EndpointInterface.class);
        User user = new User(email, username, password);

        Observable<User> observable = service.createUser(user);
        observable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<User>() {
                    @Override
                    public void onCompleted() {
                        Log.d("APIRequest", "Completed");
                    }
                    @Override
                    public void onError(Throwable e) {
                        mCallback.errorCallback(tag, new NetworkError(e));
                    }
                    @Override
                    public void onNext(User user) {
                        mCallback.successCallback(tag, user);
                    }
                });
    }

    public void login(final String tag, String username, String password) {
        final EndpointInterface service = ServiceGenerator.createService(EndpointInterface.class);
        User user = new User(username, password);

        Observable<Token> observable = service.connectUser(user);
        observable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Token>() {
                    @Override
                    public void onCompleted() {
                        Log.d("APIRequest", "Completed");
                    }
                    @Override
                    public void onError(Throwable e) {
                        mCallback.errorCallback(tag, new NetworkError(e));
                    }
                    @Override
                    public void onNext(Token token) {
                        mCallback.successCallback(tag, token);
                    }
                });
    }

}
