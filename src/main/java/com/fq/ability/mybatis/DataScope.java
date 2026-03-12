package com.fq.ability.mybatis;

import lombok.Getter;

/**
 * @Author 超chao
 * @Description 0本机关单位 1所有
 * @Date 2026/1/5 周一 9:07
 * @Version 1.0
 */
@Getter
public enum DataScope {
    SELF(0),
    ALL(1);

    private final int scope;
    DataScope(int scope) {
        this.scope = scope;
    }
}
