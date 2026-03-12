package com.fq.modules.sys.user.enums;

import lombok.Getter;

/**
 * @Author 超chao
 * @Description 登录方式枚举
 * @Date 2026/1/8 周四 11:30
 * @Version 1.0
 */
@Getter
public enum LoginType {
    /**
     * 微信登录
     */
    WECHAT("WECHAT"),

    ;
    private final String type;

    LoginType(String type) {
        this.type = type;
    }

}
