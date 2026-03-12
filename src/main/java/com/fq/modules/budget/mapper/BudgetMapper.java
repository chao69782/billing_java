package com.fq.modules.budget.mapper;

import com.fq.modules.budget.entity.Budget;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fq.modules.budget.vo.BudgetVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 月预算 Mapper 接口
 * </p>
 *
 * @author 超chao
 * @since 2026-02-26
 */
@Mapper
public interface BudgetMapper extends BaseMapper<Budget> {

    /**
     * 获取预算
     * @param monthStr
     * @return
     */
    BudgetVO getBudget(@Param("monthStr") String monthStr, @Param("userId") Long userId);

}
