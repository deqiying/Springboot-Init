package com.deqiying.common.core.cache;


import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * 本地缓存-基于Map实现
 *
 * @author deqiying
 */
@SuppressWarnings(value = {"unused"})
public class LocalCache<K, V> implements Cache<K, V> {

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
     * @param maxSize 缓存数量最大值
     */
    public LocalCache(int maxSize) {
        this.maxSize = maxSize;
        this.cache = new HashMap<>(maxSize);
    }

    /**
     * 获取当前缓存大小
     *
     * @return 当前缓存大小
     */
    @Override
    public int size() {
        return cache.size();
    }

    /**
     * 获取所有缓存
     *
     * @return cache
     */
    @Override
    public Map<K, V> asMap() {
        return cache;
    }

    /**
     * 新增缓存
     *
     * @param key   键
     * @param value 值
     */
    @Override
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
    @Override
    public void get(K key) {
        cache.get(key);
    }

    /**
     * 移除缓存
     *
     * @param key 键
     */
    @Override
    public void remove(K key) {
        cache.remove(key);
    }

    /**
     * 批量移除缓存
     *
     * @param keys 键的集合
     */
    @Override
    public void removeAll(Collection<K> keys) {
        cache.keySet().removeAll(keys);
    }

    /**
     * 清除所有缓存
     */
    @Override
    public void clear() {
        cache.clear();
    }

    /**
     * 判断缓存是否存在指定的键
     *
     * @param key 键
     * @return 缓存是否存在指定的键
     */
    @Override
    public boolean contains(K key) {
        return cache.containsKey(key);
    }

    /**
     * 判断缓存是否存在指定的值
     *
     * @param value 值
     * @return 缓存是否存在指定的值
     */
    @Override
    public boolean containsValue(V value) {
        return cache.containsValue(value);
    }

    /**
     * 将一个对于参数key执行幂等的方法产生的结果，存放到缓存中。
     *
     * @param k        键
     * @param function 幂等的方法
     * @return 幂等的方法执行返回的值
     */
    @Override
    public V compute(K k, Function<K, V> function) {
        return cache.putIfAbsent(k, function.apply(k));
    }
}
