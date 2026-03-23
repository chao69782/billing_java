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
public class EditCategoryUserDTO {

    @Schema(description = "ID")
    @NotNull(message = "ID不能为空")
    private Long id;

    /**
     * 分类图标
     */
    @Schema(description = "图标",requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String categoryIcon;

    /**
     * 分类名称
     */
    @Schema(description = "名称")
    @NotBlank(message = "名称不能为空")
    private String categoryName;
}
