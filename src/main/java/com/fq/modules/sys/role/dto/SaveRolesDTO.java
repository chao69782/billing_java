package com.fq.modules.sys.role.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @Author 超chao
 * @Description 保存用户角色数据传输类
 * @Date 2024/10/21/周一 10:34
 * @Version 1.0
 */
@Data
@Schema(title="保存用户角色数据传输类", description="保存用户角色数据传输类")
public class SaveRolesDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "用户ID")
    private Long userId;
    @Schema(description = "角色ID列表")
    private String roleId;
}
