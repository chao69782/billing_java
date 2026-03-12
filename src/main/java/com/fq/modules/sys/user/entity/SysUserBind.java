package com.fq.modules.sys.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serial;

/**
 * @Author 超chao
 * @Description 登录绑定实体类
 * @Date 2026/1/8 周四 11:30
 * @Version 1.0
 */
@Data
@TableName("sys_user_bind")
public class SysUserBind extends Model<SysUserBind> {
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
     * 登录类型
     */
    @TableField("login_type")
    private String loginType;

    /**
     * 三方ID
     */
    @TableField("open_id")
    private String openId;
}
