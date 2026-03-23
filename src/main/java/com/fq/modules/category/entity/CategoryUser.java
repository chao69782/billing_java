package com.fq.modules.category.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


/**
 * <p>
 * 用户分类
 * </p>
 *
 * @author 超chao
 * @since 2026-03-17
 */
@Data
@TableName("category_user")
@Schema(title = "CategoryUser对象", description = "用户分类")
public class CategoryUser extends Model<CategoryUser> {

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @Schema(description = "ID")
    private Long id;

    /**
     * 分类名称
     */
    @TableField("category_name")
    @Schema(description = "名称")
    private String categoryName;

    /**
     * 分类图标
     */
    @TableField("category_icon")
    @Schema(description = "图标")
    private String categoryIcon;

    /**
     * 分类类型 1收入 2支出
     */
    @Schema(description = "1收入 2支出")
    @TableField("category_type")
    private Integer categoryType;

    /**
     * 用户ID
     */
    @TableField("user_id")
    @Schema(description = "用户ID")
    private Long userId;

    /**
     * 排序
     */
    @TableField("sort")
    @Schema(description = "排序")
    private Integer sort;
}
