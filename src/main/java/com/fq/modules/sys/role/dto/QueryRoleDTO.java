package com.fq.modules.sys.role.dto;
import com.fq.api.api.dto.PagingReqDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;


/**
 * @Author 超chao
 * @Description 查询角色列表数据传输类
 * @Date 2024/11/25/周一 11:07
 * @Version 1.0
 */
@Data
@Schema(title="查询角色列表数据传输类")
public class QueryRoleDTO extends PagingReqDTO {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "角色名称")
    private String roleName;
    @Schema(description = "角色状态")
    private Integer roleState;
    @Schema(description = "开始时间")
    private String startTime;
    @Schema(description = "结束时间")
    private String endTime;

}
