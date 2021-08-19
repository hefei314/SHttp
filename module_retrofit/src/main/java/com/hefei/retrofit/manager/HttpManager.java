package com.hefei.retrofit.manager;

import android.content.Context;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * <pre>
 *     author: hefei
 *     time  : 2021/07/29
 *     desc  :
 * </pre>
 */
public class HttpManager {

    private static Context context;

    private static OkHttpClient okHttpClient;

    private static Retrofit.Builder retrofitBuilder;
    private static OkHttpClient.Builder okHttpBuilder;

    public static void init(Context appContext) {
        if (appContext != null) {
            context = appContext;
            okHttpBuilder = new OkHttpClient.Builder();
            retrofitBuilder = new Retrofit.Builder();
        }
    }

    public static Context getContext() {
        if (context == null) {
            throw new IllegalStateException("Please call ViseHttp.init(this) in Application to initialize!");
        }
        return context;
    }

    public static OkHttpClient.Builder getOkHttpBuilder() {
        if (okHttpBuilder == null) {
            throw new IllegalStateException("Please call ViseHttp.init(this) in Application to initialize!");
        }
        return okHttpBuilder;
    }

    public static Retrofit.Builder getRetrofitBuilder() {
        if (retrofitBuilder == null) {
            throw new IllegalStateException("Please call ViseHttp.init(this) in Application to initialize!");
        }
        return retrofitBuilder;
    }

    public static OkHttpClient getOkHttpClient() {
        if (okHttpClient == null) {
            okHttpClient = getOkHttpBuilder().build();
        }
        return okHttpClient;
    }
}
