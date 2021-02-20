package com.hefei.module_http.config;

import com.hefei.module_http.SHttp;
import com.hefei.module_http.core.ApiCookie;
import com.hefei.module_http.interceptor.NoCacheInterceptor;
import com.hefei.module_http.interceptor.OfflineCacheInterceptor;
import com.hefei.module_http.interceptor.OnlineCacheInterceptor;
import com.hefei.module_http.mode.SHttpHeaders;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.ConnectionPool;
import okhttp3.Interceptor;
import retrofit2.CallAdapter;
import retrofit2.Converter;

/**
 * <pre>
 *     author: hefei
 *     time  : 2021/01/29
 *     desc  : 全局配置
 * </pre>
 */
public class SHttpGlobalConfig {

    /* 基础域名 */
    private String baseUrl;

    /* 读取超时时间 */
    private long readTimeOut;
    /* 写入超时时间 */
    private long writeTimeOut;
    /* 连接超时时间 */
    private long connectTimeOut;

    /* 重试次数 */
    private int retryCount;
    /* 重试间隔 */
    private long retryDelayMillis;

    /* 是否使用Cookie */
    private boolean isCookie;
    private ApiCookie apiCookie;

    /* 是否使用Http缓存 */
    private boolean isHttpCache;
    private Cache httpCache;
    private File httpCacheDirectory;

    /* 连接池 */
    private ConnectionPool connectionPool;

    /* 全局请求头 */
    private SHttpHeaders globalHeaders = new SHttpHeaders();
    /* 全局请求参数 */
    private Map<String, String> globalParams = new LinkedHashMap<>();

    /* 全局请求拦截器 */
    protected List<Interceptor> globalInterceptors = new ArrayList<>();
    /* 全局请求网络拦截器 */
    protected List<Interceptor> globalNetworkInterceptors = new ArrayList<>();

    /* Call工厂 */
    private Call.Factory callFactory;
    /* 转换工厂 */
    private Converter.Factory converterFactory;
    /* Call适配器工厂 */
    private CallAdapter.Factory callAdapterFactory;

    private static SHttpGlobalConfig instance;

    private SHttpGlobalConfig() {
    }

    public static SHttpGlobalConfig getInstance() {
        if (instance == null) {
            synchronized (SHttpGlobalConfig.class) {
                if (instance == null) {
                    instance = new SHttpGlobalConfig();
                }
            }
        }
        return instance;
    }

    /**
     * 设置基础域名
     */
    public SHttpGlobalConfig baseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }

    /**
     * 设置读取超时时间(秒)
     */
    public SHttpGlobalConfig readTimeOut(int readTimeOut) {
        this.readTimeOut = readTimeOut;
        return this;
    }

    /**
     * 设置写入超时时间(秒)
     */
    public SHttpGlobalConfig writeTimeOut(int writeTimeOut) {
        this.writeTimeOut = writeTimeOut;
        return this;
    }

    /**
     * 设置连接超时时间(秒)
     */
    public SHttpGlobalConfig connectTimeOut(int connectTimeOut) {
        this.connectTimeOut = connectTimeOut;
        return this;
    }

    /**
     * 设置请求失败重试次数
     */
    public SHttpGlobalConfig retryCount(int retryCount) {
        this.retryCount = retryCount;
        return this;
    }

    /**
     * 设置请求失败重试间隔时间
     */
    public SHttpGlobalConfig retryDelayMillis(long retryDelayMillis) {
        this.retryDelayMillis = retryDelayMillis;
        return this;
    }

    /**
     * 设置是否使用Cookie
     */
    public SHttpGlobalConfig setCookie(boolean isCookie) {
        this.isCookie = isCookie;
        return this;
    }

    /**
     * 设置Cookie管理
     */
    public SHttpGlobalConfig apiCookie(ApiCookie cookie) {
        this.apiCookie = cookie;
        return this;
    }

    /**
     * 设置是否使用Http缓存
     */
    public SHttpGlobalConfig setHttpCache(boolean httpCache) {
        this.isHttpCache = httpCache;
        return this;
    }

    /**
     * 设置HTTP缓存
     */
    public SHttpGlobalConfig httpCache(Cache httpCache) {
        this.httpCache = httpCache;
        return this;
    }

    /**
     * 设置HTTP缓存路径
     */
    public SHttpGlobalConfig setHttpCacheDirectory(File httpCacheDirectory) {
        this.httpCacheDirectory = httpCacheDirectory;
        return this;
    }

    /**
     * 设置不缓存
     */
    public SHttpGlobalConfig noCache() {
        networkInterceptor(new NoCacheInterceptor());
        interceptor(new NoCacheInterceptor());
        this.httpCache = null;
        return this;
    }

    /**
     * 设置在线缓存，主要针对网路请求过程进行缓存
     */
    public SHttpGlobalConfig cacheOnline(Cache httpCache) {
        networkInterceptor(new OnlineCacheInterceptor());
        this.httpCache = httpCache;
        return this;
    }

    /**
     * 设置在线缓存，主要针对网路请求过程进行缓存
     */
    public SHttpGlobalConfig cacheOnline(Cache httpCache, final int cacheControlValue) {
        networkInterceptor(new OnlineCacheInterceptor(cacheControlValue));
        this.httpCache = httpCache;
        return this;
    }

    /**
     * 设置离线缓存，主要针对网路请求过程进行缓存
     */
    public SHttpGlobalConfig cacheOffline(Cache httpCache) {
        networkInterceptor(new OfflineCacheInterceptor(SHttp.getContext()));
        interceptor(new OfflineCacheInterceptor(SHttp.getContext()));
        this.httpCache = httpCache;
        return this;
    }

    /**
     * 设置离线缓存，主要针对网路请求过程进行缓存
     */
    public SHttpGlobalConfig cacheOffline(Cache httpCache, final int cacheControlValue) {
        networkInterceptor(new OfflineCacheInterceptor(SHttp.getContext(), cacheControlValue));
        interceptor(new OfflineCacheInterceptor(SHttp.getContext(), cacheControlValue));
        this.httpCache = httpCache;
        return this;
    }

    /**
     * 设置连接池
     */
    public SHttpGlobalConfig connectionPool(ConnectionPool connectionPool) {
        if (connectionPool != null) {
            this.connectionPool = connectionPool;
        }
        return this;
    }

    /**
     * 设置请求头
     */
    public SHttpGlobalConfig globalHeaders(Map<String, String> globalHeaders) {
        if (globalHeaders != null) {
            this.globalHeaders.put(globalHeaders);
        }
        return this;
    }

    /**
     * 设置请求参数
     */
    public SHttpGlobalConfig globalParams(Map<String, String> globalParams) {
        if (globalParams != null) {
            this.globalParams.putAll(globalParams);
        }
        return this;
    }

    /**
     * 设置全局拦截器
     */
    public SHttpGlobalConfig interceptor(Interceptor interceptor) {
        if (interceptor != null) {
            SHttp.getOkHttpBuilder().addInterceptor(interceptor);
        }
        return this;
    }

    /**
     * 设置全局网络拦截器
     */
    public SHttpGlobalConfig networkInterceptor(Interceptor interceptor) {
        if (interceptor != null) {
            SHttp.getOkHttpBuilder().addNetworkInterceptor(interceptor);
        }
        return this;
    }

    /**
     * 设置Call工厂
     */
    public SHttpGlobalConfig callFactory(Call.Factory factory) {
        this.callFactory = factory;
        return this;
    }

    /**
     * 设置转换工厂
     */
    public SHttpGlobalConfig converterFactory(Converter.Factory factory) {
        this.converterFactory = factory;
        return this;
    }

    /**
     * 设置Call适配器工厂
     */
    public SHttpGlobalConfig callAdapterFactory(CallAdapter.Factory factory) {
        this.callAdapterFactory = factory;
        return this;
    }

    //--------------------------------------------GET--------------------------------------------//

    public String getBaseUrl() {
        return baseUrl;
    }

    public long getReadTimeOut() {
        if (readTimeOut <= 0) {
            readTimeOut = SHttpConstants.DEFAULT_TIMEOUT;
        }
        return readTimeOut;
    }

    public long getWriteTimeOut() {
        if (writeTimeOut <= 0) {
            writeTimeOut = SHttpConstants.DEFAULT_TIMEOUT;
        }
        return writeTimeOut;
    }

    public long getConnectTimeOut() {
        if (connectTimeOut <= 0) {
            connectTimeOut = SHttpConstants.DEFAULT_TIMEOUT;
        }
        return connectTimeOut;
    }

    public int getRetryCount() {
        if (retryCount <= 0) {
            retryCount = SHttpConstants.DEFAULT_RETRY_COUNT;
        }
        return retryCount;
    }

    public long getRetryDelayMillis() {
        if (retryDelayMillis <= 0) {
            retryDelayMillis = SHttpConstants.DEFAULT_RETRY_DELAY_MILLIS;
        }
        return retryDelayMillis;
    }

    public boolean isCookie() {
        return isCookie;
    }

    public ApiCookie getApiCookie() {
        return apiCookie;
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

    public ConnectionPool getConnectionPool() {
        return connectionPool;
    }

    public SHttpHeaders getGlobalHeaders() {
        return globalHeaders;
    }

    public Map<String, String> getGlobalParams() {
        return globalParams;
    }

    public List<Interceptor> getGlobalInterceptors() {
        return globalInterceptors;
    }

    public List<Interceptor> getGlobalNetworkInterceptors() {
        return globalNetworkInterceptors;
    }

    public Call.Factory getCallFactory() {
        return callFactory;
    }

    public Converter.Factory getConverterFactory() {
        return converterFactory;
    }

    public CallAdapter.Factory getCallAdapterFactory() {
        return callAdapterFactory;
    }
}
