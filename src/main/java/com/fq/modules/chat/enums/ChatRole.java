package com.fq.modules.chat.enums;

import lombok.Getter;

/**
 * @author 超chao
 * @version 1.0
 * @date 2026/3/11 16:00
 * @description 角色(user/assistant/system)
 */
@Getter
public enum ChatRole {
    USER("user"),
    ASSISTANT("assistant"),
    SYSTEM("system");

    private final String role;
    ChatRole(String role) {
        this.role = role;
    }
}
