package com.fq.modules.sys.role.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serial;
import java.util.Date;

/**
 * @Author 超chao
 * @Description 角色实体类
 * @Date 2024/10/14/周一 11:05
 * @Version 1.0
 */
@Data
@TableName("sys_role")
public class SysRole extends Model<SysRole> {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 角色ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 角色名称
     */
    @TableField("role_name")
    private String roleName;
    /**
     * 角色类型
     */
    @TableField("role_type")
    private Integer roleType;

    /**
     * 备注信息
     */
    private String remark;
    /**
     * 角色状态
     */
    @TableField("role_state")
    private Integer roleState;

    /**
     * 创建时间
     */
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time",fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * 数据标识
     */
    @TableField("data_flag")
    private Integer dataFlag;

}
