package com.hefei.shttp;

import android.app.Application;

import com.blankj.utilcode.util.Utils;
import com.hefei.retrofit.HttpUtils;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

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
    }

    private void initHttp() {
        HttpUtils.init(this);
        HttpUtils.config()
                .baseUrl(THIS_HOST)
                .readTimeout(30)
                .writeTimeout(30)
                .connectTimeout(30);
    }
}
