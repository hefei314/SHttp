package com.hefei.module_http.core;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import io.reactivex.rxjava3.disposables.Disposable;

/**
 * <pre>
 *     author: hefei
 *     time  : 2021/02/03
 *     desc  : 请求管理，方便中途取消请求
 * </pre>
 */
public class ApiManager {

    private ConcurrentHashMap<Object, Disposable> arrayMaps;

    private static ApiManager sInstance;

    public static ApiManager get() {
        if (sInstance == null) {
            synchronized (ApiManager.class) {
                if (sInstance == null) {
                    sInstance = new ApiManager();
                }
            }
        }
        return sInstance;
    }

    private ApiManager() {
        arrayMaps = new ConcurrentHashMap<>();
    }

    public void add(Object tag, Disposable disposable) {
        arrayMaps.put(tag, disposable);
    }

    public void remove(Object tag) {
        if (!arrayMaps.isEmpty()) {
            arrayMaps.remove(tag);
        }
    }

    public void removeAll() {
        if (!arrayMaps.isEmpty()) {
            arrayMaps.clear();
        }
    }

    public void cancel(Object tag) {
        if (arrayMaps.isEmpty() || arrayMaps.get(tag) == null) {
            return;
        }
        if (!arrayMaps.get(tag).isDisposed()) {
            arrayMaps.get(tag).dispose();
            arrayMaps.remove(tag);
        }
    }

    public void cancelAll() {
        if (arrayMaps.isEmpty()) {
            return;
        }
        Set<Object> keys = arrayMaps.keySet();
        for (Object apiKey : keys) {
            cancel(apiKey);
        }
    }
}
