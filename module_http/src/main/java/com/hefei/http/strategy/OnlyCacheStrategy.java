package com.hefei.http.strategy;

import com.hefei.http.core.ApiCache;
import com.hefei.http.mode.CacheResult;

import java.lang.reflect.Type;

import io.reactivex.rxjava3.core.Observable;

/**
 * @Description: 缓存策略--只取缓存
 * @author: <a href="http://www.xiaoyaoyou1212.com">DAWI</a>
 * @date: 16/12/31 14:29.
 */
public class OnlyCacheStrategy<T> extends CacheStrategy<T> {

    @Override
    public <T> Observable<CacheResult<T>> execute(ApiCache apiCache, String cacheKey, Observable<T> source, Type type) {
        return loadCache(apiCache, cacheKey, type);
    }
}
