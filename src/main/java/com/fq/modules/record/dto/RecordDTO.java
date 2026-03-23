package com.fq.modules.record.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


/**
 * @Author 超chao
 * @Description
 * @Date 2026/2/14 周六 9:09
 * @Version 1.0
 */

@Data
public class RecordDTO {
    @Schema(description = "金额")
    @NotNull(message = "金额不能为空")
    private Double amount;

    @Schema(description = "分类名称")
    @NotNull(message = "分类名称不能为空")
    private String categoryName;

    @Schema(description = "分类图标")
    @NotNull(message = "分类图标不能为空")
    private String categoryIcon;

    @Schema(description = "备注",requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String remark;

    @Schema(description = "时间")
    @NotBlank(message = "时间不能为空")
    private String recordTime;

    @Schema(description = "1收入 2支出")
    @NotNull(message = "类型不能为空")
    private Integer recordType;
}
