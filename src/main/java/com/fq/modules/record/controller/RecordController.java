package com.fq.modules.record.controller;


import com.fq.ability.anno.RequestLimit;
import com.fq.api.api.ApiRest;
import com.fq.api.api.controller.BaseController;
import com.fq.modules.record.dto.EditRecordDTO;
import com.fq.modules.record.dto.RecordDTO;
import com.fq.modules.record.service.IRecordService;
import com.fq.modules.record.vo.BillDetailVO;
import com.fq.modules.record.vo.MonthIncomeAndExpenseVO;
import com.fq.modules.record.vo.RankingListVO;
import com.fq.modules.record.vo.RecordListVO;
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
@Tag(name = "账单记录")
@RestController
@RequestMapping("/api/front/record")
public class RecordController extends BaseController {

    @Autowired
    private IRecordService baseService;

    @GetMapping("/list")
    @Operation(summary = "获取账单记录")
    public ApiRest<List<RecordListVO>> list(@RequestParam(value = "time") String time) {
        return success(baseService.list(time));
    }

    @PostMapping("/add")
    @Operation(summary = "添加账单")
    @RequestLimit
    public ApiRest<Void> add(@Validated @RequestBody RecordDTO reqDTO) {
        baseService.save(reqDTO);
        return success();
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除账单")
    public ApiRest<Void> delete(@RequestParam(value = "id") Long id) {
        baseService.delete(id);
        return success();
    }

    @GetMapping("/getTimeList")
    @Operation(summary = "获取账单时间列表")
    public ApiRest<List<String>> getTimeList(@RequestParam(value = "timeType") String timeType) {
        return success(baseService.getTimeList(timeType));
    }

    @GetMapping("/getRankingList")
    @Operation(summary = "获取账单排行榜")
    public ApiRest<List<RankingListVO>> getRankingList(@RequestParam(value = "recordType") Integer recordType,
                                                       @RequestParam(value = "time") String time) {
        return success(baseService.getRankingList(recordType,time));
    }

    @GetMapping("/getMonthIncomeAndExpense")
    @Operation(summary = "获取账单收入支出")
    public ApiRest<MonthIncomeAndExpenseVO> getMonthIncomeAndExpense(@RequestParam(value = "month") String month) {
        return success(baseService.getMonthIncomeAndExpense(month));
    }

    @GetMapping("/getMonthBillList")
    @Operation(summary = "获取月账单详情")
    public ApiRest<List<BillDetailVO>> getMonthBillList(@RequestParam(value = "year") String year) {
        return success(baseService.getMonthBillList(year));
    }

    @GetMapping("/getYearBillList")
    @Operation(summary = "获取年账单详情")
    public ApiRest<List<BillDetailVO>> getYearBillList() {
        return success(baseService.getYearBillList());
    }

    @PutMapping("/edit")
    @Operation(summary = "编辑账单")
    public ApiRest<Void> edit(@Validated @RequestBody EditRecordDTO reqDTO) {
        baseService.edit(reqDTO);
        return success();
    }

}
