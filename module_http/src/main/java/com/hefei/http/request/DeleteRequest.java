package com.hefei.http.request;

import com.hefei.http.ViseHttp;
import com.hefei.http.callback.ACallback;
import com.hefei.http.core.ApiManager;
import com.hefei.http.mode.CacheResult;
import com.hefei.http.subscriber.ApiCallbackSubscriber;

import java.lang.reflect.Type;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.observers.DisposableObserver;

/**
 * @Description: Delete请求
 * @author: <a href="http://www.xiaoyaoyou1212.com">DAWI</a>
 * @date: 2017-04-28 16:06
 */
public class DeleteRequest extends BaseHttpRequest<DeleteRequest> {

    public DeleteRequest(String suffixUrl) {
        super(suffixUrl);
    }

    @Override
    protected <T> Observable<T> execute(Type type) {
        return apiService.delete(suffixUrl, params).compose(this.<T>norTransformer(type));
    }

    @Override
    protected <T> Observable<CacheResult<T>> cacheExecute(Type type) {
        return this.<T>execute(type).compose(ViseHttp.getApiCache().<T>transformer(cacheMode, type));
    }

    @Override
    protected <T> void execute(ACallback<T> callback) {
        DisposableObserver disposableObserver = new ApiCallbackSubscriber(callback);
        if (super.tag != null) {
            ApiManager.get().add(super.tag, disposableObserver);
        }
        if (isLocalCache) {
            this.cacheExecute(getSubType(callback)).subscribe(disposableObserver);
        } else {
            this.execute(getType(callback)).subscribe(disposableObserver);
        }
    }
}
