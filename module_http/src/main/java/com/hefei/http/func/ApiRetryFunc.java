package com.hefei.http.func;

import com.hefei.http.exception.ApiException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.functions.Function;

/**
 * @Description: 重试机制
 * @author: <a href="http://www.xiaoyaoyou1212.com">DAWI</a>
 * @date: 2017-05-04 17:19
 */
public class ApiRetryFunc implements Function<Observable<? extends Throwable>, Observable<?>> {

    private final int maxRetries;
    private final int retryDelayMillis;
    private int retryCount;

    public ApiRetryFunc(int maxRetries, int retryDelayMillis) {
        this.maxRetries = maxRetries;
        this.retryDelayMillis = retryDelayMillis;
    }

    @Override
    public Observable<?> apply(Observable<? extends Throwable> observable) throws Exception {
        return observable
                .flatMap(new Function<Throwable, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(Throwable throwable) throws Exception {
                        if (++retryCount <= maxRetries && (throwable instanceof SocketTimeoutException
                                || throwable instanceof ConnectException)) {
                            return Observable.timer(retryDelayMillis, TimeUnit.MILLISECONDS);
                        }
                        return Observable.error(ApiException.handleException(throwable));
                    }
                });
    }
}
