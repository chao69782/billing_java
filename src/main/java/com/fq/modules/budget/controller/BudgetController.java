package com.fq.modules.budget.controller;


import com.fq.ability.anno.RequestLimit;
import com.fq.api.api.ApiRest;
import com.fq.api.api.controller.BaseController;
import com.fq.modules.budget.dto.SetBudgetDTO;
import com.fq.modules.budget.service.IBudgetService;
import com.fq.modules.budget.vo.BudgetVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 月预算 前端控制器
 * </p>
 *
 * @author 超chao
 * @since 2026-02-26
 */
@Tag(name = "预算")
@RestController
@RequestMapping("/api/front/budget")
public class BudgetController extends BaseController {

    @Autowired
    private IBudgetService budgetService;

    @PostMapping("/set")
    @Operation(summary = "设置预算")
    @RequestLimit
    public ApiRest<Void> setBudget(@Validated @RequestBody SetBudgetDTO dto) {
        budgetService.set(dto);
        return super.success();
    }

    @GetMapping("/get")
    @Operation(summary = "获取预算")
    public ApiRest<BudgetVO> getBudget(@RequestParam(value = "monthStr") String monthStr) {
        BudgetVO budget = budgetService.getBudget(monthStr);
        return super.success(budget);
    }
}
