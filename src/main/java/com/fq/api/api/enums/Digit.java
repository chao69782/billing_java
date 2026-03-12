package com.fq.api.api.enums;

/**
 * @Author 超chao
 * @Description 位数枚举
 * @Date 2024/10/28/周一 11:45
 * @Version 1.0
 */
public enum Digit {
    /**
     * 4位
     */
    FOUR(4),
    /**
     * 6位
     */
    SIX(6),
    /**
     * 8位
     */
    EIGHT(8),
    /**
     * 11位
     */
    ELEVEN(11),
    /**
     * 16位
     */
    SIXTEEN(16),
    /**
     * 32位
     */
    THIRTY_TWO(32);

    private final int digit;

    Digit(int digit) {
        this.digit = digit;
    }

    public int getDigit() {
        return digit;
    }

}
