package com.fq.modules.record.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @Author 超chao
 * @Description
 * @Date 2026/2/25 周三 9:35
 * @Version 1.0
 */
@Data
public class RankingListVO {
    @Schema(description = "金额")
    private Double amount;

    @Schema(description = "分类名称")
    private String categoryName;

    @Schema(description = "分类图标")
    private String categoryIcon;

    @Schema(description = "百分比")
    private Double percent;

}
