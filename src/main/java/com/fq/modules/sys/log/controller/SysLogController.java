package com.fq.modules.sys.log.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fq.api.api.ApiRest;
import com.fq.api.api.controller.BaseController;
import com.fq.modules.sys.log.dto.QuerySysLogDTO;
import com.fq.modules.sys.log.service.SysLogService;
import com.fq.modules.sys.log.vo.SysLogVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @Author 超chao
 * @Description 系统操作日志
 * @Date 2024/12/2/周一 17:05
 * @Version 1.0
 */
@Tag(name = "系统操作日志")
@RestController
@RequestMapping(value = "/api/admin/sys/log")
public class SysLogController extends BaseController {
    @Autowired
    private SysLogService baseService;

    @PreAuthorize("@ss.hasPermi('sys:log:list')")
    @Operation(summary = "分页--查询日志列表")
    @GetMapping(value = "/paging")
    public ApiRest<IPage<SysLogVO>> paging(@ModelAttribute QuerySysLogDTO reqDTO) {
        return super.success(baseService.paging(reqDTO));
    }
}
