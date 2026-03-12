package com.fq.modules.sys.role.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * @Author 超chao
 * @Description 角色菜单授权请求类
 * @Date 2024/10/14/周一 14:55
 * @Version 1.0
 */
@Data
@Schema(title="角色菜单授权请求类", description="角色菜单授权请求类")
public class SysRoleMenuReqDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "角色ID", required=true)
    @NotBlank(message = "角色ID不能为空")
    private String roleId;

    @Schema(description = "菜单ID列表", required=true)
    @NotEmpty(message = "菜单ID列表不能为空")
    private List<Long> menuIds;

}
