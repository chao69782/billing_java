package com.fq.modules.sys.log.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fq.modules.sys.log.dto.QuerySysLogDTO;
import com.fq.modules.sys.log.entity.SysLog;
import com.fq.modules.sys.log.mapper.SysLogMapper;
import com.fq.modules.sys.log.service.SysLogService;
import com.fq.modules.sys.log.vo.SysLogVO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @Author 超chao
 * @Description 操作日志业务实现类
 * @Date 2024/12/2/周一 17:02
 * @Version 1.0
 */
@Log4j2
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements SysLogService {
    @Autowired
    private SysLogMapper baseMapper;
    @Override
    public void saveLog(SysLog sysLog) {
        sysLog.setId(IdWorker.getId());
        this.save(sysLog);
    }

    @Override
    public List<SysLogVO> queryLogListByParams(String params) {
        return baseMapper.queryLogListByParams(params);
    }

    @Override
    public IPage<SysLogVO> paging(QuerySysLogDTO reqDTO) {
        return baseMapper.paging(reqDTO.toPage(),reqDTO);
    }
}
