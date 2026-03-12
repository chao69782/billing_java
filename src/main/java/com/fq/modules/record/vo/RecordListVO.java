package com.fq.modules.record.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @Author 超chao
 * @Description
 * @Date 2026/2/13 周五 13:18
 * @Version 1.0
 */
@Data
public class RecordListVO {

    @Schema(description = "日期")
    private String date;

    @Schema(description = "周几")
    private String week;

    @Schema(description = "总收入")
    private Double totalIncome;

    @Schema(description = "总支出")
    private Double totalExpense;

    @Schema(description = "记录列表")
    private List<RecordChildrenVO> list;

}
