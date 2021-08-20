package com.hefei.shttp;

import android.app.Application;

import com.blankj.utilcode.util.Utils;
import com.hefei.http.ViseConfig;
import com.hefei.http.ViseHttp;
import com.hefei.http.core.ApiCookie;
import com.hefei.http.interceptor.HttpLogInterceptor;
import com.hefei.http.interceptor.NoCacheInterceptor;
import com.hefei.retrofit.HttpUtils;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.util.HashMap;

import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * <pre>
 *     author: hefei
 *     time  : 2021/02/03
 *     desc  :
 * </pre>
 */
public class ThisApplication extends Application {

    public static final String THIS_HOST = "https://gank.io/api/v2/";

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Logger.addLogAdapter(new AndroidLogAdapter());
        }

        Utils.init(this);

        initHttp();

        initHttp2();
    }

    private void initHttp() {
        HttpUtils.init(this);
        HttpUtils.config()
                .baseUrl(THIS_HOST)
                .readTimeout(30)
                .writeTimeout(30)
                .connectTimeout(30);
    }

    private void initHttp2() {
        ViseHttp.init(this);
        ViseHttp.CONFIG()
                //配置请求主机地址
                .baseUrl(THIS_HOST)
                //配置全局请求参数
                .globalParams(new HashMap<String, String>())
                //配置读取超时时间，单位秒
                .readTimeout(30)
                //配置写入超时时间，单位秒
                .writeTimeout(30)
                //配置连接超时时间，单位秒
                .connectTimeout(30)
                //配置请求失败重试次数
                .retryCount(1)
                //配置请求失败重试间隔时间，单位毫秒
                .retryDelayMillis(1000)
                //配置是否使用cookie
                .setCookie(true)
                //配置自定义cookie
                .apiCookie(new ApiCookie(this))
                //配置是否使用OkHttp的默认缓存
                .setHttpCache(true)
                //配置OkHttp缓存路径
                .setHttpCacheDirectory(new File(ViseHttp.getContext().getCacheDir(), ViseConfig.CACHE_HTTP_DIR))
                //配置网络拦截器
                .networkInterceptor(new NoCacheInterceptor())
                //配置转换工厂
                .converterFactory(GsonConverterFactory.create())
                //配置适配器工厂
                .callAdapterFactory(RxJava3CallAdapterFactory.create())
                //配置日志拦截器
                .interceptor(new HttpLogInterceptor().setLevel(HttpLogInterceptor.Level.BODY));
    }
}
