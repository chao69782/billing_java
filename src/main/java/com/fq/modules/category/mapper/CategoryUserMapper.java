package com.fq.modules.category.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fq.modules.category.entity.CategoryUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 用户分类 Mapper 接口
 * </p>
 *
 * @author 超chao
 * @since 2026-03-17
 */
@Mapper
public interface CategoryUserMapper extends BaseMapper<CategoryUser> {

    /**
     * 删除分类
     * @param id
     */
    void delete(@Param("id") Long id, @Param("userId") Long userId);

    /**
     * 查询分类排序值
     * @param userId
     * @return
     */
    int querySortValue(@Param("userId") Long userId);

}
