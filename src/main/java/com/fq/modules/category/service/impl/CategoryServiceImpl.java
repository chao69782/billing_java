package com.fq.modules.category.service.impl;

import com.fq.modules.category.entity.Category;
import com.fq.modules.category.mapper.CategoryMapper;
import com.fq.modules.category.service.ICategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 超chao
 * @since 2026-02-13
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements ICategoryService {

    @Override
    public List<Category> listByCategoryType(Integer categoryType) {
        return baseMapper.listByCategoryType(categoryType);
    }
}
