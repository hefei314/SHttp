package com.hefei.retrofit;

import android.content.Context;

import com.hefei.retrofit.callback.UCallback;
import com.hefei.retrofit.config.HttpGlobalConfig;
import com.hefei.retrofit.manager.HttpManager;
import com.hefei.retrofit.request.DeleteRequest;
import com.hefei.retrofit.request.GetRequest;
import com.hefei.retrofit.request.HeadRequest;
import com.hefei.retrofit.request.OptionsRequest;
import com.hefei.retrofit.request.PatchRequest;
import com.hefei.retrofit.request.PostRequest;
import com.hefei.retrofit.request.PutRequest;
import com.hefei.retrofit.request.UploadRequest;

/**
 * <pre>
 *     author: hefei
 *     time  : 2021/07/29
 *     desc  :
 * </pre>
 */
public class HttpUtils {

    private static final HttpGlobalConfig HTTP_GLOBAL_CONFIG = HttpGlobalConfig.getInstance();

    public static void init(Context appContext) {
        HttpManager.init(appContext);
    }

    public static HttpGlobalConfig config() {
        return HTTP_GLOBAL_CONFIG;
    }

    /**
     * GET请求
     */
    public static GetRequest GET(String suffixUrl) {
        return new GetRequest(suffixUrl);
    }

    /**
     * POST请求
     */
    public static PostRequest POST(String suffixUrl) {
        return new PostRequest(suffixUrl);
    }

    /**
     * HEAD请求
     */
    public static HeadRequest HEAD(String suffixUrl) {
        return new HeadRequest(suffixUrl);
    }

    /**
     * PUT请求
     */
    public static PutRequest PUT(String suffixUrl) {
        return new PutRequest(suffixUrl);
    }

    /**
     * PATCH请求
     */
    public static PatchRequest PATCH(String suffixUrl) {
        return new PatchRequest(suffixUrl);
    }

    /**
     * OPTIONS请求
     */
    public static OptionsRequest OPTIONS(String suffixUrl) {
        return new OptionsRequest(suffixUrl);
    }

    /**
     * DELETE请求
     */
    public static DeleteRequest DELETE(String suffixUrl) {
        return new DeleteRequest(suffixUrl);
    }

    /**
     * 上传
     */
    public static UploadRequest UPLOAD(String suffixUrl) {
        return new UploadRequest(suffixUrl);
    }

    /**
     * 上传（包含上传进度回调）
     */
    public static UploadRequest UPLOAD(String suffixUrl, UCallback uCallback) {
        return new UploadRequest(suffixUrl, uCallback);
    }
}
