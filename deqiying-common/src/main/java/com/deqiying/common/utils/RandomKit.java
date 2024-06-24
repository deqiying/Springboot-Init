package com.deqiying.common.utils;

import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.random.RandomGenerator;

/**
 * @author deqiying
 * @date 2022-07-14
 */
@UtilityClass
public class RandomKit {
    final RandomGenerator generator = RandomGenerator.getDefault();

    /**
     * 获得指定范围内的随机数 [0,limit)
     *
     * @param limit 限制随机数的范围，不包括这个数
     * @return 随机数
     * @see Random#nextInt(int)
     */
    public int randomInt(int limit) {
        return generator.nextInt(limit);
    }

    /**
     * 获得指定范围内的随机数
     *
     * @param min 最小数（包含）
     * @param max 最大数（不包含）
     * @return 随机数
     */
    public int randomInt(int min, int max) {
        return generator.nextInt(min, max);
    }

    /**
     * 获得指定范围内的随机数
     *
     * @param start 开始值（包含）
     * @param end   结束值（包含）
     * @return 随机数
     */
    public int random(int start, int end) {
        return start + generator.nextInt(end - start + 1);
    }

    /**
     * 获得指定范围内的随机数 (0 ~ end)
     *
     * @param end 结束值（包含）
     * @return 随机数
     */
    public int random(int end) {
        return generator.nextInt(end + 1);
    }

    /**
     * 随机一个 bool 值
     *
     * @return bool 值
     */
    public boolean randomBoolean() {
        return generator.nextBoolean();
    }

    public <T> T randomEle(List<T> list) {

        if (CollKit.isEmpty(list)) {
            return null;
        }

        int size = list.size();

        return size == 1
                ? list.get(0)
                : list.get(randomInt(size));
    }

    public <T> T randomEle(T[] array) {
        Objects.requireNonNull(array);

        return array.length == 1
                ? array[0]
                : array[randomInt(array.length)];
    }
}
