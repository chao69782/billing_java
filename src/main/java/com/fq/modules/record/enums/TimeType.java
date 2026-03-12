package com.fq.modules.record.enums;

import lombok.Getter;

/**
 * @Author 超chao
 * @Description 枚举类 月month 年year
 * @Date 2026/2/25 周三 9:12
 * @Version 1.0
 */
@Getter
public enum TimeType {
    MONTH("month"),
    YEAR("year");

    private final String value;
    TimeType(String value) {
        this.value = value;
    }
}
