package com.fq.modules.budget.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * <p>
 * 月预算
 * </p>
 *
 * @author 超chao
 * @since 2026-02-26
 */
@Data
@TableName("budget")
@Schema(title = "Budget对象", description = "月预算")
public class Budget extends Model<Budget> {

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @TableField("user_id")
    private Long userId;

    @TableField("month_str")
    private String monthStr;

    @TableField("budget")
    private Double budget;


}
