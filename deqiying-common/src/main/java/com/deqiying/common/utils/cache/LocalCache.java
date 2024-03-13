package com.deqiying.common.utils.cache;


import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * 本地缓存-基于Map实现
 *
 * @author deqiying
 */
@SuppressWarnings(value = {"unused"})
public class LocalCache<K, V> {

    /**
     * 本地缓存
     */
    private final Map<K, V> cache;
    /**
     * 预先创建的限制缓存大小的最大值
     */
    int maxSize;

    /**
     * 构造缓存对象
     *
     * @param maxSize   缓存数量最大值
     * @param keyType   key类型
     * @param valueType value类型
     */
    public LocalCache(int maxSize, Class<K> keyType, Class<V> valueType) {
        this.maxSize = maxSize;
        this.cache = new HashMap<>(maxSize);
    }

    /**
     * 获取所有缓存
     *
     * @return cache
     */
    public Map<K, V> asMap() {
        return cache;
    }

    /**
     * 新增缓存
     *
     * @param key   键
     * @param value 值
     */
    public void put(K key, V value) {
        if (maxSize < cache.size()) {
            cache.put(key, value);
        } else {
            throw new IllegalStateException("Cache full");
        }
    }

    /**
     * 获取缓存
     *
     * @param key 键
     */
    public void get(K key) {
        cache.get(key);
    }

    /**
     * 移除缓存
     *
     * @param key 键
     */
    public void remove(K key) {
        cache.remove(key);
    }

    /**
     * 批量移除缓存
     *
     * @param keys 键的集合
     */
    public void removeAll(Collection<K> keys) {
        cache.keySet().removeAll(keys);
    }

    /**
     * 清除所有缓存
     */
    public void clear() {
        cache.clear();
    }

    /**
     * 判断缓存是否存在指定的键
     *
     * @param key 键
     * @return 缓存是否存在指定的键
     */
    public boolean contains(K key) {
        return cache.containsKey(key);
    }

    /**
     * 判断缓存是否存在指定的值
     *
     * @param value 值
     * @return 缓存是否存在指定的值
     */
    public boolean containsValue(V value) {
        return cache.containsValue(value);
    }
}
