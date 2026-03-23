package com.fq.modules.sys.role.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @Author 超chao
 * @Description 更改角色状态数据传输类
 * @Date 2024/11/25/周一 10:16
 * @Version 1.0
 */
@Data
@Schema(title="更改角色状态数据传输类")
public class ChangeRoleStateDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "角色ID")
    @NotBlank(message = "角色ID不能为空")
    private String roleId;
    @Schema(description = "角色状态 0启用 1禁用")
    @NotNull(message = "角色状态不能为空")
    private Integer roleState;
}
