package com.fq.modules.sys.role.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serial;

/**
 * @Author 用户角色实体类
 * @Description 用户权限实体类
 * @Date 2024/10/14/周一 11:05
 * @Version 1.0
 */
@Data
@TableName("sys_user_role")
public class SysUserRole extends Model<SysUserRole> {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 用户ID
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 角色ID
     */
    @TableField("role_id")
    private String roleId;

}
