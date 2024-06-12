package com.deqiying.common.utils;

import java.util.Random;

/**
 * 随机工具类
 *
 * @author qiying
 */
public class RandomUtils {

    /**
     * 生成指定长度的随机数字字符串
     *
     * @param length 随机数字字符串的长度
     * @return 随机数字字符串
     */
    public static String randomNumberString(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("length must be positive");
        }
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int digit = random.nextInt(10); // 生成0-9之间的随机数
            sb.append(digit);
        }
        return sb.toString();
    }

}
