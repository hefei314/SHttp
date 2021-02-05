package com.hefei.module_http.request;

import com.hefei.module_http.callback.ACallback;
import com.hefei.module_http.core.ApiManager;
import com.hefei.module_http.core.ApiTransformer;
import com.hefei.module_http.mode.SHttpMediaTypes;
import com.hefei.module_http.request.base.BaseHttpRequest;
import com.hefei.module_http.subscriber.ApiCallbackSubscriber;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.observers.DisposableObserver;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * <pre>
 *     author: hefei
 *     time  : 2021/01/29
 *     desc  : Post请求
 * </pre>
 */
public class PostRequest extends BaseHttpRequest<PostRequest> {

    protected StringBuilder urlParams = new StringBuilder();

    protected String content;
    protected MediaType mediaType;

    protected RequestBody requestBody;

    protected Map<String, Object> forms = new LinkedHashMap<>();

    public PostRequest(String suffixUrl) {
        super(suffixUrl);
    }

    @Override
    protected <T> Observable<T> execute(Type type) {
        if (urlParams.length() > 0) {
            suffixUrl += urlParams.toString();
        }

        if (requestBody != null) {
            return apiService.postBody(suffixUrl, requestBody).compose(ApiTransformer.<T>norTransformer(type, retryCount, retryDelayMillis));
        }

        if (content != null && mediaType != null) {
            requestBody = RequestBody.create(mediaType, content);
            return apiService.postBody(suffixUrl, requestBody).compose(ApiTransformer.<T>norTransformer(type, retryCount, retryDelayMillis));
        }

        if (forms != null && forms.size() > 0) {
            if (params != null && params.size() > 0) {
                Iterator<Map.Entry<String, String>> entryIterator = params.entrySet().iterator();
                Map.Entry<String, String> entry;
                while (entryIterator.hasNext()) {
                    entry = entryIterator.next();
                    if (entry != null) {
                        forms.put(entry.getKey(), entry.getValue());
                    }
                }
            }
            return apiService.postForm(suffixUrl, forms).compose(ApiTransformer.<T>norTransformer(type, retryCount, retryDelayMillis));
        }

        return apiService.post(suffixUrl, params).compose(ApiTransformer.<T>norTransformer(type, retryCount, retryDelayMillis));
    }

    @Override
    protected <T> void execute(ACallback<T> callback) {
        DisposableObserver disposableObserver = new ApiCallbackSubscriber<T>(callback);
        if (tag != null) {
            ApiManager.get().add(tag, disposableObserver);
        }
        this.execute(getType(callback)).subscribe(disposableObserver);
    }

    public PostRequest addUrlParam(String paramName, String paramValue) {
        if (paramName != null && paramValue != null) {
            if (urlParams.length() == 0) {
                urlParams.append("?");
            } else {
                urlParams.append("&");
            }
            urlParams.append(paramName).append("=").append(paramValue);
        }
        return this;
    }

    public PostRequest addForm(String formName, Object formValue) {
        if (formName != null && formValue != null) {
            this.forms.put(formName, formValue);
        }
        return this;
    }

    public PostRequest addForms(Map<String, Object> forms) {
        if (forms != null && forms.size() > 0) {
            this.forms.putAll(forms);
        }
        return this;
    }

    public PostRequest setRequestBody(RequestBody requestBody) {
        this.requestBody = requestBody;
        return this;
    }

    public PostRequest setString(String string) {
        this.content = string;
        this.mediaType = SHttpMediaTypes.TEXT_PLAIN_TYPE;
        return this;
    }

    public PostRequest setString(String string, MediaType mediaType) {
        this.content = string;
        this.mediaType = mediaType;
        return this;
    }

    public PostRequest setJson(String json) {
        this.content = json;
        this.mediaType = SHttpMediaTypes.APPLICATION_JSON_TYPE;
        return this;
    }

    public PostRequest setJson(JSONObject jsonObject) {
        this.content = jsonObject.toString();
        this.mediaType = SHttpMediaTypes.APPLICATION_JSON_TYPE;
        return this;
    }

    public PostRequest setJson(JSONArray jsonArray) {
        this.content = jsonArray.toString();
        this.mediaType = SHttpMediaTypes.APPLICATION_JSON_TYPE;
        return this;
    }
}
