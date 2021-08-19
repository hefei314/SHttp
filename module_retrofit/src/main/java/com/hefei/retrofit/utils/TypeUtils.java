package com.hefei.retrofit.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * <pre>
 *     author: hefei
 *     time  : 2021/08/12
 *     desc  :
 * </pre>
 */
public class TypeUtils {

    public static <T> Type getType(T t) {
        return ((ParameterizedType) t.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * 获取次一级type
     */
    public static <T> Type getSubType(T t) {
        Type genType = t.getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        Type type = params[0];
        Type finalNeedType;
        if (params.length > 1) {
            if (!(type instanceof ParameterizedType))
                throw new IllegalStateException("没有填写泛型参数");
            finalNeedType = ((ParameterizedType) type).getActualTypeArguments()[0];
        } else {
            if (type instanceof ParameterizedType) {
                finalNeedType = ((ParameterizedType) type).getActualTypeArguments()[0];
            } else {
                finalNeedType = type;
            }
        }
        return finalNeedType;
    }
}
