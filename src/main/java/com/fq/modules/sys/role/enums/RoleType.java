package com.fq.modules.sys.role.enums;

import lombok.Getter;

/**
 * @Author 超chao
 * @Description 角色类型
 * @Date 2025/1/25/周六 9:44
 * @Version 1.0
 */
@Getter
public enum RoleType {
    /**
     * 普通用户
     */
    ROLE_USER("ordinary_user"),
    /**
     * 其他管理员
     */
    ROLE_ADMIN("admin"),
    /**
     * 超级管理员
     */
    ROLE_SUPER_ADMIN("super_admin");

    private final String type;

    RoleType(String type) {
        this.type = type;
    }
}

