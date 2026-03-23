package com.fq.modules.sys.role.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author 超chao
 * @Description 角色传输类
 * @Date 2024/10/14/周一 11:09
 * @Version 1.0
 */
@Data
@Schema(title="角色传输类", description="角色传输类")
public class SysRoleDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;


    @Schema(description = "角色ID")
    @NotBlank(message = "角色ID不能为空")
    private String id;

    @Schema(description = "角色名称")
    @NotBlank(message = "角色名称不能为空")
    private String roleName;

    @Schema(description = "备注信息",requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String remark;

    @Schema(description = "角色状态 0正常 1禁用", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Integer roleState;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Date createTime;

    @Schema(description = "更新时间", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Date updateTime;

    @Schema(description = "数据标识", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Integer dataFlag;

}
