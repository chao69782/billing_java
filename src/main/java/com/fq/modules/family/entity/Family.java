package com.fq.modules.family.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * <p>
 * 
 * </p>
 *
 * @author 超chao
 * @since 2026-02-25
 */
@Data
@TableName("family")
@Schema(title = "Family对象", description = "")
public class Family extends Model<Family> {

    @Schema(description = "ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @Schema(description = "分组ID")
    @TableField("group_id")
    private Long groupId;

    @Schema(description = "用户ID")
    @TableField("user_id")
    private Long userId;

    @Schema(description = "备注")
    @TableField("remark")
    private String remark;

    @Schema(description = "角色 0管理员 1成员")
    @TableField("role")
    private Integer role;

}
