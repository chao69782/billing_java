package com.fq.modules.budget.service;

import com.fq.modules.budget.dto.SetBudgetDTO;
import com.fq.modules.budget.entity.Budget;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fq.modules.budget.vo.BudgetVO;

/**
 * <p>
 * 月预算 服务类
 * </p>
 *
 * @author 超chao
 * @since 2026-02-26
 */
public interface IBudgetService extends IService<Budget> {
    /**
     * 设置预算
     * @param reqDTO
     */
    void set(SetBudgetDTO reqDTO);

    /**
     * 获取预算
     * @param monthStr
     * @return
     */
    Budget getByMonthAndUserId(String monthStr,Long userId);

    /**
     * 获取预算
     * @param monthStr
     * @return
     */
    BudgetVO getBudget(String monthStr);

}
