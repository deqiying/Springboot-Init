package com.deqiying.common.utils;


import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 集合相关常用算法
 *
 * @author deqiying
 */
@SuppressWarnings(value = {"unused", "unchecked"})
public class CollUtils {

    /**
     * 数组转list
     *
     * @param list 集合
     * @param <T>  泛型
     * @return 数组
     */
    public static <T> T[] toArray(List<T> list) {
        // 创建一个新的数组，大小为列表的大小
        T[] array = (T[]) new Object[list.size()];
        // 返回转换后的数组
        return list.toArray(array);
    }

    /**
     * 数组转集合
     *
     * @param array 数组
     * @param <T>   泛型
     * @return 集合
     */
    public static <T> List<T> asList(T[] array) {
        return Arrays.asList(array);
    }

    // 计算交集
    public static <T> List<T> intersection(List<T> list1, List<T> list2) {
        Set<T> set2 = new HashSet<>(list2);
        return list1.stream()
                .filter(set2::contains)
                .collect(Collectors.toList());
    }

    // 计算并集
    public static <T> List<T> union(List<T> list1, List<T> list2) {
        Set<T> set = new HashSet<>(list1);
        set.addAll(list2);
        return new ArrayList<>(set);
    }

    // 计算差集
    public static <T> List<T> difference(List<T> list1, List<T> list2) {
        Set<T> set2 = new HashSet<>(list2);
        return list1.stream()
                .filter(e -> !set2.contains(e))
                .collect(Collectors.toList());
    }

    /**
     * 根据子集大小分割集合
     *
     * @param list    原集合
     * @param maxSize 子集最大长度
     * @param <T>     泛型
     * @return 子集集合
     */
    public static <T> List<List<T>> splitByMaxSize(List<T> list, int maxSize) {
        return Stream.iterate(0, i -> i + 1)
                .limit((list.size() + maxSize - 1) / maxSize)
                .map(i -> list.subList(i * maxSize, Math.min((i + 1) * maxSize, list.size())))
                .collect(Collectors.toList());
    }

    /**
     * 根据子集个数分割集合
     *
     * @param list     原集合
     * @param maxCount 子集最大数量
     * @param <T>      泛型
     * @return 子集集合
     */
    public static <T> List<List<T>> splitByMaxCount(List<T> list, int maxCount) {
        if (maxCount < 1) {
            throw new IllegalArgumentException("maxCount can not letter than zero");
        }
        int sizePerList = (int) Math.ceil((double) list.size() / maxCount);
        return Stream.iterate(0, i -> i + 1)
                .limit(maxCount)
                .map(i -> list.subList(i * sizePerList, Math.min((i + 1) * sizePerList, list.size())))
                .collect(Collectors.toList());
    }

}
