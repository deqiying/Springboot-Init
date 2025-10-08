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

    /**
     *  移除 null 元素
     * @param collection 集合
     * @return 集合
     * @param <T> 泛型
     */
    public static <T> Collection<T> removeNull(final Collection<T> collection){
        if(collection == null){
            return null;
        }
        collection.removeIf(Objects::isNull);
        return collection;
    }

    /**
     * 对集合进行映射转换（map），返回一个新的列表
     *
     * @param source 源集合
     * @param mapper 映射函数
     * @param <T>    源元素类型
     * @param <R>    目标元素类型
     * @return 转换后的列表（当 source 为 null 时返回空列表）
     */
    public static <T, R> List<R> map(Collection<T> source, java.util.function.Function<? super T, ? extends R> mapper) {
        if (source == null || source.isEmpty()) {
            return Collections.emptyList();
        }
        Objects.requireNonNull(mapper, "mapper 不能为 null");
        return source.stream().map(mapper).collect(Collectors.toList());
    }

    /**
     * 过滤集合（filter），返回一个新的列表
     *
     * @param source    源集合
     * @param predicate 过滤条件
     * @param <T>       元素类型
     * @return 过滤后的列表（当 source 为 null 时返回空列表）
     */
    public static <T> List<T> filter(Collection<T> source, java.util.function.Predicate<? super T> predicate) {
        if (source == null || source.isEmpty()) {
            return Collections.emptyList();
        }
        Objects.requireNonNull(predicate, "predicate 不能为 null");
        return source.stream().filter(predicate).collect(Collectors.toList());
    }

    /**
     * 根据 key 提取函数分组
     *
     * @param source        源集合
     * @param keyExtractor  键提取函数
     * @param <T>           元素类型
     * @param <K>           键类型
     * @return Map 分组结果（当 source 为 null 时返回空 Map）
     */
    public static <T, K> Map<K, List<T>> groupBy(Collection<T> source, java.util.function.Function<? super T, ? extends K> keyExtractor) {
        if (source == null || source.isEmpty()) {
            return Collections.emptyMap();
        }
        Objects.requireNonNull(keyExtractor, "keyExtractor 不能为 null");
        return source.stream().collect(Collectors.groupingBy(keyExtractor));
    }

    /**
     * 将集合转换为 Map（键唯一），值为元素本身
     *
     * @param source       源集合
     * @param keyExtractor 键提取函数（必须唯一）
     * @param <T>          元素类型
     * @param <K>          键类型
     * @return Map 结果（当 source 为 null 时返回空 Map）
     * @throws IllegalStateException 当出现重复键时抛出异常
     */
    public static <T, K> Map<K, T> toMapUnique(Collection<T> source, java.util.function.Function<? super T, ? extends K> keyExtractor) {
        if (source == null || source.isEmpty()) {
            return Collections.emptyMap();
        }
        Objects.requireNonNull(keyExtractor, "keyExtractor 不能为 null");
        return source.stream().collect(Collectors.toMap(keyExtractor, java.util.function.Function.identity(), (a, b) -> {
            throw new IllegalStateException("存在重复键: " + a);
        }));
    }

    /**
     * 根据 key 去重，保留第一次出现的元素
     *
     * @param source       源集合
     * @param keyExtractor 键提取函数
     * @param <T>          元素类型
     * @param <K>          键类型
     * @return 去重后的列表（当 source 为 null 时返回空列表）
     */
    public static <T, K> List<T> distinctByKey(Collection<T> source, java.util.function.Function<? super T, ? extends K> keyExtractor) {
        if (source == null || source.isEmpty()) {
            return Collections.emptyList();
        }
        Objects.requireNonNull(keyExtractor, "keyExtractor 不能为 null");
        Set<K> seen = new HashSet<>();
        return source.stream()
                .filter(t -> seen.add(keyExtractor.apply(t)))
                .collect(Collectors.toList());
    }

    /**
     * 获取集合的第一个元素
     *
     * @param collection 集合
     * @param <T>        元素类型
     * @return 第一个元素；当集合为空或为 null 时返回 null
     */
    public static <T> T firstOrNull(Collection<T> collection) {
        if (collection == null || collection.isEmpty()) {
            return null;
        }
        if (collection instanceof List) {
            return ((List<T>) collection).get(0);
        }
        return collection.iterator().next();
    }

    /**
     * 获取集合的最后一个元素
     *
     * @param collection 集合
     * @param <T>        元素类型
     * @return 最后一个元素；当集合为空或为 null 时返回 null
     */
    public static <T> T lastOrNull(Collection<T> collection) {
        if (collection == null || collection.isEmpty()) {
            return null;
        }
        if (collection instanceof List) {
            List<T> list = (List<T>) collection;
            return list.get(list.size() - 1);
        }
        T last = null;
        for (T t : collection) {
            last = t;
        }
        return last;
    }

    /**
     * 安全获取集合大小
     *
     * @param collection 集合
     * @return 集合大小（当 collection 为 null 时返回 0）
     */
    public static int safeSize(Collection<?> collection) {
        return collection == null ? 0 : collection.size();
    }

    /**
     * 判断两个集合是否存在交集
     *
     * @param a 集合 A
     * @param b 集合 B
     * @return 是否存在任意相同元素
     */
    public static boolean containsAny(Collection<?> a, Collection<?> b) {
        if (isEmpty(a) || isEmpty(b)) {
            return false;
        }
        if (a.size() > b.size()) {
            // 交换，减少空间
            Collection<?> tmp = a;
            a = b;
            b = tmp;
        }
        Set<?> set = new HashSet<>(b);
        for (Object o : a) {
            if (set.contains(o)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 扁平化嵌套集合，将嵌套的集合展开为单层集合
     *
     * @param nested 嵌套集合
     * @param <T>    元素类型
     * @return 扁平化后的列表（当 nested 为 null 时返回空列表）
     */
    public static <T> List<T> flatten(Collection<? extends Collection<T>> nested) {
        if (nested == null || nested.isEmpty()) {
            return Collections.emptyList();
        }
        return nested.stream()
                .filter(Objects::nonNull)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    /**
     * 反转集合顺序，返回一个新的反序列表
     *
     * @param collection 原集合
     * @param <T>        元素类型
     * @return 反转后的新列表（当 collection 为 null 时返回空列表）
     */
    public static <T> List<T> reverse(Collection<T> collection) {
        if (collection == null || collection.isEmpty()) {
            return Collections.emptyList();
        }
        List<T> result = new ArrayList<>(collection);
        Collections.reverse(result);
        return result;
    }
}
