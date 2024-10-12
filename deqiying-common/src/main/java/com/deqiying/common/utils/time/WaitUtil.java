package com.deqiying.common.utils.time;


import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * 等待重试工具类
 *
 * @author qiying
 */
@Slf4j
public class WaitUtil {

    private final static int DEFAULT_WAIT_COUNT = 5;
    private final static TimeUnit DEFAULT_WAIT_TIME_UNIT = TimeUnit.SECONDS;
    private final static int DEFAULT_WAIT_TIME = 2;

    /**
     * 等待条件满足
     * 重复尝试次数
     *
     * @param waitCondition 等待条件
     * @return true: 等待条件满足，false: 等待条件不满足
     */
    public static boolean waitForCondition(Supplier<Boolean> waitCondition) {
        return waitForCondition(waitCondition, DEFAULT_WAIT_COUNT, DEFAULT_WAIT_TIME_UNIT, DEFAULT_WAIT_TIME);
    }

    /**
     * 等待条件满足
     * 重复尝试次数
     *
     * @param waitCondition 等待条件
     * @param retryCount    重复尝试次数
     * @return true: 等待条件满足，false: 等待条件不满足
     */
    public static boolean waitForCondition(Supplier<Boolean> waitCondition, int retryCount) {
        return waitForCondition(waitCondition, retryCount, DEFAULT_WAIT_TIME_UNIT, DEFAULT_WAIT_TIME);
    }

    /**
     * 等待条件满足
     * 等待时间
     *
     * @param waitCondition 等待条件
     * @param timeUnit      等待时间单位
     * @param waitTime      等待时间
     * @return true: 等待条件满足，false: 等待条件不满足
     */
    public static boolean waitForCondition(Supplier<Boolean> waitCondition, TimeUnit timeUnit, int waitTime) {
        return waitForCondition(waitCondition, DEFAULT_WAIT_COUNT, timeUnit, waitTime);
    }

    /**
     * 等待条件满足
     * 重复尝试次数
     * 等待时间
     *
     * @param waitCondition 等待条件
     * @param retryCount    重复尝试次数
     * @param timeUnit      等待时间单位
     * @param waitTime      等待时间
     * @return true: 等待条件满足，false: 等待条件不满足
     */
    public static boolean waitForCondition(Supplier<Boolean> waitCondition, int retryCount, TimeUnit timeUnit, int waitTime) {
        try {
            boolean isWait = waitCondition.get();
            while (isWait && retryCount-- > 0) {
                timeUnit.sleep(waitTime);
                isWait = waitCondition.get();
            }
            return isWait;
        } catch (Throwable throwable) {
            log.error("waitForCondition | wait for condition occur error! error = ", throwable);
            throw new RuntimeException("wait for condition occur error!");
        }
    }

}
