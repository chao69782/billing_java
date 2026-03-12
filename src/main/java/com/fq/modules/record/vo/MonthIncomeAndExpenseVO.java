package com.fq.modules.record.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @Author 超chao
 * @Description
 * @Date 2026/2/25 周三 16:03
 * @Version 1.0
 */
@Data
public class MonthIncomeAndExpenseVO {
    @Schema(description = "收入")
    private Double income;

    @Schema(description = "支出")
    private Double expense;

}
