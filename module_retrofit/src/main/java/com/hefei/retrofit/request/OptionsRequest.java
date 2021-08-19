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
public class OptionsRequest extends BaseHttpRequest<OptionsRequest> {

    public OptionsRequest(String suffixUrl) {
        super(suffixUrl);
    }

    @Override
    protected <T> void execute(ACallback<T> callback) {
        apiService.options(suffixUrl, params).enqueue(callbackTransformer(callback));
    }
}
