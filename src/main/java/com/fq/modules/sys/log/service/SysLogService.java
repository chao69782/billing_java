package com.fq.modules.sys.log.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fq.modules.sys.log.dto.QuerySysLogDTO;
import com.fq.modules.sys.log.entity.SysLog;
import com.fq.modules.sys.log.vo.SysLogVO;

import java.util.List;

/**
 * @Author 超chao
 * @Description 操作日志业务接口
 * @Date 2024/12/2/周一 17:02
 * @Version 1.0
 */
public interface SysLogService extends IService<SysLog> {
    /**
     * 保存日志
     * @param sysLog 日志信息
     */
    void saveLog(SysLog sysLog);

    /**
     * 根据操作参数查询日志列表
     * @param params 参数
     * @return 日志列表
     */
    List<SysLogVO> queryLogListByParams(String params);

    /**
     * 日志分页列表
     * @param reqDTO
     * @return
     */
    IPage<SysLogVO> paging(QuerySysLogDTO reqDTO);
}
