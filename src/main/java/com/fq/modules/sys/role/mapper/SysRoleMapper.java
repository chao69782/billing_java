package com.fq.modules.sys.role.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fq.modules.sys.role.dto.QueryRoleDTO;
import com.fq.modules.sys.role.dto.SysRoleDTO;
import com.fq.modules.sys.role.entity.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Author 超chao
 * @Description 角色业务类
 * @Date 2024/10/14/ 11:11
 * @Version 1.0
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {
    /**
     * 分页查询数据
     * @param reqDTO
     * @return
     */
    IPage<SysRoleDTO> paging(Page<SysRoleDTO> page, @Param("query") QueryRoleDTO reqDTO);
    /**
     * 查询角色是否有用户使用
     * @param roleId
     * @return
     */
    boolean checkRoleUserUse(String roleId);

    /**
     * 删除
     */
    void delete(String id);

}