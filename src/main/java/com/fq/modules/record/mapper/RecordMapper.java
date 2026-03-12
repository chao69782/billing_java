package com.fq.modules.record.mapper;

import com.fq.modules.record.entity.Record;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fq.modules.record.vo.BillDetailVO;
import com.fq.modules.record.vo.MonthIncomeAndExpenseVO;
import com.fq.modules.record.vo.RankingListVO;
import com.fq.modules.record.vo.RecordListVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 超chao
 * @since 2026-02-13
 */
@Mapper
public interface RecordMapper extends BaseMapper<Record> {

    /**
     * 获取列表
     * @param time
     * @return
     */
    List<RecordListVO> list(@Param("time") String time,@Param("userId") Long userId);

    /**
     * 删除
     * @param id
     */
    void delete(@Param("id") Long id,@Param("userId") Long userId);

    /**
     * 获取月份列表
     * @param userId
     * @return
     */
    List<String> getMonthList(@Param("userId") Long userId);

    /**
     * 获取年份列表
     * @param userId
     * @return
     */
    List<String> getYearList(@Param("userId") Long userId);

    /**
     * 获取排行榜
     * @return
     */
    List<RankingListVO> getRankingList(@Param("userId") Long userId,@Param("recordType") Integer recordType,@Param("time") String time);

    /**
     * 根据月份查询总收入总支出和结余
     * @param month
     * @return
     */
    MonthIncomeAndExpenseVO getMonthIncomeAndExpense(@Param("month") String month,@Param("userId") Long userId);

    /**
     * 获取月账单详情
     * @param year
     * @return
     */
    List<BillDetailVO> getMonthBillList(@Param("year") String year,@Param("userId") Long userId);

    /**
     * 获取年账单详情
     * @return
     */
    List<BillDetailVO> getYearBillList(Long userId);

}
