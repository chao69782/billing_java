package com.fq.modules.budget.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @Author 超chao
 * @Description
 * @Date 2026/2/26 周四 9:20
 * @Version 1.0
 */
@Data
public class BudgetVO {
    @Schema(description = "总预算")
    private Double totalBudget;

    @Schema(description = "剩余预算")
    private Double remainBudget;

    @Schema(description = "本月支出")
    private Double monthExpense;

    @Schema(description = "剩余预算百分比")
    private Double budgetPercent;
}
