package com.fq.modules.sys.user.entity;

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
 * @Description 系统用户实体类
 * @Date 2025/2/5/周三 9:18
 * @Version 1.0
 */
@Data
@TableName("sys_user")
public class SysUser extends Model<SysUser> {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 用户名
     */
    @TableField("user_name")
    private String userName;
    /**
     * 昵称
     */
    @TableField("nick_name")
    private String nickName;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 人脸照片
     */
    private String face;

    /**
     * 真实姓名
     */
    @TableField("real_name")
    private String realName;

    /**
     * 密码
     */
    private String password;

    /**
     * 状态
     */
    private Integer state;

    /**
     * 身份证号码
     */
    @TableField("id_card")
    private String idCard;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 邮箱
     */
    private String email;
    /**
     * 性别
     */
    private Integer gender;
    /**
     * 出生年月
     */
    private String birthday;
    /**
     * 账号类型
     */
    @TableField("account_type")
    private Integer accountType;

    /**
     * 邀请码
     */
    @TableField("invite_code")
    private String inviteCode;
    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    private Date updateTime;
    /**
     * 创建人
     */
    @TableField(value = "create_by")
    private Long createBy;
    /**
     * 更新人
     */
    @TableField(value = "update_by")
    private Long updateBy;

}