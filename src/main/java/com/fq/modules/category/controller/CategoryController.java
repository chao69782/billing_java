package com.fq.modules.category.controller;


import com.fq.api.api.ApiRest;
import com.fq.api.api.controller.BaseController;
import com.fq.modules.category.entity.Category;
import com.fq.modules.category.service.ICategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/listByCategoryType")
    @Operation(summary = "获取分类列表")
    public ApiRest<List<Category>> listByCategoryType(@RequestParam(value = "categoryType") Integer categoryType){
        return super.success(baseService.listByCategoryType(categoryType));
    }

}
