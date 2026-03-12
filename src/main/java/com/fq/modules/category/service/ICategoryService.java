package com.fq.modules.category.service;

import com.fq.modules.category.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 超chao
 * @since 2026-02-13
 */
public interface ICategoryService extends IService<Category> {

    /**
     * 根据分类类型查询
     * @param categoryType
     * @return
     */
    List<Category> listByCategoryType(Integer categoryType);

}
