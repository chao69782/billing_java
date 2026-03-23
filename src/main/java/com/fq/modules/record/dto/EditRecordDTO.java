package com.fq.modules.record.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


/**
 * @Author 超chao
 * @Description
 * @Date 2026/2/27 周五 14:28
 * @Version 1.0
 */
@Data
public class EditRecordDTO {
    @Schema(description = "ID")
    @NotNull(message = "ID不能为空")
    private Long id;

    @Schema(description = "金额",requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Double amount;

    @Schema(description = "分类名称",requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String categoryName;

    @Schema(description = "分类图标",requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String categoryIcon;

    @Schema(description = "备注",requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String remark;

    @Schema(description = "时间",requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String recordTime;

    @Schema(description = "1收入 2支出",requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Integer recordType;
}
