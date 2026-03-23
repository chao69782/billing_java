package com.fq.modules.ai.tools;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.fq.modules.category.entity.Category;
import com.fq.modules.category.service.ICategoryService;
import com.fq.modules.record.entity.Record;
import com.fq.modules.record.enums.RecordType;
import com.fq.modules.record.mapper.RecordMapper;
import com.fq.modules.record.service.IRecordService;
import com.fq.modules.record.vo.MonthIncomeAndExpenseVO;
import com.fq.modules.record.vo.RankingListVO;
import com.fq.utils.TimeUtil;
import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import dev.langchain4j.agent.tool.ToolMemoryId;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
@Slf4j
public class AssistantTools {

    @Autowired
    private IRecordService recordService;

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private RecordMapper recordMapper;


    /**
     * 获取所有分类的格式化字符串，用于 System Prompt
     */
    public String getCategoriesForPrompt(Long userId) {

        List<Category> categories = categoryService.listByUserId(userId);

        StringBuilder sb = new StringBuilder();
        categories.forEach(c -> sb.append(String.format("- %s (类型: %s 图标: %s)\n",
                c.getCategoryName(),
                c.getCategoryType() == 1 ? "支出" : "收入",
                c.getCategoryIcon())));
        return sb.toString();
    }

    @Tool("""
            保存记账记录。
            注意：
            1. 必须提供描述和金额。
            2. 必须明确是'收入'还是'支出'。
            3. 根据系统提示的【分类列表】，选择最匹配的【分类名称】。
            4. 必须简洁回答。
            """)
    public String saveRecord(
            @P("金额") Double amount,
            @P("分类名称（例如'餐饮'、'工资'）") String categoryName,
            @P("备注/描述") String remark,
            @P("时间，格式'yyyy-MM-dd'。如未提供用当前时间") String recordTime,
            @P("类型：1支出，2收入") Integer recordType,
            @P("图标：icon-licai") String categoryIcon,
            @ToolMemoryId Long userId
    ) {
        try {
            if (StrUtil.isBlank(recordTime)) {
                recordTime = TimeUtil.formatCurrentTime();
            }

            Record record = new Record();
            record.setId(IdWorker.getId());
            record.setUserId(userId);
            record.setAmount(amount);
            record.setCategoryName(categoryName);
            record.setCategoryIcon(categoryIcon);
            record.setRemark(remark);
            record.setRecordTime(recordTime);
            record.setRecordType(recordType);
            recordService.save(record);
            
            return "记账成功，已归类为：" + categoryName;
        } catch (Exception e) {
            log.error("保存记录失败", e);
            return "记账失败: " + e.getMessage();
        }
    }

    @Tool("分析月度收支情况。包括总收入、总支出，以及各分类的支出/收入排行，如果没有账单数据则直接告知用户没有相关月份的记录。")
    public String analyzeMonthlyExpenses(
            @P("月份，格式'yyyy-MM'。如未提供用当前月") String month,
            @ToolMemoryId Long userId
    ) {
        try {
            if (month == null || month.isEmpty()) {
                month = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM"));
            }

            // 1. 查询总收支
            MonthIncomeAndExpenseVO totalVo = recordMapper.getMonthIncomeAndExpense(month, userId);
            if (totalVo == null) {
                return month + " 没有找到账单数据。";
            }
            
            StringBuilder sb = new StringBuilder();
            sb.append(String.format("%s 账单分析:\n", month));
            sb.append(String.format("- 总收入: %.2f\n", totalVo.getIncome() != null ? totalVo.getIncome() : 0.0));
            sb.append(String.format("- 总支出: %.2f\n", totalVo.getExpense() != null ? totalVo.getExpense() : 0.0));

            // 2. 查询支出排行 (recordType=2)
            List<RankingListVO> expenseRanking = recordMapper.getRankingList(userId, RecordType.OUTCOME.getCode(), month);
            if (expenseRanking != null && !expenseRanking.isEmpty()) {
                sb.append("\n支出分类排行:\n");
                for (RankingListVO vo : expenseRanking) {
                    sb.append(String.format("  - %s: %.2f (%.1f%%)\n", vo.getCategoryName(), vo.getAmount(), vo.getPercent()));
                }
            }

            // 3. 查询收入排行 (recordType=1)
            List<RankingListVO> incomeRanking = recordMapper.getRankingList(userId, RecordType.INCOME.getCode(), month);
            if (incomeRanking != null && !incomeRanking.isEmpty()) {
                sb.append("\n收入分类排行:\n");
                for (RankingListVO vo : incomeRanking) {
                    sb.append(String.format("  - %s: %.2f (%.1f%%)\n", vo.getCategoryName(), vo.getAmount(), vo.getPercent()));
                }
            }

            return sb.toString();
        } catch (Exception e) {
            log.error("分析账单失败", e);
            return "分析失败: " + e.getMessage();
        }
    }
}
