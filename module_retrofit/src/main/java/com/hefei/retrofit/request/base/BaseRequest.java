package com.hefei.retrofit.request.base;

import com.hefei.retrofit.HttpUtils;
import com.hefei.retrofit.callback.UCallback;
import com.hefei.retrofit.config.HttpGlobalConfig;
import com.hefei.retrofit.interceptor.HeadersInterceptor;
import com.hefei.retrofit.interceptor.UploadProgressInterceptor;
import com.hefei.retrofit.manager.HttpManager;
import com.hefei.retrofit.model.HttpHeaders;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * <pre>
 *     author: hefei
 *     time  : 2021/08/13
 *     desc  :
 * </pre>
 */
public abstract class BaseRequest<R extends BaseRequest> {

    protected HttpGlobalConfig httpGlobalConfig;

    protected Retrofit retrofit;

    protected String baseUrl;

    protected long readTimeOut;
    protected long writeTimeOut;
    protected long connectTimeOut;

    protected HttpHeaders headers = new HttpHeaders();

    protected List<Interceptor> interceptors = new ArrayList<>();
    protected List<Interceptor> networkInterceptors = new ArrayList<>();

    protected UCallback uploadCallback;

    /**
     * 设置基础域名，当前请求会替换全局域名
     */
    public R baseUrl(String baseUrl) {
        if (baseUrl != null) {
            this.baseUrl = baseUrl;
        }
        return (R) this;
    }

    /**
     * 添加请求头
     */
    public R addHeader(String headerKey, String headerValue) {
        this.headers.put(headerKey, headerValue);
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
    public R removeHeader(String headerKey) {
        this.headers.remove(headerKey);
        return (R) this;
    }

    /**
     * 设置请求头
     */
    public R headers(HttpHeaders headers) {
        if (headers != null) {
            this.headers = headers;
        }
        return (R) this;
    }

    /**
     * 设置连接超时时间（秒）
     */
    public R connectTimeOut(int connectTimeOut) {
        this.connectTimeOut = connectTimeOut;
        return (R) this;
    }

    /**
     * 设置读取超时时间（秒）
     */
    public R readTimeOut(int readTimeOut) {
        this.readTimeOut = readTimeOut;
        return (R) this;
    }

    /**
     * 设置写入超时时间（秒）
     */
    public R writeTimeOut(int writeTimeOut) {
        this.writeTimeOut = writeTimeOut;
        return (R) this;
    }

    /**
     * 局部设置拦截器
     */
    public R interceptor(Interceptor interceptor) {
        if (interceptor != null) {
            interceptors.add(interceptor);
        }
        return (R) this;
    }

    /**
     * 局部设置网络拦截器
     */
    public R networkInterceptor(Interceptor interceptor) {
        if (interceptor != null) {
            networkInterceptors.add(interceptor);
        }
        return (R) this;
    }

    /**
     * 生成局部配置
     */
    protected void generateLocalConfig() {
        OkHttpClient.Builder newBuilder = HttpManager.getOkHttpClient().newBuilder();

        if (httpGlobalConfig.getGlobalHeaders() != null) {
            headers.put(httpGlobalConfig.getGlobalHeaders());
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

        if (headers.headersMap.size() > 0) {
            newBuilder.addInterceptor(new HeadersInterceptor(headers.headersMap));
        }

        if (uploadCallback != null) {
            newBuilder.addNetworkInterceptor(new UploadProgressInterceptor(uploadCallback));
        }

        if (readTimeOut > 0) {
            newBuilder.readTimeout(readTimeOut, TimeUnit.SECONDS);
        }

        if (writeTimeOut > 0) {
            newBuilder.readTimeout(writeTimeOut, TimeUnit.SECONDS);
        }

        if (connectTimeOut > 0) {
            newBuilder.readTimeout(connectTimeOut, TimeUnit.SECONDS);
        }

        if (baseUrl != null) {
            Retrofit.Builder newRetrofitBuilder = new Retrofit.Builder();
            newRetrofitBuilder.baseUrl(baseUrl);
            if (httpGlobalConfig.getConverterFactory() != null) {
                newRetrofitBuilder.addConverterFactory(httpGlobalConfig.getConverterFactory());
            }
            newRetrofitBuilder.client(newBuilder.build());
            retrofit = newRetrofitBuilder.build();
        } else {
            HttpManager.getRetrofitBuilder().client(newBuilder.build());
            retrofit = HttpManager.getRetrofitBuilder().build();
        }
    }

    /**
     * 生成全局配置
     */
    protected void generateGlobalConfig() {
        httpGlobalConfig = HttpUtils.config();

        HttpManager.getRetrofitBuilder().baseUrl(httpGlobalConfig.getBaseUrl());

        if (httpGlobalConfig.getConverterFactory() == null) {
            httpGlobalConfig.converterFactory(GsonConverterFactory.create());
        }
        HttpManager.getRetrofitBuilder().addConverterFactory(httpGlobalConfig.getConverterFactory());

        HttpManager.getOkHttpBuilder().connectTimeout(HttpGlobalConfig.DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        HttpManager.getOkHttpBuilder().writeTimeout(HttpGlobalConfig.DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        HttpManager.getOkHttpBuilder().readTimeout(HttpGlobalConfig.DEFAULT_TIMEOUT, TimeUnit.SECONDS);
    }

}
