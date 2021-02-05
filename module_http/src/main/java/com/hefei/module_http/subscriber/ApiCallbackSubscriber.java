package com.hefei.module_http.subscriber;

import com.hefei.module_http.callback.ACallback;
import com.hefei.module_http.exception.ApiException;

import io.reactivex.rxjava3.annotations.NonNull;

/**
 * <pre>
 *     author: hefei
 *     time  : 2021/02/02
 *     desc  : 包含回调的订阅者，如果订阅这个，上层在不使用订阅者的情况下可获得回调
 * </pre>
 */
public class ApiCallbackSubscriber<T> extends ApiSubscriber<T> {

    T data;

    ACallback<T> callBack;

    public ApiCallbackSubscriber(ACallback<T> callBack) {
        if (callBack == null) {
            throw new NullPointerException("this callback is null!");
        }
        this.callBack = callBack;
    }


    @Override
    protected void onError(ApiException e) {
        if (e == null) {
            callBack.onFail(-1, "This ApiException is Null.");
            return;
        }
        callBack.onFail(e.getCode(), e.getMessage());
    }

    @Override
    public void onNext(@NonNull T t) {
        this.data = t;
        callBack.onSuccess(t);
    }

    @Override
    public void onComplete() {

    }

    public T getData() {
        return data;
    }
}
