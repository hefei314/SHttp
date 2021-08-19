package com.hefei.retrofit.callback;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * <pre>
 *     author: hefei
 *     time  : 2021/07/29
 *     desc  : 上传进度回调
 * </pre>
 */
public abstract class UCallback implements Callback {

    @Override
    public void onResponse(Call call, Response response) {
        if (!response.isSuccessful()) {
            onFail(-1, response.message());
        }
    }

    public abstract void onProgress(long currentLength, long totalLength, float percent);

    public abstract void onFail(int errCode, String errMsg);

}
