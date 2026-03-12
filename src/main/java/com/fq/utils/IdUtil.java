package com.fq.utils;

/**
 * @Author 超chao
 * @Description
 * @Date 2025/12/30 周二 11:51
 * @Version 1.0
 */
public class IdUtil {
    /**
     * 判断Long不为null切不为0L
     */
    public static boolean isNotNullAndNotZero(Long id) {
        return id != null && id != 0L;
    }

    /**
     * 判断Long为null或者为0L
     */
    public static boolean isNullOrZero(Long id) {
        return id == null || id == 0L;
    }
}
