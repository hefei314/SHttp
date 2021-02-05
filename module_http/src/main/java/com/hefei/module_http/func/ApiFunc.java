package com.hefei.module_http.func;

import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.Type;

import io.reactivex.rxjava3.functions.Function;
import okhttp3.ResponseBody;

/**
 * <pre>
 *     author: hefei
 *     time  : 2021/02/02
 *     desc  : ResponseBody转T
 * </pre>
 */
public class ApiFunc<T> implements Function<ResponseBody, T> {

    private Type type;

    public ApiFunc(Type type) {
        this.type = type;
    }

    @Override
    public T apply(ResponseBody responseBody) {
        Gson gson = new Gson();
        String json;
        try {
            json = responseBody.string();
            if (type.equals(String.class)) {
                return (T) json;
            } else {
                return gson.fromJson(json, type);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            responseBody.close();
        }
        return null;
    }
}
