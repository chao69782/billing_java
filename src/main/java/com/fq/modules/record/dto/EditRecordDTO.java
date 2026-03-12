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
    @Schema(description = "ID",required = true)
    @NotNull(message = "ID不能为空")
    private Long id;

    @Schema(description = "金额")
    private Double amount;

    @Schema(description = "分类")
    private Long categoryId;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "时间")
    private String recordTime;

    @Schema(description = "1收入 2支出")
    private Integer recordType;
}
