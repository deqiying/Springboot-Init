package com.deqiying.common.core.cache;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;

/**
 * 统一缓存接口
 *
 * @author deqiying
 */
@SuppressWarnings(value = {"unused"})
public interface Cache<K, V> {
    /**
     * 获取当前缓存大小
     *
     * @return 当前缓存大小
     */
    int size();

    /**
     * 获取所有缓存
     *
     * @return cache
     */
    Map<K, V> asMap();

    /**
     * 新增缓存
     *
     * @param key   键
     * @param value 值
     */
    void put(K key, V value);

    /**
     * 将一个对于参数key执行幂等的方法产生的结果，存放到缓存中。
     * 主要用于需要计算过程的值
     *
     * @param k        键
     * @param function 幂等的方法
     * @return 幂等的方法执行返回的值
     */
    default V compute(K k, Function<K, V> function) {
        V value = function.apply(k);
        put(k, value);
        return value;
    }

    /**
     * 获取缓存
     *
     * @param key 键
     */
    void get(K key);

    /**
     * 移除缓存
     *
     * @param key 键
     */
    void remove(K key);

    /**
     * 批量移除缓存
     *
     * @param keys 键的集合
     */
    void removeAll(Collection<K> keys);

    /**
     * 清除所有缓存
     */
    void clear();

    /**
     * 判断缓存是否存在指定的键
     *
     * @param key 键
     * @return 缓存是否存在指定的键
     */
    boolean contains(K key);

    /**
     * 判断缓存是否存在指定的值
     *
     * @param value 值
     * @return 缓存是否存在指定的值
     */
    boolean containsValue(V value);


}
