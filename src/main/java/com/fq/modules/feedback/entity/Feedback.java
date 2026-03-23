package com.fq.modules.feedback.entity;

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
 * @since 2026-02-27
 */
@Data
@TableName("feedback")
@Schema(title = "Feedback对象", description = "")
public class Feedback extends Model<Feedback> {

    @Schema(description = "ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @Schema(description = "用户ID")
    @TableField("user_id")
    private Long userId;

    @Schema(description = "内容")
    @TableField("content")
    private String content;

    @Schema(description = "联系方式")
    @TableField("contact")
    private String contact;

    @Schema(description = "创建时间")
    @TableField("create_time")
    private Date createTime;


}
