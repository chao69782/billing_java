package com.fq.modules.family.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @Author 超chao
 * @Description
 * @Date 2026/2/25 周三 17:03
 * @Version 1.0
 */
@Data
public class MemberListVO {
    @Schema(description = "ID")
    private Long Id;

    @Schema(description = "昵称")
    private String nickName;

    @Schema(description = "用户ID")
    private Long userId;

    @Schema(description = "角色 0管理员 1成员")
    private Integer role;
}
