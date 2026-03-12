package com.fq.modules.budget.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fq.modules.budget.dto.SetBudgetDTO;
import com.fq.modules.budget.entity.Budget;
import com.fq.modules.budget.mapper.BudgetMapper;
import com.fq.modules.budget.service.IBudgetService;
import com.fq.modules.budget.vo.BudgetVO;
import com.fq.utils.AmountUtil;
import com.fq.utils.BeanMapper;
import com.fq.utils.user.UserUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 月预算 服务实现类
 * </p>
 *
 * @author 超chao
 * @since 2026-02-26
 */
@Service
public class BudgetServiceImpl extends ServiceImpl<BudgetMapper, Budget> implements IBudgetService {

    @Override
    public void set(SetBudgetDTO reqDTO) {
        Budget budget = this.getByMonthAndUserId(reqDTO.getMonthStr(), UserUtils.getUserId());
        if (budget != null) {
            budget.setBudget(reqDTO.getBudget());
            this.updateById(budget);
            return;
        }

        Budget entity = new Budget();
        BeanMapper.copy(reqDTO, entity);
        entity.setUserId(UserUtils.getUserId());
        this.save(entity);
    }

    @Override
    public Budget getByMonthAndUserId(String monthStr,Long userId) {
        QueryWrapper<Budget> wrapper = new QueryWrapper<>();
        wrapper.lambda()
                .eq(Budget::getUserId, userId)
                .eq(Budget::getMonthStr, monthStr);
        return this.getOne(wrapper);
    }

    @Override
    public BudgetVO getBudget(String monthStr) {
        BudgetVO budget = baseMapper.getBudget(monthStr, UserUtils.getUserId());
        if (budget == null){
            return null;
        }
        double remainBudget = AmountUtil.sub(budget.getTotalBudget(), budget.getMonthExpense());
        budget.setRemainBudget(remainBudget);
        budget.setBudgetPercent(AmountUtil.div(remainBudget, budget.getTotalBudget()) * 100);
        return budget;
    }
}
