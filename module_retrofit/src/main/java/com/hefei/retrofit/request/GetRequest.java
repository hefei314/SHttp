package com.hefei.retrofit.request;

import com.hefei.retrofit.callback.ACallback;
import com.hefei.retrofit.request.base.BaseHttpRequest;

/**
 * <pre>
 *     author: hefei
 *     time  : 2021/08/13
 *     desc  :
 * </pre>
 */
public class GetRequest extends BaseHttpRequest<GetRequest> {

    public GetRequest(String suffixUrl) {
        super(suffixUrl);
    }

    @Override
    protected <T> void execute(ACallback<T> callback) {
        apiService.get(suffixUrl, params).enqueue(callbackTransformer(callback));
    }
}
