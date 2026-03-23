package com.fq.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * @Author 超chao
 * @Description MD5加密工具类
 * @Date 2024/10/12/周六 14:30
 * @Version 1.0
 */
public class MD5Util {
    /**
     * 开始
     */
    public static final Integer START = 1;
    /**
     * 结束
     */
    public static final Integer END = 3;
    /**
     * 简单MD5
     *
     * @param str
     * @return
     */
    public static String md5(String str) {

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] array = md.digest(str.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte item : array) {
                sb.append(Integer.toHexString((item & 0xFF) | 0x100), START, END);
            }
            return sb.toString();
        } catch (Exception e) {
            return null;
        }
    }

}
