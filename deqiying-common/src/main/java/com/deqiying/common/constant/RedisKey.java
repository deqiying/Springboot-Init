package com.deqiying.common.constant;

/**
 * Redis常量
 */
public class RedisKey {
    /**
     * 用户信息缓存
     */
    public static final String USER_CACHE = "user_cache:";
    /**
     * 用户锁
     */
    public static final String USER_LOCK = "user_lock:";

    /**
     * 系统信息缓存
     */
    public static final String SYSTEM = "system:";

    /**
     * 系统唯一标识
     */
    public static final String SYSTEM_UNIQUE = SYSTEM + "unique:";

    /**
     * 系统唯一锁
     */
    public static final String SYSTEM_UNIQUE_LOCK = SYSTEM_UNIQUE + "lock:";
}
