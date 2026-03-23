package com.fq.modules.sys.log.dto;

import com.fq.api.api.dto.PagingReqDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * @Author 超chao
 * @Description 查询日志参数
 * @Date 2024/12/23/周一 11:07
 * @Version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(title = "查询日志参数")
public class QuerySysLogDTO extends PagingReqDTO {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "操作人")
    private String operator;
    @Schema(description = "操作状态")
    private Integer status;
    @Schema(description = "开始时间")
    private String startTime;
    @Schema(description = "结束时间")
    private String endTime;
    @Schema(description = "标题")
    private String title;
}
