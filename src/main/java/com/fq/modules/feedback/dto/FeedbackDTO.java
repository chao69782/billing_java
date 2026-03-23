package com.fq.modules.feedback.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * @Author 超chao
 * @Description
 * @Date 2026/2/27 周五 14:59
 * @Version 1.0
 */
@Data
public class FeedbackDTO {
    @Schema(description = "反馈内容")
    @NotBlank(message = "反馈内容不能为空")
    @Size(max = 500,message = "反馈内容不能超过500个字符")
    private String content;

    @Schema(description = "联系方式")
    @NotBlank(message = "联系方式不能为空")
    @Size(max = 200,message = "联系方式不能超过200个字符")
    private String contact;
}
