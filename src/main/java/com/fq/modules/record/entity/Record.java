package com.fq.modules.record.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author 超chao
 * @since 2026-02-13
 */
@Data
@TableName("record")
@Schema(title = "Record对象", description = "")
public class Record extends Model<Record> {

    @Schema(description = "ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @Schema(description = "用户ID")
    @TableField("user_id")
    private Long userId;

    @Schema(description = "金额")
    @TableField("amount")
    private Double amount;

    @Schema(description = "分类")
    @TableField("category_id")
    private Long categoryId;

    @Schema(description = "备注")
    @TableField("remark")
    private String remark;

    @Schema(description = "时间")
    @TableField("record_time")
    private String recordTime;

    @Schema(description = "1收入  2支出")
    @TableField("record_type")
    private Integer recordType;

    @Schema(description = "创建时间")
    @TableField("create_time")
    private Date createTime;


}
