package com.fq.modules.family.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


/**
 * @Author 超chao
 * @Description
 * @Date 2026/2/26 周四 11:23
 * @Version 1.0
 */
@Data
public class EditRemarkDTO {

    @Schema(description = "id",required = true)
    @NotNull(message = "id不能为空")
    private Long id;

    @Schema(description = "备注",required = true)
    @NotBlank(message = "备注不能为空")
    private String remark;
}
