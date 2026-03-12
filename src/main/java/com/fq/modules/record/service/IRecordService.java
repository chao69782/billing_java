package com.fq.modules.record.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fq.modules.record.dto.EditRecordDTO;
import com.fq.modules.record.dto.RecordDTO;
import com.fq.modules.record.entity.Record;
import com.fq.modules.record.vo.BillDetailVO;
import com.fq.modules.record.vo.MonthIncomeAndExpenseVO;
import com.fq.modules.record.vo.RankingListVO;
import com.fq.modules.record.vo.RecordListVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 超chao
 * @since 2026-02-13
 */
public interface IRecordService extends IService<Record> {

    /**
     * 获取列表
     * @param time
     * @return
     */
    List<RecordListVO> list(String time);

    /**
     * 保存
     * @param reqDTO
     */
    void save(RecordDTO reqDTO);

    /**
     * 删除
     * @param id
     */
    void delete(Long id);

    /**
     * 获取时间列表
     * @return
     */
    List<String> getTimeList(String timeType);

    /**
     * 获取排行榜
     * @return
     */
    List<RankingListVO> getRankingList(Integer recordType,String time);

    /**
     * 根据月份查询总收入总支出和结余
     * @param month
     * @return
     */
    MonthIncomeAndExpenseVO getMonthIncomeAndExpense(String month);

    /**
     * 获取月账单详情
     * @param year
     * @return
     */
    List<BillDetailVO> getMonthBillList(String year);

    /**
     * 获取年账单详情
     * @return
     */
    List<BillDetailVO> getYearBillList();

    /**
     * 编辑
     * @param reqDTO
     */
    void edit(EditRecordDTO reqDTO);

}
