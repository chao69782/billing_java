package com.fq.modules.record.enums;

import lombok.Getter;

/**
 * @author 超chao
 * @version 1.0 1支出 2收入
 * @date 2026/3/9 14:22
 * @description
 */
@Getter
public enum RecordType {
    OUTCOME(1, "支出"),
    INCOME(2, "收入");

    private final Integer code;
    private final String des;
    RecordType(Integer code, String des) {
        this.code = code;
        this.des = des;
    }
}
