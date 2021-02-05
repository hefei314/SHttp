package com.hefei.module_http.subscriber;

import com.hefei.module_http.exception.ApiException;
import com.hefei.module_http.mode.ApiCode;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.observers.DisposableObserver;

/**
 * <pre>
 *     author: hefei
 *     time  : 2021/02/02
 *     desc  : API统一订阅者
 * </pre>
 */
abstract class ApiSubscriber<T> extends DisposableObserver<T> {

    ApiSubscriber() {

    }

    @Override
    public void onError(@NonNull Throwable e) {
        if (e instanceof ApiException) {
            onError((ApiException) e);
        } else {
            onError(new ApiException(e, ApiCode.Request.UNKNOWN));
        }
    }

    protected abstract void onError(ApiException e);
}
