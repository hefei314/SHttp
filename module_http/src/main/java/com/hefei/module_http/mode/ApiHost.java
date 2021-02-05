package com.hefei.module_http.mode;

import com.hefei.module_http.config.SHttpConstants;

/**
 * <pre>
 *     author: hefei
 *     time  : 2021/02/01
 *     desc  : 主机信息
 * </pre>
 */
public class ApiHost {

    private static String host = SHttpConstants.API_HOST;

    public static String getHost() {
        return host;
    }

    public static void setHost(String host) {
        setHostHttp(host);
    }

    public static void setHostHttp(String url) {
        if (host.startsWith("https://") || host.startsWith("http://")) {
            host = url;
            host = host.replaceAll("https://", "http://");
        } else {
            host = "http://" + url;
        }
    }

    public static void setHostHttps(String url) {
        if (host.startsWith("http://") || host.startsWith("https://")) {
            host = url;
            host = host.replaceAll("http://", "https://");
        } else {
            host = "https://" + url;
        }
    }

    public static String getHttp() {
        if (host.startsWith("https://") || host.startsWith("http://")) {
            host = host.replaceAll("https://", "http://");
        } else {
            host = "http://" + host;
        }
        return host;
    }

    public static String getHttps() {
        if (host.startsWith("https://") || host.startsWith("http://")) {
            host = host.replaceAll("http://", "https://");
        } else {
            host = "https://" + host;
        }
        return host;
    }
}
