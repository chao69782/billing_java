package com.fq.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @Author 超chao
 * @Description
 * @Date 2026/2/26 周四 9:31
 * @Version 1.0
 */
public class AmountUtil {
    private static final int AMOUNT_FORMAT_LENGTH = 2;

    /**
     * double金额相减，保留2位小数
     *
     * @param amount1
     * @param amount2
     * @return
     */
    public static double sub(double amount1, double amount2) {
        BigDecimal bigDecimal1 = new BigDecimal(amount1);
        BigDecimal bigDecimal2 = new BigDecimal(amount2);
        return bigDecimal1.subtract(bigDecimal2).setScale(AMOUNT_FORMAT_LENGTH, RoundingMode.HALF_UP).doubleValue();
    }

    /**
     * double金额相除，保留2位小数
     *
     * @param amount1
     * @param amount2
     * @return
     */
    public static double div(double amount1, double amount2) {
        if (amount2 == 0) {
            return 0;
        }
        BigDecimal bigDecimal1 = new BigDecimal(amount1);
        BigDecimal bigDecimal2 = new BigDecimal(amount2);
        return bigDecimal1.divide(bigDecimal2, AMOUNT_FORMAT_LENGTH, RoundingMode.HALF_UP).doubleValue();
    }

}
