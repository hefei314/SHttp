package com.hefei.module_http.config;

/**
 * <pre>
 *     author: hefei
 *     time  : 2021/02/01
 *     desc  : 常量类
 * </pre>
 */
public class SHttpConstants {

    public static final String API_HOST = "https://api.github.com/";    // 默认主机地址

    /* Cookie */
    public static final String COOKIE_PREFS = "cookies_prefs";          // 默认Cookie缓存目录

    /* ConnectionPool */
    public static final int DEFAULT_TIMEOUT = 60;                       // 默认超时时间（秒）
    public static final int DEFAULT_MAX_IDLE_CONNECTIONS = 5;           // 默认空闲连接数
    public static final long DEFAULT_KEEP_ALIVE_DURATION = 8;           // 默认心跳间隔时长（秒）

    /* Cache */
    public static final int MAX_AGE_ONLINE = 60;                        // 默认最大在线缓存时间（秒）
    public static final int MAX_AGE_OFFLINE = 24 * 60 * 60;             // 默认最大离线缓存时间（秒）
    public static final long CACHE_MAX_SIZE = 10 * 1024 * 1024;         // 默认最大缓存大小（字节）
    public static final String CACHE_HTTP_DIR = "http_cache";           // 默认HTTP缓存目录

    /* Retry */
    public static final int DEFAULT_RETRY_COUNT = 0;                    // 默认重试次数
    public static final int DEFAULT_RETRY_DELAY_MILLIS = 1000;          // 默认重试间隔时间（毫秒）

}
