package com.fq.modules.category.service;

import com.fq.modules.category.dto.AddCategoryUserDTO;
import com.fq.modules.category.dto.EditCategoryUserDTO;
import com.fq.modules.category.entity.CategoryUser;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户分类 服务类
 * </p>
 *
 * @author 超chao
 * @since 2026-03-17
 */
public interface ICategoryUserService extends IService<CategoryUser> {

    /**
     * 删除分类
     * @param id
     */
    void delete(Long id);

    /**
     * 添加
     * @param reqDTO
     */
    void add(AddCategoryUserDTO reqDTO);

    /**
     * 编辑
     * @param reqDTO
     */
    void edit(EditCategoryUserDTO reqDTO);

    /**
     * 排序
     * @param ids
     */
    void sort(List<Long> ids);

    /**
     * 批量插入用户分类
     * @param userId
     */
    void batchInsert(Long userId);


}
