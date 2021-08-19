package com.hefei.retrofit.config;

import com.hefei.retrofit.manager.HttpManager;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import retrofit2.Converter;

/**
 * <pre>
 *     author: hefei
 *     time  : 2021/08/13
 *     desc  :
 * </pre>
 */
public class HttpGlobalConfig {

    // 默认超时时间（秒）
    public static final int DEFAULT_TIMEOUT = 60;

    // 基础域名
    private String baseUrl;
    // 全局请求参数
    private Map<String, String> globalParams = new LinkedHashMap<>();
    // 全局请求头
    private Map<String, String> globalHeaders = new LinkedHashMap<>();
    // 转换工厂
    private Converter.Factory converterFactory;

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
     * 设置请求baseUrl
     */
    public HttpGlobalConfig baseUrl(String baseUrl) {
        this.baseUrl = checkNotNull(baseUrl, "baseUrl == null");
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
            HttpManager.getOkHttpBuilder().connectTimeout(timeout, unit);
        } else {
            HttpManager.getOkHttpBuilder().connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        }
        return this;
    }

    /**
     * 设置写入超时时间
     */
    public HttpGlobalConfig writeTimeout(int timeout, TimeUnit unit) {
        if (timeout > -1) {
            HttpManager.getOkHttpBuilder().writeTimeout(timeout, unit);
        } else {
            HttpManager.getOkHttpBuilder().writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        }
        return this;
    }

    /**
     * 设置读取超时时间
     */
    public HttpGlobalConfig readTimeout(int timeout, TimeUnit unit) {
        if (timeout > -1) {
            HttpManager.getOkHttpBuilder().readTimeout(timeout, unit);
        } else {
            HttpManager.getOkHttpBuilder().readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        }
        return this;
    }

    /**
     * 设置拦截器
     */
    public HttpGlobalConfig interceptor(Interceptor interceptor) {
        HttpManager.getOkHttpBuilder().addInterceptor(checkNotNull(interceptor, "interceptor == null"));
        return this;
    }

    /**
     * 设置网络拦截器
     */
    public HttpGlobalConfig networkInterceptor(Interceptor interceptor) {
        HttpManager.getOkHttpBuilder().addNetworkInterceptor(checkNotNull(interceptor, "interceptor == null"));
        return this;
    }

    /**
     * 设置转换工厂
     */
    public HttpGlobalConfig converterFactory(Converter.Factory factory) {
        this.converterFactory = factory;
        return this;
    }

    private <T> T checkNotNull(T t, String message) {
        if (t == null) {
            throw new NullPointerException(message);
        }
        return t;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public Map<String, String> getGlobalHeaders() {
        return globalHeaders;
    }

    public Map<String, String> getGlobalParams() {
        return globalParams;
    }

    public Converter.Factory getConverterFactory() {
        return converterFactory;
    }
}
