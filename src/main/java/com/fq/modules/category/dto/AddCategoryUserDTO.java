package com.fq.modules.category.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author 超chao
 * @version 1.0
 * @date 2026/3/17 10:35
 * @description
 */
@Data
public class AddCategoryUserDTO {

    /**
     * 分类名称
     */
    @Schema(description = "名称")
    @NotBlank(message = "名称不能为空")
    private String categoryName;

    /**
     * 分类图标
     */
    @Schema(description = "图标")
    @NotBlank(message = "图标不能为空")
    private String categoryIcon;

    /**
     * 1收入 2支出
     */
    @Schema(description = "1收入 2支出")
    @NotNull(message = "分类类型不能为空")
    private Integer categoryType;
}
