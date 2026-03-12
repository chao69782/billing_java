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
 * 
 * </p>
 *
 * @author 超chao
 * @since 2026-02-13
 */
@Data
@TableName("category")
@Schema(title = "Category对象", description = "")
public class Category extends Model<Category> {

    @Schema(description = "ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @Schema(description = "名称")
    @TableField("category_name")
    private String categoryName;

    @Schema(description = "图标")
    @TableField("category_icon")
    private String categoryIcon;

    @Schema(description = "1收入 2支出")
    @TableField("category_type")
    private Integer categoryType;


}
