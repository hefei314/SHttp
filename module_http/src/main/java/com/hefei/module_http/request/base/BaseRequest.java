package com.hefei.module_http.request.base;

import android.text.TextUtils;
import android.util.Log;

import com.hefei.module_http.SHttp;
import com.hefei.module_http.config.SHttpConstants;
import com.hefei.module_http.config.SHttpGlobalConfig;
import com.hefei.module_http.core.ApiCookie;
import com.hefei.module_http.interceptor.HeadersInterceptor;
import com.hefei.module_http.mode.ApiHost;
import com.hefei.module_http.mode.SHttpHeaders;

import java.io.File;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.ConnectionPool;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;

/**
 * <pre>
 *     author: hefei
 *     time  : 2021/01/29
 *     desc  : 请求基类
 * </pre>
 */
@SuppressWarnings("unchecked")
public abstract class BaseRequest<R extends BaseRequest> {

    public static final String TAG = BaseRequest.class.getSimpleName();

    protected SHttpGlobalConfig sHttpGlobalConfig;

    /* 基础域名 */
    protected String baseUrl;

    /* 读取超时时间 */
    protected long readTimeOut;
    /* 写入超时时间 */
    protected long writeTimeOut;
    /* 连接超时时间 */
    protected long connectTimeOut;

    /* 是否使用Http缓存 */
    protected boolean isHttpCache;
    protected long httpCacheMaxAge;

    /* 请求标签 */
    protected String tag;

    /* 请求头 */
    protected SHttpHeaders headers = new SHttpHeaders();

    /* 局部请求拦截器 */
    protected List<Interceptor> interceptors = new ArrayList<>();
    /* 局部请求网络拦截器 */
    protected List<Interceptor> networkInterceptors = new ArrayList<>();

    protected Retrofit retrofit;

    /**
     * 设置基础域名
     */
    public R baseUrl(String baseUrl) {
        if (!TextUtils.isEmpty(baseUrl)) {
            this.baseUrl = baseUrl;
        }
        return (R) this;
    }

    /**
     * 设置读取超时时间(秒)
     */
    public R readTimeOut(long readTimeOut) {
        this.readTimeOut = readTimeOut;
        return (R) this;
    }

    /**
     * 设置写入超时时间(秒)
     */
    public R writeTimeOut(long writeTimeOut) {
        this.writeTimeOut = writeTimeOut;
        return (R) this;
    }

    /**
     * 设置连接超时时间(秒)
     */
    public R connectTimeOut(long connectTimeOut) {
        this.connectTimeOut = connectTimeOut;
        return (R) this;
    }

    /**
     * 设置是否使用Http缓存
     */
    public R setHttpCache(boolean httpCache) {
        isHttpCache = httpCache;
        return (R) this;
    }

    /**
     * 添加请求头
     */
    public R addHeader(String name, String value) {
        this.headers.put(name, value);
        return (R) this;
    }

    /**
     * 添加请求头
     */
    public R addHeaders(Map<String, String> headers) {
        this.headers.put(headers);
        return (R) this;
    }

    /**
     * 移除请求头
     */
    public R removeHeader(String name) {
        this.headers.remove(name);
        return (R) this;
    }

    /**
     * 移除所有请求头
     */
    public R removeAllHeaders() {
        this.headers.clear();
        return (R) this;
    }

    /**
     * 设置局部拦截器
     */
    public R interceptor(Interceptor interceptor) {
        if (interceptor != null) {
            interceptors.add(interceptor);
        }
        return (R) this;
    }

    /**
     * 设置局部网络拦截器
     */
    public R networkInterceptor(Interceptor interceptor) {
        if (interceptor != null) {
            networkInterceptors.add(interceptor);
        }
        return (R) this;
    }

    /**
     * 加载局部配置
     */
    protected void loadLocalConfig() {
        OkHttpClient.Builder newBuilder = SHttp.getOkHttpClient().newBuilder();

        if (readTimeOut > 0) {
            newBuilder.readTimeout(readTimeOut, TimeUnit.SECONDS);
        }

        if (writeTimeOut > 0) {
            newBuilder.writeTimeout(writeTimeOut, TimeUnit.SECONDS);
        }

        if (connectTimeOut > 0) {
            newBuilder.connectTimeout(connectTimeOut, TimeUnit.SECONDS);
        }

        if (isHttpCache) {
            try {
                if (sHttpGlobalConfig.getHttpCacheDirectory() == null) {
                    sHttpGlobalConfig.setHttpCacheDirectory(new File(SHttp.getContext().getCacheDir(), SHttpConstants.CACHE_HTTP_DIR));
                }

                if (sHttpGlobalConfig.getHttpCache() == null) {
                    sHttpGlobalConfig.httpCache(new Cache(sHttpGlobalConfig.getHttpCacheDirectory(), SHttpConstants.CACHE_MAX_SIZE));
                }
                newBuilder.cache(sHttpGlobalConfig.getHttpCache());

                sHttpGlobalConfig.cacheOnline(sHttpGlobalConfig.getHttpCache());
                sHttpGlobalConfig.cacheOffline(sHttpGlobalConfig.getHttpCache());
            } catch (Exception e) {
                Log.e(TAG, "Could not create http cache", e);
            }
        } else {
            sHttpGlobalConfig.noCache();
        }

        if (sHttpGlobalConfig.getGlobalHeaders() != null) {
            headers.put(sHttpGlobalConfig.getGlobalHeaders());
        }

        if (!headers.isEmpty()) {
            newBuilder.addInterceptor(new HeadersInterceptor(headers.headersMap));
        }

        if (!interceptors.isEmpty()) {
            for (Interceptor interceptor : interceptors) {
                newBuilder.addInterceptor(interceptor);
            }
        }

        if (!networkInterceptors.isEmpty()) {
            for (Interceptor interceptor : networkInterceptors) {
                newBuilder.addNetworkInterceptor(interceptor);
            }
        }

        if (baseUrl != null) {
            Retrofit.Builder newRetrofitBuilder = new Retrofit.Builder();
            newRetrofitBuilder.baseUrl(baseUrl);
            if (sHttpGlobalConfig.getCallFactory() != null) {
                newRetrofitBuilder.callFactory(sHttpGlobalConfig.getCallFactory());
            }
            if (sHttpGlobalConfig.getConverterFactory() != null) {
                newRetrofitBuilder.addConverterFactory(sHttpGlobalConfig.getConverterFactory());
            }
            if (sHttpGlobalConfig.getCallAdapterFactory() != null) {
                newRetrofitBuilder.addCallAdapterFactory(sHttpGlobalConfig.getCallAdapterFactory());
            }
            newRetrofitBuilder.client(newBuilder.build());
            retrofit = newRetrofitBuilder.build();
        } else {
            SHttp.getRetrofitBuilder().client(newBuilder.build());
            retrofit = SHttp.getRetrofitBuilder().build();
        }
    }

    /**
     * 加载全局配置
     */
    protected void loadGlobalConfig() {
        sHttpGlobalConfig = SHttp.CONFIG();

        if (sHttpGlobalConfig.getBaseUrl() == null) {
            sHttpGlobalConfig.baseUrl(ApiHost.getHost());
        }
        SHttp.getRetrofitBuilder().baseUrl(sHttpGlobalConfig.getBaseUrl());

        SHttp.getOkHttpBuilder().readTimeout(sHttpGlobalConfig.getReadTimeOut(), TimeUnit.SECONDS);
        SHttp.getOkHttpBuilder().writeTimeout(sHttpGlobalConfig.getWriteTimeOut(), TimeUnit.SECONDS);
        SHttp.getOkHttpBuilder().connectTimeout(sHttpGlobalConfig.getConnectTimeOut(), TimeUnit.SECONDS);

        if (sHttpGlobalConfig.isCookie()) {
            if (sHttpGlobalConfig.getApiCookie() == null) {
                sHttpGlobalConfig.apiCookie(new ApiCookie(SHttp.getContext()));
            }
            SHttp.getOkHttpBuilder().cookieJar(sHttpGlobalConfig.getApiCookie());
        }

        if (sHttpGlobalConfig.isHttpCache()) {
            try {
                if (sHttpGlobalConfig.getHttpCacheDirectory() == null) {
                    sHttpGlobalConfig.setHttpCacheDirectory(new File(SHttp.getContext().getCacheDir(), SHttpConstants.CACHE_HTTP_DIR));
                }

                if (sHttpGlobalConfig.getHttpCache() == null) {
                    sHttpGlobalConfig.httpCache(new Cache(sHttpGlobalConfig.getHttpCacheDirectory(), SHttpConstants.CACHE_MAX_SIZE));
                }
                SHttp.getOkHttpBuilder().cache(sHttpGlobalConfig.getHttpCache());

                sHttpGlobalConfig.cacheOnline(sHttpGlobalConfig.getHttpCache());
                sHttpGlobalConfig.cacheOffline(sHttpGlobalConfig.getHttpCache());
            } catch (Exception e) {
                Log.e(TAG, "Could not create http cache", e);
            }
        }

        if (sHttpGlobalConfig.getConnectionPool() == null) {
            sHttpGlobalConfig.connectionPool(new ConnectionPool(
                    SHttpConstants.DEFAULT_MAX_IDLE_CONNECTIONS,
                    SHttpConstants.DEFAULT_KEEP_ALIVE_DURATION,
                    TimeUnit.SECONDS)
            );
        }
        SHttp.getOkHttpBuilder().connectionPool(sHttpGlobalConfig.getConnectionPool());

        if (!sHttpGlobalConfig.getGlobalInterceptors().isEmpty()) {
            for (Interceptor interceptor : sHttpGlobalConfig.getGlobalInterceptors()) {
                SHttp.getOkHttpBuilder().addInterceptor(interceptor);
            }
        }

        if (!sHttpGlobalConfig.getGlobalNetworkInterceptors().isEmpty()) {
            for (Interceptor interceptor : sHttpGlobalConfig.getGlobalNetworkInterceptors()) {
                SHttp.getOkHttpBuilder().addNetworkInterceptor(interceptor);
            }
        }

        if (sHttpGlobalConfig.getCallFactory() != null) {
            SHttp.getRetrofitBuilder().callFactory(sHttpGlobalConfig.getCallFactory());
        }

        if (sHttpGlobalConfig.getConverterFactory() != null) {
            SHttp.getRetrofitBuilder().addConverterFactory(sHttpGlobalConfig.getConverterFactory());
        }

        if (sHttpGlobalConfig.getCallAdapterFactory() == null) {
            sHttpGlobalConfig.callAdapterFactory(RxJava3CallAdapterFactory.create());
        }
        SHttp.getRetrofitBuilder().addCallAdapterFactory(sHttpGlobalConfig.getCallAdapterFactory());

        // TODO: 2021/2/1 SSL主机验证
    }

    protected <T> Type getType(T t) {
        Type genType = t.getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        Type type = params[0];
        Type finalNeedType;
        if (params.length > 1) {
            if (!(type instanceof ParameterizedType))
                throw new IllegalStateException("没有填写泛型参数");
            finalNeedType = ((ParameterizedType) type).getActualTypeArguments()[0];
        } else {
            finalNeedType = type;
        }
        return finalNeedType;
    }
}
