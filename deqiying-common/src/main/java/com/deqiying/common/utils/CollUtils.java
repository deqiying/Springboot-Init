package com.deqiying.common.utils;


import java.util.*;
import java.util.stream.Collectors;

/**
 * 集合相关常用算法
 *
 * @author deqiying
 */
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
        @SuppressWarnings("unchecked")
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
}
