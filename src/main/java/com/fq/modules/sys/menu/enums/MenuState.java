package com.fq.modules.sys.menu.enums;

/**
 * @Author 超chao
 * @Description 菜单状态枚举
 * @Date 2024/11/25/周一 10:14
 * @Version 1.0
 */
public enum MenuState {
    /**
     * 正常
     */
    NORMAL(0),
    /**
     * 禁用
     */
    DISABLE(1);

    private final Integer value;

    MenuState(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
