package com.fq.modules.sys.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * @author 超chao
 * @version 1.0
 * @date 2026/3/5 8:56
 * @description
 */
@Data
@Schema(title="系统用户登录传输类", description="系统用户登录传输类")
public class SysUserLoginDTO {

    @Schema(description = "账号")
    @NotBlank(message = "账号不能为空")
    @Size(max = 20, message = "账号长度不能超过20")
    private String userName;

    @Schema(description = "密码")
    @NotBlank(message = "密码不能为空")
    @Size(max = 20, message = "密码长度不能超过20")
    private String password;

    @Schema(description = "验证码")
    @NotBlank(message = "验证码不能为空")
    private String captcha;

    @Schema(description = "验证码key")
    @NotBlank(message = "验证码key不能为空")
    private String captchaKey;
}
