package com.deqiying.common.utils;


import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;

/**
 * 时间日期工具类
 *
 * @author qiying
 */
public class DateTimeUtils {
    /**
     * 时间格式常量
     */
    public static final String COMMON_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String COMMON_PATTERN_TYPE2 = "yyyy/MM/dd HH:mm:ss";
    public static final String SHORT_PATTERN = "yyyy-MM-dd";
    public static final String SHORT_PATTERN_TYPE2 = "yyyy/MM/dd";
    public static final String LONG_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String SUP_SHORT_PATTERN = "yyyyMMdd";
    public static final String SUP_LONG_PATTERN = "yyyyMMddHHmmss";
    public static final String YEAR_MONTH = "yyyyMM";
    public static final String CN_SHORT_PATTERN = "yyyy年MM月dd日";
    public static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
    public static DateTimeFormatter DATE_FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd");

    public static String formatDateTime(DateTime dateTime) {
        return dateTime.toString("yyyy-MM-dd HH:mm:ss");
    }

    public static String formatDate(DateTime dateTime) {
        return dateTime.toString("yyyy-MM-dd");
    }

    /**
     * 格式化时间
     *
     * @param time 时间
     * @return 格式化后的时间
     */
    public static String formatDateTime(Long time) {
        if (time == null || time < 0) {
            return null;
        }
        return formatDateTime(new DateTime(time));
    }

    /**
     * 格式化时间
     *
     * @param time 时间
     * @return 格式化后的时间
     */
    public static String formatDate(Long time) {
        if (time == null || time < 0) {
            return null;
        }
        return formatDate(new DateTime(time));
    }

    /**
     * 根据时间字符串解析时间
     *
     * @param dateTimeStr 时间字符串
     * @return 时间
     */
    public static DateTime parseByDateTimeStr(String dateTimeStr) {
        return DateTime.parse(dateTimeStr, DATE_TIME_FORMATTER);
    }

    /**
     * 根据时间字符串解析时间
     *
     * @param dateTimeStr 时间字符串
     * @return 时间
     */
    public static DateTime parseByDateStr(String dateTimeStr) {
        return DateTime.parse(dateTimeStr, DATE_FORMATTER);
    }

    /**
     * 获取上一个周期的开始时间
     *
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @return 上一个周期的开始时间
     */
    public static DateTime getPreviousCycleBeginTime(DateTime beginTime, DateTime endTime) {
        DateTime previousCycleBeginTime = null;
        if (beginTime != null && endTime != null) {
            // 计算周期的长度
            Period cyclePeriod = new Period(beginTime, endTime);
            // 获取上一个周期的开始时间
            previousCycleBeginTime = beginTime.minus(cyclePeriod);
        }
        return previousCycleBeginTime;
    }

    /**
     * 获取一天的24小时
     *
     * @return 小时列表
     */
    public static List<String> getHoursInOneDay() {
        DateTime timeAtStartOfDay = DateTime.now().withTimeAtStartOfDay();
        List<String> hourList = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormat.forPattern("HH:mm");
        for (int i = 0; i < 24; i++) {
            String hourString = timeAtStartOfDay.toString(formatter);
            hourList.add(hourString);
            // 增加一小时
            timeAtStartOfDay = timeAtStartOfDay.plusHours(1);
        }
        return hourList;
    }

    /**
     * 获取两个日期之间的天数
     *
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @return 天数列表
     */
    public static List<String> getDayBetweenTwoDate(Long beginTime, Long endTime) {
        List<String> dayList = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");
        DateTime beginDateTime = new DateTime(beginTime).withTimeAtStartOfDay();
        DateTime endDateTime = new DateTime(endTime).withTimeAtStartOfDay();
        while (!beginDateTime.isAfter(endDateTime)) {
            String dayString = beginDateTime.toString(formatter);
            dayList.add(dayString);
            // 增加一天
            beginDateTime = beginDateTime.plusDays(1);
        }
        return dayList;
    }

    /**
     * 获取两个日期之间的月份
     *
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @return 月份列表
     */
    public static List<String> getMonthBetweenTwoDate(Long beginTime, Long endTime) {
        List<String> monthList = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM");
        DateTime beginDateTime = new DateTime(beginTime).withDayOfMonth(1).withTimeAtStartOfDay();
        DateTime endDateTime = new DateTime(endTime).withDayOfMonth(1).withTimeAtStartOfDay();
        while (!beginDateTime.isAfter(endDateTime)) {
            String monthString = beginDateTime.toString(formatter);
            monthList.add(monthString);
            // 增加一个月
            beginDateTime = beginDateTime.plusMonths(1);
        }
        return monthList;
    }

}
