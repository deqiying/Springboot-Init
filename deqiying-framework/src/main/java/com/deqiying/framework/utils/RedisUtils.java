package com.deqiying.framework.utils;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * redis工具类
 *
 * @author qiying
 */
@SuppressWarnings(value = {"unused", "unchecked", "rawtypes"})
@Component
public class RedisUtils {

    private static RedisTemplate redisTemplate;
    private static RedissonClient redissonClient;

    public static void init(RedisTemplate redisTemplate, RedissonClient redissonClient) {
        if (RedisUtils.redisTemplate == null) {
            RedisUtils.redisTemplate = redisTemplate;
        }
        if (RedisUtils.redissonClient == null) {
            RedisUtils.redissonClient = redissonClient;
        }
    }

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key   缓存的键值
     * @param value 缓存的值
     */
    public static <T> void setCacheObject(final String key, final T value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key      缓存的键值
     * @param value    缓存的值
     * @param timeout  时间
     * @param timeUnit 时间颗粒度
     */
    public static <T> void setCacheObject(final String key, final T value, final Integer timeout, final TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }

    /**
     * 设置有效时间
     *
     * @param key     Redis键
     * @param timeout 超时时间
     * @return true=设置成功；false=设置失败
     */
    public static boolean expire(final String key, final long timeout) {
        return expire(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * 设置有效时间
     *
     * @param key     Redis键
     * @param timeout 超时时间
     * @param unit    时间单位
     * @return true=设置成功；false=设置失败
     */
    public static boolean expire(final String key, final long timeout, final TimeUnit unit) {
        return Boolean.TRUE.equals(redisTemplate.expire(key, timeout, unit));
    }

    /**
     * 获得缓存的基本对象。
     *
     * @param key 缓存键值
     * @return 缓存键值对应的数据
     */
    public static <T> T getCacheObject(final String key) {
        ValueOperations<String, T> operation = redisTemplate.opsForValue();
        return operation.get(key);
    }

    /**
     * 根据key 获取过期时间
     *
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    public static Long getExpire(final String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 判断key是否存在
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    public static Boolean hasKey(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 删除单个对象
     *
     * @param key Redis键
     */
    public static boolean deleteObject(final String key) {
        return Boolean.TRUE.equals(redisTemplate.delete(key));
    }

    /**
     * 删除集合对象
     *
     * @param collection 多个对象
     * @return 删除的个数
     */
    public static long deleteObject(final Collection collection) {
        Long deleteNum = redisTemplate.delete(collection);
        return deleteNum == null ? 0 : deleteNum;
    }

    /**
     * 缓存List数据
     *
     * @param key      缓存的键值
     * @param dataList 待缓存的List数据
     * @return 缓存的对象
     */
    public static <T> long setCacheList(final String key, final List<T> dataList) {
        Long count = redisTemplate.opsForList().rightPushAll(key, dataList);
        return count == null ? 0 : count;
    }

    /**
     * 获得缓存的list对象
     *
     * @param key 缓存的键值
     * @return 缓存键值对应的数据
     */
    public static <T> List<T> getCacheList(final String key) {
        return redisTemplate.opsForList().range(key, 0, -1);
    }

    /**
     * 缓存Set
     *
     * @param key     缓存键值
     * @param dataSet 缓存的数据
     * @return 缓存数据的对象
     */
    public static <T> BoundSetOperations<String, T> setCacheSet(final String key, final Set<T> dataSet) {
        BoundSetOperations<String, T> setOperation = redisTemplate.boundSetOps(key);
        for (T t : dataSet) {
            setOperation.add(t);
        }
        return setOperation;
    }

    /**
     * 获得缓存的set
     *
     * @param key 缓存键值
     * @return 缓存数据
     */
    public static <T> Set<T> getCacheSet(final String key) {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * 缓存Map
     *
     * @param key     缓存键值
     * @param dataMap Map数据
     */
    public static <T> void setCacheMap(final String key, final Map<String, T> dataMap) {
        if (dataMap != null) {
            redisTemplate.opsForHash().putAll(key, dataMap);
        }
    }

    /**
     * 获得缓存的Map
     *
     * @param key Redis键
     * @return Map对象
     */
    public static <T> Map<String, T> getCacheMap(final String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 往Hash中存入数据
     *
     * @param key   Redis键
     * @param hKey  Hash键
     * @param value 值
     */
    public static <T> void setCacheMapValue(final String key, final String hKey, final T value) {
        redisTemplate.opsForHash().put(key, hKey, value);
    }

    /**
     * 获取Hash中的数据
     *
     * @param key  Redis键
     * @param hKey Hash键
     * @return Hash中的对象
     */
    public static <T> T getCacheMapValue(final String key, final String hKey) {
        HashOperations<String, String, T> opsForHash = redisTemplate.opsForHash();
        return opsForHash.get(key, hKey);
    }


    public static void incrementCacheMapValue(String key, String hKey, int v) {
        redisTemplate.opsForHash().increment(key, hKey, v);
    }

    /**
     * 删除Hash中的数据
     *
     * @param key  Redis键
     * @param hkey Hash键
     */
    public static void delCacheMapValue(final String key, final String hkey) {
        HashOperations hashOperations = redisTemplate.opsForHash();
        hashOperations.delete(key, hkey);
    }

    /**
     * 获取多个Hash中的数据
     *
     * @param key   Redis键
     * @param hKeys Hash键集合
     * @return Hash对象集合
     */
    public static <T> List<T> getMultiCacheMapValue(final String key, final Collection<Object> hKeys) {
        return redisTemplate.opsForHash().multiGet(key, hKeys);
    }

    /**
     * 获得缓存的基本对象列表
     *
     * @param pattern 字符串前缀
     * @return 对象列表
     */
    public static Collection<String> keys(final String pattern) {
        return redisTemplate.keys(pattern);
    }


    /**
     * 获取锁 - 默认过期时间5分钟
     *
     * @param lockKey   锁的键
     * @param requestId 请求标识，用于识别持有锁的线程
     * @return true表示获取锁成功，false表示获取锁失败
     */
    public static boolean lock(String lockKey, String requestId) {
        return lock(lockKey, requestId, 5, TimeUnit.MINUTES);
    }


    /**
     * 获取锁
     *
     * @param lockKey    锁的键
     * @param requestId  请求标识，用于识别持有锁的线程
     * @param expireTime 锁的过期时间，单位为毫秒
     * @return true表示获取锁成功，false表示获取锁失败
     */
    public static boolean lock(String lockKey, String requestId, long expireTime) {
        return lock(lockKey, requestId, expireTime, TimeUnit.MILLISECONDS);
    }

    /**
     * 获取锁
     *
     * @param lockKey    锁的键
     * @param requestId  请求标识，用于识别持有锁的线程
     * @param expireTime 锁的过期时间
     * @param timeUnit   时间单位
     * @return true表示获取锁成功，false表示获取锁失败
     */
    public static boolean lock(String lockKey, String requestId, long expireTime, TimeUnit timeUnit) {
        return Boolean.TRUE.equals(redisTemplate.opsForValue().setIfAbsent(lockKey, requestId, expireTime, timeUnit));
    }

    /**
     * 释放锁
     *
     * @param lockKey   锁的键
     * @param requestId 请求标识，用于校验释放锁的合法性
     */
    public static void unlock(String lockKey, String requestId) {
        // 获取当前锁的持有者
        String value = getCacheObject(lockKey);
        // 如果当前请求标识与锁的持有者一致，则删除锁
        if (value != null && value.equals(requestId)) {
            redisTemplate.delete(lockKey);
        }
    }

    /**
     * 检测锁是否被占用
     *
     * @param lockKey 锁的键
     * @return true表示锁被占用，false表示锁未被占用
     */
    public static boolean isLocked(String lockKey) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(lockKey));
    }

    /**
     * 获取锁的剩余过期时间
     *
     * @param lockKey 锁的键
     * @return 剩余的过期时间（以秒为单位），如果锁不存在则返回-1
     */
    public static long getLockRemainingTime(String lockKey) {
        return getLockRemainingTime(lockKey, TimeUnit.SECONDS);
    }


    /**
     * 获取锁的剩余过期时间
     *
     * @param lockKey 锁的键
     * @return 剩余的过期时间（以秒为单位），如果锁不存在则返回-1
     */
    public static long getLockRemainingTime(String lockKey, TimeUnit timeUnit) {
        Long expire = redisTemplate.getExpire(lockKey, timeUnit);
        return expire != null ? expire : -1;
    }

    /**
     * 获取锁
     *
     * @param key 锁的key
     * @return RLock
     */
    public static RLock getLock(String key) {
        return redissonClient.getLock(key);
    }
}
