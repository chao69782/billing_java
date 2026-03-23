package com.fq.modules.sys.role.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fq.api.api.dto.BaseChangeStateDTO;
import com.fq.modules.sys.role.dto.QueryRoleDTO;
import com.fq.modules.sys.role.dto.SysRoleDTO;
import com.fq.modules.sys.role.entity.SysRole;

/**
 * @Author 超chao
 * @Description 角色业务类
 * @Date 2024/10/14/ 11:11
 * @Version 1.0
 */
public interface SysRoleService extends IService<SysRole> {

    /**
     * 分页查询数据
     * @param reqDTO 查询参数
     * @return 分页列表
     */
    IPage<SysRoleDTO> paging(QueryRoleDTO reqDTO);

    /**
     * 保存或修改角色
     * @param reqDTO
     */
    void save(SysRoleDTO reqDTO);

    /**
     * 删除角色
     *
     * @param id 角色id
     */
    void deleteById(String id);

    /**
     * 查询角色是否有用户使用
     * @param roleId 角色ID
     * @return true 使用 false 未使用
     */
    boolean checkRoleUserUse(String roleId);

    /**
     * 禁用或启用角色
     *
     * @param reqDTO 请求参数
     */
    void updateRoleState(BaseChangeStateDTO<String> reqDTO);

    /**
     * 判断是否是超级管理员
     *
     * @param roleId 角色ID
     */
    void checkIsSuperAdmin(String roleId);

    /**
     * 判断是否是普通用户
     *
     * @param roleId 角色ID
     */
    void checkIsOrdinaryUser(String roleId);
}