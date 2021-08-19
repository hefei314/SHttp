package com.hefei.retrofit.request.base;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.hefei.retrofit.api.ApiService;
import com.hefei.retrofit.callback.ACallback;
import com.hefei.retrofit.utils.TypeUtils;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * <pre>
 *     author: hefei
 *     time  : 2021/08/13
 *     desc  :
 * </pre>
 */
public abstract class BaseHttpRequest<R extends BaseHttpRequest> extends BaseRequest<BaseHttpRequest> {

    protected ApiService apiService;

    protected String suffixUrl = "";

    protected Map<String, String> params = new LinkedHashMap<>();

    public BaseHttpRequest() {
    }

    public BaseHttpRequest(String suffixUrl) {
        if (!TextUtils.isEmpty(suffixUrl)) {
            this.suffixUrl = suffixUrl;
        }
    }

    /**
     * 添加请求参数
     */
    public R addParam(String paramKey, String paramValue) {
        if (paramKey != null && paramValue != null) {
            this.params.put(paramKey, paramValue);
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
    public R removeParam(String paramKey) {
        if (paramKey != null) {
            this.params.remove(paramKey);
        }
        return (R) this;
    }

    /**
     * 设置请求参数
     */
    public R params(Map<String, String> params) {
        if (params != null) {
            this.params = params;
        }
        return (R) this;
    }

    public <T> void request(ACallback<T> callback) {
        generateGlobalConfig();
        generateLocalConfig();
        execute(callback);
    }

    protected abstract <T> void execute(ACallback<T> callback);

    @Override
    protected void generateLocalConfig() {
        super.generateLocalConfig();
        if (httpGlobalConfig.getGlobalParams() != null) {
            params.putAll(httpGlobalConfig.getGlobalParams());
        }
        apiService = retrofit.create(ApiService.class);
    }

    public <T> Callback<ResponseBody> callbackTransformer(ACallback<T> callback) {
        return new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                ResponseBody responseBody = response.body();
                if (responseBody != null && callback != null) {
                    try {
                        callback.onSuccess(new Gson().fromJson(responseBody.string(), TypeUtils.getType(callback)));
                    } catch (IOException e) {
                        e.printStackTrace();
                        callback.onFail(e.getMessage());
                    } finally {
                        responseBody.close();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                if (callback != null) {
                    callback.onFail(t.getMessage());
                }
            }
        };
    }
}
