package com.fq.modules.sys.role.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serial;

/**
 * @Author 超chao
 * @Description 角色菜单授权实体类
 * @Date 2024/11/22/周五 16:14
 * @Version 1.0
 */
@Data
@TableName("sys_role_menu")
public class SysRoleMenu extends Model<SysRoleMenu> {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 角色ID
     */
    @TableField("role_id")
    private String roleId;

    /**
     * 菜单ID
     */
    @TableField("menu_id")
    private Long menuId;
}
