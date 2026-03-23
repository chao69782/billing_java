package com.fq.utils;

import cn.hutool.core.date.DateUtil;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author 超chao
 * @Description
 * @Date 2026/1/7 周三 14:13
 * @Version 1.0
 */
public class TimeUtil {
    /**
     * 获取从今天开始往后7天的日期
     */
    public static final int DAYS = 7;

    /**
     * 时间段截取
     */
    public static final int TIME_PERIOD = 5;


    /**
     * 格式化当前时间为 2025-01-01格式
     * @return
     */
    public static String formatCurrentTime() {
        return DateUtil.format(DateUtil.date(), "yyyy-MM-dd");
    }

    /**
     * 时间比较大小
     * @param time1
     * @param time2
     * @return
     */
    public static int compareTime(String time1, String time2) {
        return DateUtil.compare(DateUtil.parse(time1), DateUtil.parse(time2));
    }

    /**
     * 根据日期 2026-01-01格式，查询是星期几，返回周四格式
     * @param date
     * @return
     */
    public static String getWeek(String date) {
        return DateUtil.dayOfWeekEnum(DateUtil.parse(date)).toChinese("周");
    }

    /**
     * 获取从今天开始往后7天的日期 格式为2025-01-01
     * 包含周几
     * 按照key从小到大排序
     */
    public static Map<String,String> getNext7Days() {
        Map<String,String> days = new LinkedHashMap<>();
        for (int i = 0; i < DAYS; i++) {
            String date = DateUtil.format(DateUtil.offsetDay(DateUtil.date(), i), "yyyy-MM-dd");
            String week = getWeek(date);
            days.put(date,week);
        }
        return days;
    }

    /**
     * 计算两个日期之间的小时数
     */
    public static int getHoursBetweenTwoDays(Date startTime, Date endTime) {
        return (int) ((endTime.getTime() - startTime.getTime()) / (1000 * 60 * 60));
    }

    /**
     * 判断某个时间是否小于当前时间
     */
    public static boolean isBefore(String time) {
        return DateUtil.parse(time).getTime() < DateUtil.date().getTime();
    }

    /**
     * 08:00-09:00 截取时间
     */
    public static String getSubstringTimePeriod (String time) {
        return time.substring(0, TIME_PERIOD);
    }


}
