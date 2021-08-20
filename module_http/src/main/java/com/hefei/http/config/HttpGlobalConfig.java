package com.hefei.http.config;

import com.hefei.http.ViseConfig;
import com.hefei.http.ViseHttp;
import com.hefei.http.core.ApiCookie;
import com.hefei.http.interceptor.GzipRequestInterceptor;
import com.hefei.http.interceptor.OfflineCacheInterceptor;
import com.hefei.http.interceptor.OnlineCacheInterceptor;
import com.hefei.http.mode.ApiHost;

import java.io.File;
import java.net.Proxy;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;

import okhttp3.Cache;
import okhttp3.Call.Factory;
import okhttp3.ConnectionPool;
import okhttp3.Interceptor;
import retrofit2.CallAdapter;
import retrofit2.Converter;

/**
 * @Description: 请求全局配置
 * @author: <a href="http://www.xiaoyaoyou1212.com">DAWI</a>
 * @date: 2017-04-28 17:17
 */
public class HttpGlobalConfig {

    /* Call适配器工厂 */
    private CallAdapter.Factory callAdapterFactory;
    /* 转换工厂 */
    private Converter.Factory converterFactory;
    /* Call工厂 */
    private Factory callFactory;
    /* SSL工厂 */
    private SSLSocketFactory sslSocketFactory;
    /* 主机域名验证 */
    private HostnameVerifier hostnameVerifier;
    /* 连接池 */
    private ConnectionPool connectionPool;

    /* 基础域名 */
    private String baseUrl;

    /* 请求参数 */
    private Map<String, String> globalParams = new LinkedHashMap<>();
    /* 请求头 */
    private Map<String, String> globalHeaders = new LinkedHashMap<>();

    /* 是否使用Http缓存 */
    private boolean isHttpCache;
    /* Http缓存对象 */
    private Cache httpCache;
    /* Http缓存路径 */
    private File httpCacheDirectory;

    /* 是否使用Cookie */
    private boolean isCookie;
    /* Cookie配置 */
    private ApiCookie apiCookie;

    /* 请求失败重试次数 */
    private int retryCount;
    /* 请求失败重试间隔时间 */
    private int retryDelayMillis;

    private static HttpGlobalConfig instance;

    private HttpGlobalConfig() {
    }

    public static HttpGlobalConfig getInstance() {
        if (instance == null) {
            synchronized (HttpGlobalConfig.class) {
                if (instance == null) {
                    instance = new HttpGlobalConfig();
                }
            }
        }
        return instance;
    }

    /**
     * 设置CallAdapter工厂
     */
    public HttpGlobalConfig callAdapterFactory(CallAdapter.Factory factory) {
        this.callAdapterFactory = factory;
        return this;
    }

    /**
     * 设置转换工厂
     */
    public HttpGlobalConfig converterFactory(Converter.Factory factory) {
        this.converterFactory = factory;
        return this;
    }

    /**
     * 设置Call的工厂
     */
    public HttpGlobalConfig callFactory(Factory factory) {
        this.callFactory = checkNotNull(factory, "factory == null");
        return this;
    }

    /**
     * 设置SSL工厂
     */
    public HttpGlobalConfig SSLSocketFactory(SSLSocketFactory sslSocketFactory) {
        this.sslSocketFactory = sslSocketFactory;
        return this;
    }

    /**
     * 设置主机验证机制
     */
    public HttpGlobalConfig hostnameVerifier(HostnameVerifier hostnameVerifier) {
        this.hostnameVerifier = hostnameVerifier;
        return this;
    }

    /**
     * 设置连接池
     */
    public HttpGlobalConfig connectionPool(ConnectionPool connectionPool) {
        this.connectionPool = checkNotNull(connectionPool, "connectionPool == null");
        return this;
    }

    /**
     * 设置请求baseUrl
     */
    public HttpGlobalConfig baseUrl(String baseUrl) {
        this.baseUrl = checkNotNull(baseUrl, "baseUrl == null");
        ApiHost.setHost(this.baseUrl);
        return this;
    }

    /**
     * 设置请求头部
     */
    public HttpGlobalConfig globalHeaders(Map<String, String> globalHeaders) {
        if (globalHeaders != null) {
            this.globalHeaders = globalHeaders;
        }
        return this;
    }

    /**
     * 设置请求参数
     */
    public HttpGlobalConfig globalParams(Map<String, String> globalParams) {
        if (globalParams != null) {
            this.globalParams = globalParams;
        }
        return this;
    }

    /**
     * 设置HTTP缓存
     */
    public HttpGlobalConfig httpCache(Cache httpCache) {
        this.httpCache = httpCache;
        return this;
    }

    /**
     * 设置是否添加HTTP缓存
     */
    public HttpGlobalConfig setHttpCache(boolean isHttpCache) {
        this.isHttpCache = isHttpCache;
        return this;
    }

    /**
     * 设置HTTP缓存路径
     */
    public HttpGlobalConfig setHttpCacheDirectory(File httpCacheDirectory) {
        this.httpCacheDirectory = httpCacheDirectory;
        return this;
    }

    /**
     * 设置Cookie管理
     */
    public HttpGlobalConfig apiCookie(ApiCookie cookie) {
        this.apiCookie = checkNotNull(cookie, "cookieManager == null");
        return this;
    }

    /**
     * 设置是否添加Cookie
     */
    public HttpGlobalConfig setCookie(boolean isCookie) {
        this.isCookie = isCookie;
        return this;
    }

    /**
     * 设置请求失败重试次数
     */
    public HttpGlobalConfig retryCount(int retryCount) {
        this.retryCount = retryCount;
        return this;
    }

    /**
     * 设置请求失败重试间隔时间
     */
    public HttpGlobalConfig retryDelayMillis(int retryDelayMillis) {
        this.retryDelayMillis = retryDelayMillis;
        return this;
    }

    /**
     * 设置代理
     */
    public HttpGlobalConfig proxy(Proxy proxy) {
        ViseHttp.getOkHttpBuilder().proxy(checkNotNull(proxy, "proxy == null"));
        return this;
    }

    /**
     * 设置连接超时时间（秒）
     */
    public HttpGlobalConfig connectTimeout(int timeout) {
        return connectTimeout(timeout, TimeUnit.SECONDS);
    }

    /**
     * 设置读取超时时间（秒）
     */
    public HttpGlobalConfig readTimeout(int timeout) {
        return readTimeout(timeout, TimeUnit.SECONDS);
    }

    /**
     * 设置写入超时时间（秒）
     */
    public HttpGlobalConfig writeTimeout(int timeout) {
        return writeTimeout(timeout, TimeUnit.SECONDS);
    }

    /**
     * 设置连接超时时间
     */
    public HttpGlobalConfig connectTimeout(int timeout, TimeUnit unit) {
        if (timeout > -1) {
            ViseHttp.getOkHttpBuilder().connectTimeout(timeout, unit);
        } else {
            ViseHttp.getOkHttpBuilder().connectTimeout(ViseConfig.DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        }
        return this;
    }

    /**
     * 设置写入超时时间
     */
    public HttpGlobalConfig writeTimeout(int timeout, TimeUnit unit) {
        if (timeout > -1) {
            ViseHttp.getOkHttpBuilder().writeTimeout(timeout, unit);
        } else {
            ViseHttp.getOkHttpBuilder().writeTimeout(ViseConfig.DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        }
        return this;
    }

    /**
     * 设置读取超时时间
     */
    public HttpGlobalConfig readTimeout(int timeout, TimeUnit unit) {
        if (timeout > -1) {
            ViseHttp.getOkHttpBuilder().readTimeout(timeout, unit);
        } else {
            ViseHttp.getOkHttpBuilder().readTimeout(ViseConfig.DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        }
        return this;
    }

    /**
     * 设置拦截器
     */
    public HttpGlobalConfig interceptor(Interceptor interceptor) {
        ViseHttp.getOkHttpBuilder().addInterceptor(checkNotNull(interceptor, "interceptor == null"));
        return this;
    }

    /**
     * 设置网络拦截器
     */
    public HttpGlobalConfig networkInterceptor(Interceptor interceptor) {
        ViseHttp.getOkHttpBuilder().addNetworkInterceptor(checkNotNull(interceptor, "interceptor == null"));
        return this;
    }

    /**
     * 使用POST方式是否需要进行GZIP压缩，服务器不支持则不设置
     */
    public HttpGlobalConfig postGzipInterceptor() {
        interceptor(new GzipRequestInterceptor());
        return this;
    }

    /**
     * 设置在线缓存，主要针对网路请求过程进行缓存
     */
    public HttpGlobalConfig cacheOnline(Cache httpCache) {
        networkInterceptor(new OnlineCacheInterceptor());
        this.httpCache = httpCache;
        return this;
    }

    /**
     * 设置在线缓存，主要针对网路请求过程进行缓存
     */
    public HttpGlobalConfig cacheOnline(Cache httpCache, final int cacheControlValue) {
        networkInterceptor(new OnlineCacheInterceptor(cacheControlValue));
        this.httpCache = httpCache;
        return this;
    }

    /**
     * 设置离线缓存，主要针对网路请求过程进行缓存
     */
    public HttpGlobalConfig cacheOffline(Cache httpCache) {
        networkInterceptor(new OfflineCacheInterceptor(ViseHttp.getContext()));
        interceptor(new OfflineCacheInterceptor(ViseHttp.getContext()));
        this.httpCache = httpCache;
        return this;
    }

    /**
     * 设置离线缓存，主要针对网路请求过程进行缓存
     */
    public HttpGlobalConfig cacheOffline(Cache httpCache, final int cacheControlValue) {
        networkInterceptor(new OfflineCacheInterceptor(ViseHttp.getContext(), cacheControlValue));
        interceptor(new OfflineCacheInterceptor(ViseHttp.getContext(), cacheControlValue));
        this.httpCache = httpCache;
        return this;
    }

    public CallAdapter.Factory getCallAdapterFactory() {
        return callAdapterFactory;
    }

    public Converter.Factory getConverterFactory() {
        return converterFactory;
    }

    public Factory getCallFactory() {
        return callFactory;
    }

    public SSLSocketFactory getSslSocketFactory() {
        return sslSocketFactory;
    }

    public HostnameVerifier getHostnameVerifier() {
        return hostnameVerifier;
    }

    public ConnectionPool getConnectionPool() {
        return connectionPool;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public Map<String, String> getGlobalParams() {
        return globalParams;
    }

    public Map<String, String> getGlobalHeaders() {
        return globalHeaders;
    }

    public boolean isHttpCache() {
        return isHttpCache;
    }

    public Cache getHttpCache() {
        return httpCache;
    }

    public File getHttpCacheDirectory() {
        return httpCacheDirectory;
    }

    public boolean isCookie() {
        return isCookie;
    }

    public ApiCookie getApiCookie() {
        return apiCookie;
    }

    public int getRetryCount() {
        if (retryCount < 0) {
            retryCount = ViseConfig.DEFAULT_RETRY_COUNT;
        }
        return retryCount;
    }

    public int getRetryDelayMillis() {
        if (retryDelayMillis < 0) {
            retryDelayMillis = ViseConfig.DEFAULT_RETRY_DELAY_MILLIS;
        }
        return retryDelayMillis;
    }

    private <T> T checkNotNull(T t, String message) {
        if (t == null) {
            throw new NullPointerException(message);
        }
        return t;
    }
}
