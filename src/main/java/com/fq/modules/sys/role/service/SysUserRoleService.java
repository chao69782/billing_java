package com.fq.modules.sys.role.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fq.modules.sys.role.dto.SaveRolesDTO;
import com.fq.modules.sys.role.entity.SysRole;
import com.fq.modules.sys.role.entity.SysUserRole;

import java.util.List;

/**
 * @Author 超chao
 * @Description 用户角色业务类
 * @Date 2024/10/14/周一 11:30
 * @Version 1.0
 */
public interface SysUserRoleService extends IService<SysUserRole> {
    /**
     * 保存全部角色
     * @param dto 保存角色信息
     */
    void saveUserRole(SaveRolesDTO dto);

    /**
     * 查找用户的权限标签
     * @param userId 用户id
     * @return 角色标签列表
     */
    List<String> findUserPermission(Long userId);

    /**
     * 查找用户角色列表
     * @param userId 用户id
     * @return 角色
     */
    SysRole userRole(Long userId);



}
