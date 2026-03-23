package com.fq.modules.sys.role.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fq.modules.sys.role.entity.SysRoleMenu;

import java.util.List;

/**
 * @Author 超chao
 * @Description 角色菜单授权业务类
 * @Date 2024/11/22/周五 16:13
 * @Version 1.0
 */
public interface SysRoleMenuService extends IService<SysRoleMenu> {
    /**
     * 查找角色授权
     * @param roleId 角色ID
     * @return 授权的菜单ID集合
     */
    List<Long> findRoleMenus(String roleId);


    /**
     * 保存角色授权
     * @param roleId 角色ID
     */
    void saveRoleIds(String roleId, List<Long> ids);

    /**
     * 查找角色授权标识
     * @param roleId 角色ID
     * @return 授权标识集合
     */
    List<String> findRolePerms(String roleId);

    /**
     * 获取拥有该菜单权限的角色列表
     * @param menuId 菜单ID
     * @return 角色ID集合
     */
    List<String> findRoleIdsByMenuId(Long menuId);

    /**
     * 删除角色菜单权限
     *
     * @param menuId 菜单ID
     */
    void deleteByMenuId(Long menuId);
}
