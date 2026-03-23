package com.fq.modules.category.service.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fq.modules.category.dto.AddCategoryUserDTO;
import com.fq.modules.category.dto.EditCategoryUserDTO;
import com.fq.modules.category.entity.Category;
import com.fq.modules.category.entity.CategoryUser;
import com.fq.modules.category.mapper.CategoryUserMapper;
import com.fq.modules.category.service.ICategoryService;
import com.fq.modules.category.service.ICategoryUserService;
import com.fq.utils.user.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 用户分类 服务实现类
 * </p>
 *
 * @author 超chao
 * @since 2026-03-17
 */
@Service
public class CategoryUserServiceImpl extends ServiceImpl<CategoryUserMapper, CategoryUser> implements ICategoryUserService {

    @Autowired
    private ICategoryService categoryService;

    @Override
    public void delete(Long id) {
        baseMapper.delete(id, UserUtils.getUserId());
    }

    @Override
    public void add(AddCategoryUserDTO reqDTO) {
        Long userId = UserUtils.getUserId();
        int sort = baseMapper.querySortValue(userId);
        CategoryUser entity = new CategoryUser();
        entity.setId(IdWorker.getId());
        entity.setCategoryName(reqDTO.getCategoryName());
        entity.setCategoryIcon(reqDTO.getCategoryIcon());
        entity.setCategoryType(reqDTO.getCategoryType());
        entity.setUserId(userId);
        entity.setSort(sort + 1);

        this.save(entity);

    }

    @Override
    public void edit(EditCategoryUserDTO reqDTO) {
        CategoryUser entity = new CategoryUser();
        entity.setId(reqDTO.getId());
        entity.setCategoryName(reqDTO.getCategoryName());
        entity.setCategoryIcon(reqDTO.getCategoryIcon());
        this.updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void sort(List<Long> ids) {
        int sort = 0;
        List<CategoryUser> list = new ArrayList<>();
        for (Long id : ids){
            CategoryUser entity = new CategoryUser();
            entity.setId(id);
            entity.setSort(sort++);
            list.add(entity);

        }
        this.updateBatchById(list);
    }

    @Override
    public void batchInsert(Long userId) {
        int sort = 0;
        List<Category> categoryList = categoryService.list();
        List<CategoryUser> list = new ArrayList<>();
        for (Category category : categoryList){
            CategoryUser entity = new CategoryUser();
            entity.setId(IdWorker.getId());
            entity.setCategoryName(category.getCategoryName());
            entity.setCategoryIcon(category.getCategoryIcon());
            entity.setCategoryType(category.getCategoryType());
            entity.setUserId(userId);
            entity.setSort(sort++);
            list.add(entity);
        }
        this.saveBatch(list);
    }
}
