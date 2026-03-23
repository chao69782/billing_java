package com.fq.modules.sys.log.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fq.modules.sys.log.dto.QuerySysLogDTO;
import com.fq.modules.sys.log.entity.SysLog;
import com.fq.modules.sys.log.vo.SysLogVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author 超chao
 * @Description 操作日志mapper
 * @Date 2024/12/2/周一 17:01
 * @Version 1.0
 */
@Mapper
public interface SysLogMapper extends BaseMapper<SysLog> {
    /**
     * 根据操作参数查询日志列表
     * @param params
     * @return
     */
    List<SysLogVO> queryLogListByParams(String params);

    /**
     * 日志分页列表
     * @param page
     * @param reqDTO
     * @return
     */
    IPage<SysLogVO> paging(Page<SysLogVO> page, @Param("query") QuerySysLogDTO reqDTO);
}
