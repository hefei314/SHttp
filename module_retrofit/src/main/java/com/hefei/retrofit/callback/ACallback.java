package com.hefei.retrofit.callback;

/**
 * <pre>
 *     author: hefei
 *     time  : 2021/07/29
 *     desc  :
 * </pre>
 */
public abstract class ACallback<T> {

    public abstract void onSuccess(T data);

    public abstract void onFail(String errMsg);

}
