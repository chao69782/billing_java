package com.fq.modules.category.mapper;

import com.fq.modules.category.entity.Category;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 超chao
 * @since 2026-02-13
 */
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
    /**
     * 根据分类类型查询
     * @param categoryType
     * @return
     */
    List<Category> listByCategoryType(Integer categoryType);

}
