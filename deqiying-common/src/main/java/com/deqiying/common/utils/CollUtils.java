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

    /**
     * 随机打乱集合
     *
     * @param list 原集合
     * @param <T>  泛型
     * @return 打乱后的新集合
     */
    public static <T> List<T> shuffle(List<T> list) {
        if (list == null || list.isEmpty()) {
            return Collections.emptyList();
        }
        List<T> newlist = new ArrayList<>(list);
        Collections.shuffle(newlist);
        return newlist;
    }

    /**
     * 判断数组是否为空
     *
     * @param array 数组
     * @return 是否为空
     */
    public static boolean isEmpty(Object[] array) {
        return array == null || array.length == 0;
    }

    /**
     * 判断数组是否不为空
     *
     * @param array 数组
     * @return 是否不为空
     */
    public static boolean isNotEmpty(Object[] array) {
        return !isEmpty(array);
    }

    /**
     * 判断集合是否为空
     *
     * @param collection 集合
     * @return 是否为空
     */
    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * 判断集合是否不为空
     *
     * @param collection 集合
     * @return 是否不为空
     */
    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }

    /**
     * 判断Map是否为空
     *
     * @param map Map
     * @return 是否为空
     */
    public static boolean isEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    /**
     * 判断Map是否不为空
     *
     * @param map Map
     * @return 是否不为空
     */
    public static boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }

    /**
     * 判断队列是否为空
     *
     * @param queue 队列
     * @return 是否为空
     */
    public static boolean isNotEmpty(Queue<?> queue) {
        return !isEmpty(queue);
    }

    /**
     * 判断队列是否为空
     *
     * @param queue 队列
     * @return 是否为空
     */
    public static boolean isEmpty(Queue<?> queue) {
        return queue == null || queue.isEmpty();
    }

    /**
     * 新增元素，并且忽略空的元素
     *
     * @param collection 集合
     * @param object     元素
     * @param <T>        泛型
     * @return 是否新增成功
     */
    public static <T> boolean addIgnoreNull(final Collection<T> collection, final T object) {
        if (collection == null) {
            throw new NullPointerException("The collection must not be null");
        }
        return object != null && collection.add(object);
    }

    /**
     * 返回一个不为null的集合，如果集合本身不为null，则返回集合本身
     *
     * @param collection 集合
     * @param <T>        泛型
     * @return 集合
     */
    public static <T> Collection<T> emptyIfNull(final Collection<T> collection) {
        return collection == null ? Collections.emptyList() : collection;
    }

    public static <T> Collection<T> removeNull(final Collection<T> collection){
        return collection == null ? Collections.emptyList() : collection.stream().filter(Objects::nonNull).collect(Collectors.toList());
    }
}
