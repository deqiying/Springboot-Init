package com.deqiying.common.utils;

import org.apache.commons.collections4.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class CollUtils {
    public static <T> Set<T> intersection(List<T> list1, List<T> list2) {
        Set<T> set1 = new HashSet<>(list1);
        Set<T> intersection = new HashSet<>();

        for (T element : list2) {
            if (set1.contains(element)) {
                intersection.add(element);
            }
        }

        return intersection;
    }
}
