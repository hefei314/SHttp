package com.hefei.retrofit.interceptor;

import androidx.annotation.NonNull;

import com.hefei.retrofit.body.UploadProgressRequestBody;
import com.hefei.retrofit.callback.UCallback;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * <pre>
 *     author: hefei
 *     time  : 2021/07/28
 *     desc  : 上传进度拦截
 * </pre>
 */
public class UploadProgressInterceptor implements Interceptor {

    private UCallback callback;

    public UploadProgressInterceptor(UCallback callback) {
        this.callback = callback;
        if (callback == null) {
            throw new NullPointerException("this callback must not null.");
        }
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request originalRequest = chain.request();
        if (originalRequest.body() == null) {
            return chain.proceed(originalRequest);
        }
        Request progressRequest = originalRequest.newBuilder()
                .method(originalRequest.method(), new UploadProgressRequestBody(originalRequest.body(), callback))
                .build();
        return chain.proceed(progressRequest);
    }
}
