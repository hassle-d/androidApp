package com.androidapp.network;

/**
 * Created by Damien on 5/3/2017.
 */

import android.app.Activity;
import android.content.Context;

import com.squareup.okhttp.OkHttpClient;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import java.lang.annotation.Annotation;
import retrofit.Converter;
import com.squareup.okhttp.ResponseBody;


public class ServiceGenerator extends Activity {

    public static final String API_BASE_URL = "http://192.168.1.46:3000";
    private static Retrofit mRetrofit = null;

    private static OkHttpClient httpClient = new OkHttpClient();
    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create());

    public static <S> S createService(Class<S> serviceClass) {
        mRetrofit = builder.client(httpClient).build();
        return mRetrofit.create(serviceClass);
    }

    public static Retrofit getRetrofit() {
        return mRetrofit;
    }

}
