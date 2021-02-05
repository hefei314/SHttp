package com.hefei.module_http.func;

import com.hefei.module_http.exception.ApiException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.functions.Function;

/**
 * <pre>
 *     author: hefei
 *     time  : 2021/02/02
 *     desc  : 重试机制
 * </pre>
 */
public class ApiRetryFunc implements Function<Observable<? extends Throwable>, Observable<?>> {

    private int retryCount;

    private final int maxRetryCount;
    private final long retryDelayMillis;

    public ApiRetryFunc(int maxRetryCount, long retryDelayMillis) {
        this.maxRetryCount = maxRetryCount;
        this.retryDelayMillis = retryDelayMillis;
    }

    @Override
    public Observable<?> apply(Observable<? extends Throwable> observable) {
        return observable
                .flatMap((Function<Throwable, ObservableSource<?>>) throwable -> {
                    if (++retryCount <= maxRetryCount && (throwable instanceof SocketTimeoutException
                            || throwable instanceof ConnectException)) {
                        return Observable.timer(retryDelayMillis, TimeUnit.MILLISECONDS);
                    }
                    return Observable.error(ApiException.handleException(throwable));
                });
    }
}
