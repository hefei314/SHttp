package com.hefei.module_http.interceptor;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.hefei.module_http.config.SHttpConstants;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * <pre>
 *     author: hefei
 *     time  : 2021/02/01
 *     desc  : 在线缓存拦截
 * </pre>
 */
public class OnlineCacheInterceptor implements Interceptor {

    private String cacheControlValue;

    public OnlineCacheInterceptor() {
        this(SHttpConstants.MAX_AGE_ONLINE);
    }

    public OnlineCacheInterceptor(int cacheControlValue) {
        this.cacheControlValue = String.format("max-age=%d", cacheControlValue);
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());
        String cacheControl = originalResponse.header("Cache-Control");
        if (TextUtils.isEmpty(cacheControl) || cacheControl.contains("no-store") || cacheControl.contains("no-cache") || cacheControl
                .contains("must-revalidate") || cacheControl.contains("max-age") || cacheControl.contains("max-stale")) {
            return originalResponse.newBuilder()
                    .header("Cache-Control", "public, " + cacheControlValue)
                    .removeHeader("Pragma")
                    .build();

        } else {
            return originalResponse;
        }
    }
}
