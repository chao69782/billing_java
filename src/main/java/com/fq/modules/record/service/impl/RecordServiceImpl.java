package com.fq.modules.record.service.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fq.api.api.ApiError;
import com.fq.api.exception.ServiceException;
import com.fq.modules.record.dto.EditRecordDTO;
import com.fq.modules.record.dto.RecordDTO;
import com.fq.modules.record.entity.Record;
import com.fq.modules.record.enums.TimeType;
import com.fq.modules.record.mapper.RecordMapper;
import com.fq.modules.record.service.IRecordService;
import com.fq.modules.record.vo.BillDetailVO;
import com.fq.modules.record.vo.MonthIncomeAndExpenseVO;
import com.fq.modules.record.vo.RankingListVO;
import com.fq.modules.record.vo.RecordListVO;
import com.fq.utils.BeanMapper;
import com.fq.utils.user.UserUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 超chao
 * @since 2026-02-13
 */
@Service
public class RecordServiceImpl extends ServiceImpl<RecordMapper, Record> implements IRecordService {

    @Override
    public List<RecordListVO> list(String time) {
        return baseMapper.list(time, UserUtils.getUserId());
    }

    @Override
    public void save(RecordDTO reqDTO) {
        Record record = new Record();
        BeanMapper.copy(reqDTO, record);
        record.setId(IdWorker.getId());
        record.setUserId(UserUtils.getUserId());
        this.save(record);
    }

    @Override
    public void delete(Long id) {
        baseMapper.delete(id, UserUtils.getUserId());
    }

    @Override
    public List<String> getTimeList(String timeType) {
        Long userId = UserUtils.getUserId();
        if (timeType.equals(TimeType.MONTH.getValue())){
            return baseMapper.getMonthList(userId);
        } else if (timeType.equals(TimeType.YEAR.getValue())){
            return baseMapper.getYearList(userId);
        }

        throw new ServiceException(ApiError.ERROR_10010007);
    }

    @Override
    public List<RankingListVO> getRankingList(Integer recordType,String time) {
        return baseMapper.getRankingList(UserUtils.getUserId(),recordType,time);
    }

    @Override
    public MonthIncomeAndExpenseVO getMonthIncomeAndExpense(String month) {
        return baseMapper.getMonthIncomeAndExpense(month,UserUtils.getUserId());
    }

    @Override
    public List<BillDetailVO> getMonthBillList(String year) {
        return baseMapper.getMonthBillList(year,UserUtils.getUserId());
    }

    @Override
    public List<BillDetailVO> getYearBillList() {
        return baseMapper.getYearBillList(UserUtils.getUserId());
    }

    @Override
    public void edit(EditRecordDTO reqDTO) {
        Record record = new Record();
        BeanMapper.copy(reqDTO, record);
        this.updateById(record);
    }
}
