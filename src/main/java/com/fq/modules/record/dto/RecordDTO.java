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
    @Schema(description = "金额",required = true)
    @NotNull(message = "金额不能为空")
    private Double amount;

    @Schema(description = "分类",required = true)
    @NotNull(message = "分类不能为空")
    private Long categoryId;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "时间",required = true)
    @NotBlank(message = "时间不能为空")
    private String recordTime;

    @Schema(description = "1收入 2支出",required = true)
    @NotNull(message = "类型不能为空")
    private Integer recordType;
}
