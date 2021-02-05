package com.hefei.module_http.request;

import com.hefei.module_http.callback.ACallback;
import com.hefei.module_http.core.ApiManager;
import com.hefei.module_http.core.ApiTransformer;
import com.hefei.module_http.request.base.BaseHttpRequest;
import com.hefei.module_http.subscriber.ApiCallbackSubscriber;

import java.lang.reflect.Type;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.observers.DisposableObserver;

/**
 * <pre>
 *     author: hefei
 *     time  : 2021/01/29
 *     desc  : Patch请求
 * </pre>
 */
public class PatchRequest extends BaseHttpRequest<PatchRequest> {

    public PatchRequest(String suffixUrl) {
        super(suffixUrl);
    }

    @Override
    protected <T> Observable<T> execute(Type type) {
        return apiService.patch(suffixUrl, params).compose(ApiTransformer.<T>norTransformer(type, retryCount, retryDelayMillis));
    }

    @Override
    protected <T> void execute(ACallback<T> callback) {
        DisposableObserver disposableObserver = new ApiCallbackSubscriber<T>(callback);
        if (tag != null) {
            ApiManager.get().add(tag, disposableObserver);
        }
        this.execute(getType(callback)).subscribe(disposableObserver);
    }
}
