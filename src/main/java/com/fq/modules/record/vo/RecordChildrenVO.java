package com.fq.modules.record.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @Author 超chao
 * @Description
 * @Date 2026/2/13 周五 13:20
 * @Version 1.0
 */

@Data
public class RecordChildrenVO {

    @Schema(description = "id")
    private Long id;

    @Schema(description = "分类名称")
    private String categoryName;

    @Schema(description = "分类图标")
    private String categoryIcon;

    @Schema(description = "金额")
    private Double amount;

    @Schema(description = "类型")
    private Integer recordType;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "时间")
    private String createTime;
}
