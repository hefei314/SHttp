package com.hefei.module_http.request.base;

import android.text.TextUtils;

import com.hefei.module_http.api.ApiService;
import com.hefei.module_http.callback.ACallback;

import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Map;

import io.reactivex.rxjava3.core.Observable;

/**
 * <pre>
 *     author: hefei
 *     time  : 2021/01/29
 *     desc  : 通用的请求基类
 * </pre>
 */
@SuppressWarnings("unchecked")
public abstract class BaseHttpRequest<R extends BaseHttpRequest> extends BaseRequest<R> {

    /* 通用接口服务 */
    protected ApiService apiService;

    /* 重试次数 */
    protected int retryCount;
    /* 重试间隔 */
    protected long retryDelayMillis;

    /* 链接后缀 */
    protected String suffixUrl = "";

    /* 请求参数 */
    protected Map<String, String> params = new LinkedHashMap<>();

    public BaseHttpRequest(String suffixUrl) {
        if (!TextUtils.isEmpty(suffixUrl)) {
            this.suffixUrl = suffixUrl;
        }
    }

    public BaseHttpRequest<R> build() {
        loadGlobalConfig();
        loadLocalConfig();
        return this;
    }

    public <T> void request(ACallback<T> callback) {
        execute(callback);
    }

    protected abstract <T> Observable<T> execute(Type type);

    protected abstract <T> void execute(ACallback<T> callback);

    /**
     * 添加请求参数
     */
    public R addParam(String paramName, String paramValue) {
        if (paramName != null && paramValue != null) {
            this.params.put(paramName, paramValue);
        }
        return (R) this;
    }

    /**
     * 添加请求参数
     */
    public R addParams(Map<String, String> params) {
        if (params != null) {
            this.params.putAll(params);
        }
        return (R) this;
    }

    /**
     * 移除请求参数
     */
    public R removeParam(String paramName) {
        if (paramName != null) {
            this.params.remove(paramName);
        }
        return (R) this;
    }

    /**
     * 设置请求失败重试次数
     */
    public R retryCount(int retryCount) {
        this.retryCount = retryCount;
        return (R) this;
    }

    /**
     * 设置请求失败重试间隔时间（毫秒）
     */
    public R retryDelayMillis(long retryDelayMillis) {
        this.retryDelayMillis = retryDelayMillis;
        return (R) this;
    }

    @Override
    protected void loadLocalConfig() {
        super.loadLocalConfig();

        apiService = retrofit.create(ApiService.class);

        if (sHttpGlobalConfig.getGlobalParams() != null) {
            params.putAll(sHttpGlobalConfig.getGlobalParams());
        }

        if (retryCount <= 0) {
            retryCount = sHttpGlobalConfig.getRetryCount();
        }

        if (retryDelayMillis <= 0) {
            retryDelayMillis = sHttpGlobalConfig.getRetryDelayMillis();
        }
    }

    //--------------------------------------------GET--------------------------------------------//

    public int getRetryCount() {
        return retryCount;
    }

    public long getRetryDelayMillis() {
        return retryDelayMillis;
    }

    public String getSuffixUrl() {
        return suffixUrl;
    }

    public Map<String, String> getParams() {
        return params;
    }
}
