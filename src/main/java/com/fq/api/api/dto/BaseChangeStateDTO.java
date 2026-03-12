package com.fq.api.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @Author 超chao
 * @Description 基础更新状态数据传输类
 * @Date 2024/11/25/周一 14:08
 * @Version 1.0
 */
@Data
@Schema(title = "更改状态数据传输类")
public class BaseChangeStateDTO<T> {
    @Schema(description = "角色ID", required=true)
    @NotNull(message = "ID不能为空")
    private T id;
    @Schema(description = "状态", required=true)
    @NotNull(message = "状态不能为空")
    private Integer state;
    @Schema(description = "备注")
    private String remark;
}
