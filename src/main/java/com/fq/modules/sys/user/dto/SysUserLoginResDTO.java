package com.fq.modules.sys.user.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * @Author 超chao
 * @Description 系统用户登录传输类
 * @Date 2024/10/18/周五 11:02
 * @Version 1.0
 */
@Data
@Schema(title="系统用户登录传输类", description="系统用户登录传输类")
public class SysUserLoginResDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;


    @Schema(description = "ID")
    private Long id;

    @Schema(description = "用户名")
    private String userName;

    @Schema(description = "昵称")
    private String nickName;

    @Schema(description = "头像")
    private String avatar;

    @Schema(description = "真实姓名")
    private String realName;

    @Schema(description = "身份证号")
    private String idCard;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "手机")
    private String mobile;

    @Schema(description = "状态")
    private Integer state;

    @Schema(description = "角色类型")
    private Integer roleType;

    @Schema(description = "角色")
    private String role;

    @Schema(description = "权限列表")
    private List<String> permissions;

    @Schema(description = "登录令牌")
    private String token;

    @Schema(description = "是否有密码")
    private Boolean isHavePassword;

    @Schema(description = "邀请码")
    private String inviteCode;
}