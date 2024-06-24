package com.deqiying.common.utils;


import lombok.experimental.UtilityClass;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 集合相关工具
 *
 * @author deqiying
 * @date 2022-01-14
 */
@UtilityClass
public class CollKit {
    /**
     * 分组统计
     * <pre>
     *     key is 元素下标
     *     value is 元素下标对应的数量
     * </pre>
     *
     * <pre>
     *     示例
     *     handCards: [11, 11, 11, 21, 46, 33,33, 18, 18, 18, 18]
     *
     *     得到的 map {
     *         11 : 3
     *         18 : 4
     *         21 : 1
     *         33 : 2
     *         46 : 1
     *     }
     * </pre>
     *
     * @param list 元素列表
     * @return map
     */
    public Map<Integer, Integer> groupCounting(List<Integer> list) {
        return list.stream().
                collect(
                        Collectors.groupingBy(Function.identity(), Collectors.summingInt(e -> 1))
                );
    }

    public boolean notEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }

    public boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public <T> Optional<T> findAny(Set<T> set) {
        if (isEmpty(set)) {
            return Optional.empty();
        }

        return set.stream().findAny();
    }
}
