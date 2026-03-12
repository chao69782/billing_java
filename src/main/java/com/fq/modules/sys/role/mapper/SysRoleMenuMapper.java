package com.fq.modules.sys.role.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fq.modules.sys.role.entity.SysRoleMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author 超chao
 * @Description 角色菜单授权Mapper
 * @Date 2024/11/22/周五 16:16
 * @Version 1.0
 */
@Mapper
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {
    /**
     * 查找角色授权标识
     * @param roleId
     * @return
     */
    List<String> findRolePerms(String roleId);

    /**
     * 获取拥有该菜单权限的角色列表
     * @param menuId
     * @return
     */
    List<String> findRoleIdsByMenuId(Long menuId);

    /**
     * 删除角色菜单权限
     * @param menuId
     * @return
     */
    boolean deleteByMenuId(Long menuId);
}
