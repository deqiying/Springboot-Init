package com.deqiying.common.utils;

@SuppressWarnings(value = {"unused"})
public class CurrentLineUtils {
    /**
     * 线程栈基础索引
     */
    private static final int ORIGIN_STACK_INDEX = 2;

    /**
     * 获取当前线程堆栈跟踪元素数组
     *
     * @return 堆栈跟踪元素数组
     */
    public static StackTraceElement[] getCurrentThreadStackTrace() {
        return Thread.currentThread().getStackTrace();
    }

    /**
     * 获取当前线程所在执行文件名称
     *
     * @return 执行文件名称
     */
    public static String getFileName() {
        return getCurrentThreadStackTrace()[ORIGIN_STACK_INDEX].getFileName();
    }


    /**
     * 得到当前线程所在的类名称
     *
     * @return 类名称
     */
    public static String getClassName() {
        return getCurrentThreadStackTrace()[ORIGIN_STACK_INDEX].getClassName();
    }

    /**
     * 得到当前线程所在的方法名称
     *
     * @return 方法名称
     */
    public static String getMethodName() {
        return getCurrentThreadStackTrace()[ORIGIN_STACK_INDEX].getMethodName();
    }

    /**
     * 得到当前线程在第几行
     *
     * @return 第几行
     */
    public static int getLineNumber() {
        return getCurrentThreadStackTrace()[ORIGIN_STACK_INDEX].getLineNumber();
    }
}
