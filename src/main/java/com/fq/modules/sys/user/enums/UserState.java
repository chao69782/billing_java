package com.fq.modules.sys.user.enums;

import lombok.Getter;

/**
 * @Author 超chao
 * @Description 用户状态
 * @Date 2025/2/5/周三 9:30
 * @Version 1.0
 */
@Getter
public enum UserState {
    /**
     * 正常的
     */
    NORMAL(0),
    /**
     * 禁用
     */
    DISABLED(1),
    ;

    private final int state;

    UserState(int state) {
        this.state = state;
    }

}
