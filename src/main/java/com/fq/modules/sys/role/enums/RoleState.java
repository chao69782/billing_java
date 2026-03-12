package com.fq.modules.sys.role.enums;

/**
 * @Author 超chao
 * @Description 角色状态枚举
 * @Date 2024/11/25/周一 10:14
 * @Version 1.0
 */
public enum RoleState {
    /**
     * 正常
     */
    NORMAL(0),
    /**
     * 禁用
     */
    DISABLE(1);

    private final Integer value;

    RoleState(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
