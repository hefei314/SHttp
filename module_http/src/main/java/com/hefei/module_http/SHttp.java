package com.hefei.module_http;

import android.content.Context;

import com.hefei.module_http.config.SHttpGlobalConfig;
import com.hefei.module_http.core.ApiManager;
import com.hefei.module_http.request.DeleteRequest;
import com.hefei.module_http.request.GetRequest;
import com.hefei.module_http.request.HeadRequest;
import com.hefei.module_http.request.OptionsRequest;
import com.hefei.module_http.request.PatchRequest;
import com.hefei.module_http.request.PostRequest;
import com.hefei.module_http.request.PutRequest;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * <pre>
 *     author: hefei
 *     time  : 2021/01/29
 *     desc  :
 * </pre>
 */
public class SHttp {

    private static Context context;

    private static OkHttpClient okHttpClient;

    private static Retrofit.Builder retrofitBuilder;
    private static OkHttpClient.Builder okHttpBuilder;

    private static final SHttpGlobalConfig S_HTTP_GLOBAL_CONFIG = SHttpGlobalConfig.getInstance();

    public static SHttpGlobalConfig CONFIG() {
        return S_HTTP_GLOBAL_CONFIG;
    }

    public static void init(Context appContext) {
        if (appContext != null) {
            context = appContext.getApplicationContext();
        }
    }

    public static Context getContext() {
        if (context == null) {
            throw new IllegalStateException("Please call SHttp.init(this) in Application to initialize!");
        }
        return context;
    }

    public static OkHttpClient getOkHttpClient() {
        if (okHttpClient == null) {
            okHttpClient = getOkHttpBuilder().build();
        }
        return okHttpClient;
    }

    public static Retrofit.Builder getRetrofitBuilder() {
        if (retrofitBuilder == null) {
            throw new IllegalStateException("Please call SHttp.init(this) in Application to initialize!");
        }
        return retrofitBuilder;
    }

    public static OkHttpClient.Builder getOkHttpBuilder() {
        if (okHttpBuilder == null) {
            throw new IllegalStateException("Please call SHttp.init(this) in Application to initialize!");
        }
        return okHttpBuilder;
    }

    /**
     * GET请求
     */
    public static GetRequest GET(String suffixUrl) {
        return new GetRequest(suffixUrl);
    }

    /**
     * POST请求
     */
    public static PostRequest POST(String suffixUrl) {
        return new PostRequest(suffixUrl);
    }

    /**
     * HEAD请求
     */
    public static HeadRequest HEAD(String suffixUrl) {
        return new HeadRequest(suffixUrl);
    }

    /**
     * OPTIONS请求
     */
    public static OptionsRequest OPTIONS(String suffixUrl) {
        return new OptionsRequest(suffixUrl);
    }

    /**
     * PUT请求
     */
    public static PutRequest PUT(String suffixUrl) {
        return new PutRequest(suffixUrl);
    }

    /**
     * PATCH请求
     */
    public static PatchRequest PATCH(String suffixUrl) {
        return new PatchRequest(suffixUrl);
    }


    /**
     * DELETE请求
     */
    public static DeleteRequest DELETE(String suffixUrl) {
        return new DeleteRequest(suffixUrl);
    }

    /**
     * 根据Tag取消请求
     */
    public static void cancelTag(Object tag) {
        ApiManager.get().cancel(tag);
    }

    /**
     * 取消所有请求
     */
    public static void cancelAll() {
        ApiManager.get().cancelAll();
    }
}
