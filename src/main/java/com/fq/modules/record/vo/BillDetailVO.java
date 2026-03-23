package com.fq.modules.record.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @Author 超chao
 * @Description
 * @Date 2026/2/26 周四 15:51
 * @Version 1.0
 */
@Data
public class BillDetailVO {
    @Schema(description = "收入")
    private Double income;

    @Schema(description = "支出")
    private Double expense;

    @Schema(description = "日期")
    private String timeStr;
}
