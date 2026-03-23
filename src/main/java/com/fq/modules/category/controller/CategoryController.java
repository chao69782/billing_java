package com.fq.modules.category.controller;


import com.fq.api.api.ApiRest;
import com.fq.api.api.controller.BaseController;
import com.fq.modules.category.dto.AddCategoryUserDTO;
import com.fq.modules.category.dto.EditCategoryUserDTO;
import com.fq.modules.category.entity.Category;
import com.fq.modules.category.service.ICategoryService;
import com.fq.modules.category.service.ICategoryUserService;
import com.fq.utils.user.UserUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 超chao
 * @since 2026-02-13
 */
@Tag(name = "分类")
@RestController
@RequestMapping("/api/front/category")
public class CategoryController extends BaseController {
    @Autowired
    private ICategoryService baseService;
    @Autowired
    private ICategoryUserService categoryUserService;

    @GetMapping("/listByCategoryType")
    @Operation(summary = "获取分类列表")
    public ApiRest<List<Category>> listByCategoryType(@RequestParam(value = "categoryType") Integer categoryType){
        return super.success(baseService.listByCategoryType(UserUtils.getUserId(),categoryType));
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除分类")
    public ApiRest<Void> delete(@RequestParam(value = "id") Long id){
        categoryUserService.delete(id);
        return super.success();
    }

    @PostMapping("/add")
    @Operation(summary = "添加分类")
    public ApiRest<Void> add(@Validated @RequestBody AddCategoryUserDTO reqDTO){
        categoryUserService.add(reqDTO);
        return super.success();
    }

    @PutMapping("/edit")
    @Operation(summary = "编辑分类")
    public ApiRest<Void> edit(@Validated @RequestBody EditCategoryUserDTO reqDTO){
        categoryUserService.edit(reqDTO);
        return super.success();
    }

    @PutMapping("/sort")
    @Operation(summary = "分类排序")
    public ApiRest<Void> sort(@RequestBody List<Long> ids){
        categoryUserService.sort(ids);
        return super.success();
    }

}
