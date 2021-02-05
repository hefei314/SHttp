package com.hefei.module_http.mode;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * <pre>
 *     author: hefei
 *     time  : 2021/01/29
 *     desc  : 请求头
 * </pre>
 */
public class SHttpHeaders implements Serializable {

    public LinkedHashMap<String, String> headersMap;

    public SHttpHeaders() {
        this.headersMap = new LinkedHashMap<>();
    }

    public void put(String name, String value) {
        if (name != null && value != null) {
            headersMap.put(name, value);
        }
    }

    public void put(Map<String, String> headers) {
        if (headers != null) {
            headersMap.putAll(headers);
        }
    }

    public void put(SHttpHeaders headers) {
        if (headers != null) {
            if (headers.headersMap != null) {
                headersMap.putAll(headers.headersMap);
            }
        }
    }

    public String get(String name) {
        return headersMap.get(name);
    }

    public String remove(String name) {
        return headersMap.remove(name);
    }

    public void clear() {
        headersMap.clear();
    }

    public Set<String> getNames() {
        return headersMap.keySet();
    }

    public Collection<String> getValues() {
        return headersMap.values();
    }

    public boolean isEmpty() {
        return headersMap != null && headersMap.size() <= 0;
    }

    @Override
    public String toString() {
        return "SHttpHeaders{" + "headersMap=" + headersMap + '}';
    }
}
