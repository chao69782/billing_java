package com.fq.modules.sys.menu.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author 超chao
 * @Description 系统菜单数据传输类
 * @Date 2024/11/21/周四 10:06
 * @Version 1.0
 */
@Data
@Schema(title = "系统菜单数据传输类")
public class SysMenuDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "ID")
    private Long id;

    @Schema(description = "上级菜单", required=true)
    @NotNull(message="上级菜单不能为空")
    private Long parentId;

    @Schema(description = "1菜单2功能", required=true)
    @NotNull(message="菜单类型不能为空")
    private Integer menuType;

    @Schema(description = "权限标识", required=true)
    @NotBlank(message="权限标识不能为空")
    private String permissionTag;

    @Schema(description = "访问路径-菜单必填路径-功能按钮不填路径")
    private String path;

    @Schema(description = "视图或Layout")
    private String component;

    @Schema(description = "跳转地址")
    private String redirect;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "路由标题", required=true)
    @NotBlank(message="路由标题不能为空")
    private String metaTitle;

    @Schema(description = "路由图标")
    private String metaIcon;

    @Schema(description = "高亮菜单")
    private String metaActiveMenu;

    @Schema(description = "是否缓存")
    private Boolean metaNoCache;

    @Schema(description = "是否隐藏")
    private Boolean hidden;

    @Schema(description = "越小越前")
    private Integer sort;

    @Schema(description = "状态")
    private Integer state;

    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "更新时间")
    private Date updateTime;

    @Schema(description = "创建人")
    private String createBy;

    @Schema(description = "修改人")
    private String updateBy;

    @Schema(description = "数据标识")
    private Integer dataFlag;
}
