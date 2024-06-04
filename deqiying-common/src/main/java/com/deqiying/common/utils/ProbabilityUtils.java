package com.deqiying.common.utils;

import java.util.Map;
import java.util.Random;

@SuppressWarnings(value = {"unused"})
public class ProbabilityUtils {
    private static final Random random = new Random();

    /**
     * 判断概率事件是否发生
     *
     * @param numerator 事件发生的概率，应该是0到1之间的一个小数
     * @return 如果事件发生，返回true；否则返回false
     */
    public static boolean fraction(double numerator) {
        if (numerator < 0) {
            return false;
        }
        if (numerator >= 1) {
            return true;
        }
        return random.nextDouble() < numerator;
    }

    public static boolean percent(int numerator) {
        if (numerator < 0) {
            return false;
        }
        if (numerator >= 100) {
            return true;
        }
        return random.nextInt(100) < numerator;
    }

    public static boolean permille(int numerator) {
        if (numerator < 0) {
            return false;
        }
        if (numerator >= 10000) {
            return true;
        }
        return random.nextInt(10000) < numerator;
    }

    public static <T> T randomElement(Map<T,Integer> map){
        int sum = 0;
        for (int value : map.values()) {
            sum += value;
        }
        int randomValue = random.nextInt(sum);
        for (Map.Entry<T, Integer> entry : map.entrySet()) {
            randomValue -= entry.getValue();
            if (randomValue < 0) {
                return entry.getKey();
            }
        }
        return null;
    }

}
