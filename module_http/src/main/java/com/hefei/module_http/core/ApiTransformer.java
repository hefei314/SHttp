package com.hefei.module_http.core;

import com.hefei.module_http.config.SHttpGlobalConfig;
import com.hefei.module_http.func.ApiFunc;
import com.hefei.module_http.func.ApiRetryFunc;

import java.lang.reflect.Type;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.ObservableTransformer;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * <pre>
 *     author: hefei
 *     time  : 2021/02/03
 *     desc  : 转换器
 * </pre>
 */
public class ApiTransformer {

    public static <T> ObservableTransformer<ResponseBody, T> norTransformer(Type type) {
        return apiResultObservable -> apiResultObservable
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new ApiFunc<T>(type))
                .retryWhen(new ApiRetryFunc(SHttpGlobalConfig.getInstance().getRetryCount(),
                        SHttpGlobalConfig.getInstance().getRetryDelayMillis()));
    }

    public static <T> ObservableTransformer<ResponseBody, T> norTransformer(Type type, int retryCount, long retryDelayMillis) {
        return apiResultObservable -> apiResultObservable
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new ApiFunc<T>(type))
                .retryWhen(new ApiRetryFunc(retryCount, retryDelayMillis));
    }

}
