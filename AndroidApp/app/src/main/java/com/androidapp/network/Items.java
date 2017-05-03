package com.androidapp.network;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.androidapp.interfaces.EndpointInterface;
import com.androidapp.interfaces.MyCallback;
import com.androidapp.models.Item;
import com.androidapp.models.Token;
import com.androidapp.models.User;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;

import java.io.File;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Damien on 5/3/2017.
 */

public class Items {

    Context     mContext;
    MyCallback mCallback;

    public Items(Context context) {
        mContext = context;
        mCallback = (MyCallback) context;
    }


    public void addItem(final String tag, final String token, String alias,
                         String dateBuy, String dateEnd, String dateNotification,
                         Boolean emailNotification, Boolean smsNotification) {
        final EndpointInterface service = ServiceGenerator.createService(EndpointInterface.class);

        Item item = new Item(alias, dateBuy, dateEnd, dateNotification, emailNotification, smsNotification);

        Observable<Item> observable = service.createItem(token, item);
        observable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Item>() {
                    @Override
                    public void onCompleted() {
                        Log.d("APIRequest", "Completed");
                    }
                    @Override
                    public void onError(Throwable e) {
                        mCallback.errorCallback(tag, new NetworkError(e));
                    }
                    @Override
                    public void onNext(Item item) {
                        mCallback.successCallback(tag, item);
                    }
                });
    }

    public void getItemList(final String tag, final String token) {
        final EndpointInterface service = ServiceGenerator.createService(EndpointInterface.class);

        Observable<List<Item>> observable = service.getItemList(token);
        observable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Item>>() {
                    @Override
                    public void onCompleted() {
                        Log.d("APIRequest", "Completed");
                    }
                    @Override
                    public void onError(Throwable e) {
                        mCallback.errorCallback(tag, new NetworkError(e));
                    }
                    @Override
                    public void onNext(List<Item> items) {
                        mCallback.successCallback(tag, items);
                    }
                });
    }

    public void getItem(final String tag, final String token, final String itemId) {
        final EndpointInterface service = ServiceGenerator.createService(EndpointInterface.class);

        Observable<Item> observable = service.getItem(token, itemId);
        observable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Item>() {
                    @Override
                    public void onCompleted() {
                        Log.d("APIRequest", "Completed");
                    }
                    @Override
                    public void onError(Throwable e) {
                        mCallback.errorCallback(tag, new NetworkError(e));
                    }
                    @Override
                    public void onNext(Item item) {
                        mCallback.successCallback(tag, item);
                    }
                });
    }

    public void addImage(final String tag, final String token, Uri imageUri, final String itemId) {
        final EndpointInterface service = ServiceGenerator.createService(EndpointInterface.class);

        File file = new File(imageUri.getPath());
        RequestBody fbody = RequestBody.create(MediaType.parse("image/*"), file);
        RequestBody mItemId = RequestBody.create(MediaType.parse("text/plain"), itemId);

        Observable<Item> observable = service.addImage(token, fbody, mItemId);
        observable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Item>() {
                    @Override
                    public void onCompleted() {
                        Log.d("APIRequest", "Completed");
                    }
                    @Override
                    public void onError(Throwable e) {
                        mCallback.errorCallback(tag, new NetworkError(e));
                    }
                    @Override
                    public void onNext(Item item) {
                        mCallback.successCallback(tag, item);
                    }
                });
    }

}
