package com.hefei.retrofit.request;

import com.hefei.retrofit.callback.ACallback;
import com.hefei.retrofit.request.base.BaseHttpRequest;

/**
 * <pre>
 *     author: hefei
 *     time  : 2021/08/16
 *     desc  :
 * </pre>
 */
public class DeleteRequest extends BaseHttpRequest<DeleteRequest> {

    public DeleteRequest(String suffixUrl) {
        super(suffixUrl);
    }

    @Override
    protected <T> void execute(ACallback<T> callback) {
        apiService.delete(suffixUrl, params).enqueue(callbackTransformer(callback));
    }
}
