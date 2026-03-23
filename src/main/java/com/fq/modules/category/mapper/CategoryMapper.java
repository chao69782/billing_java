package com.fq.modules.category.mapper;

import com.fq.modules.category.entity.Category;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import dev.langchain4j.agent.tool.P;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
    List<Category> listByCategoryType(@Param("userId") Long userId,@Param("categoryType") Integer categoryType);

    /**
     * 根据用户ID查询
     * @param userId
     * @return
     */
    List<Category> listByUserId(@Param("userId") Long userId);

}
