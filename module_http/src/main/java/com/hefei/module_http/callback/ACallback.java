package com.hefei.module_http.callback;

/**
 * <pre>
 *     author: hefei
 *     time  : 2021/01/29
 *     desc  : 请求接口回调
 * </pre>
 */
public abstract class ACallback<T> {

    public abstract void onSuccess(T data);

    public abstract void onFail(int errCode, String errMsg);

}
