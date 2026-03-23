package com.fq.modules.budget.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


/**
 * @Author 超chao
 * @Description
 * @Date 2026/2/26 周四 9:06
 * @Version 1.0
 */
@Data
public class SetBudgetDTO {

    @Schema(description = "月份")
    @NotBlank(message = "月份不可为空")
    private String monthStr;

    @Schema(description = "预算")
    @NotNull(message = "预算不可为空")
    private Double budget;

}
