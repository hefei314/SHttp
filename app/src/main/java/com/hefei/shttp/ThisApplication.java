package com.hefei.shttp;

import android.app.Application;

import com.hefei.module_http.SHttp;

import java.util.HashMap;

/**
 * <pre>
 *     author: hefei
 *     time  : 2021/02/03
 *     desc  :
 * </pre>
 */
public class ThisApplication extends Application {

    public static final String THIS_HOST = "";

    @Override
    public void onCreate() {
        super.onCreate();
        initHttp();
    }

    private void initHttp() {
        SHttp.init(this);
        SHttp.CONFIG()
                //配置请求主机地址
                .baseUrl(THIS_HOST)
                //配置全局请求头
                .globalHeaders(new HashMap<String, String>())
                //配置全局请求参数
                .globalParams(new HashMap<String, String>())
                //配置读取超时时间，单位秒
                .readTimeOut(30)
                //配置写入超时时间，单位秒
                .writeTimeOut(30)
                //配置连接超时时间，单位秒
                .connectTimeOut(30)
                //配置请求失败重试次数
                .retryCount(1)
                //配置请求失败重试间隔时间，单位毫秒
                .retryDelayMillis(1000);
    }
}
