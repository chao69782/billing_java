package com.fq.modules.sys.user.enums;

import lombok.Getter;

/**
 * @Author 超chao
 * @Description 账号类型 0 普通用户 1 管理员
 * @Date 2025/8/8/周五 9:13
 * @Version 1.0
 */
@Getter
public enum AccountType {
    USER(0),
    ADMIN(1);
    private final Integer type;
    AccountType(Integer type) {
        this.type = type;
    }
}
