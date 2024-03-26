package com.deqiying.common.utils;

import com.deqiying.common.core.SnowFlake;

/**
 * 雪花算法工具类
 *
 * @author qiying
 */
public class SnowFlakeUtils {
    public static SnowFlake snowFlake;

    static {
        snowFlake = new SnowFlake(1, 1);
    }

    /**
     * 更新雪花算法数据中心ID和机器ID
     *
     * @param datacenterId 数据中心ID
     * @param machineId    机器ID
     */
    public static void updateSnowFlake(long datacenterId, long machineId) {
        SnowFlakeUtils.snowFlake = new SnowFlake(datacenterId, machineId);
    }

    /**
     * 获取下一个ID
     *
     * @return ID
     */
    public static long nextId() {
        return snowFlake.nextId();
    }

    /**
     * 获取下一个ID（指定进制）
     *
     * @param radix 进制
     * @return ID
     */
    public static String nextId(int radix) {
        return Long.toString(nextId(), radix);
    }

    /**
     * 获取下一个ID（16进制）
     *
     * @return ID
     */
    public static String nextId16() {
        return Long.toHexString(nextId());
    }

    /**
     * 获取下一个ID（26进制）
     *
     * @return ID
     */
    public static String nextId26() {
        return radix10To26(nextId());
    }

    /**
     * 10进制转26进制
     *
     * @param num 10进制数
     * @return 26进制数
     */
    private static String radix10To26(long num) {
        if (num < 0) {
            throw new IllegalArgumentException("must be non-negative: " + num);
        }
        final int radix = 26;
        char[] outs = new char[64];
        int outsIndex = outs.length;
        long quotient;
        long remainder;
        char c;
        do {
            quotient = num / radix;
            remainder = num % radix;
            c = (char) ('A' + remainder);
            outs[--outsIndex] = c;
            num = quotient;
        } while (num > 0);
        return new String(outs, outsIndex, outs.length - outsIndex);
    }

}
