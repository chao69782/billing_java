package com.fq.modules.sys.role.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fq.api.api.ApiError;
import com.fq.api.exception.ServiceException;
import com.fq.modules.sys.role.entity.SysRoleMenu;
import com.fq.modules.sys.role.enums.RoleType;
import com.fq.modules.sys.role.mapper.SysRoleMenuMapper;
import com.fq.modules.sys.role.service.SysRoleMenuService;
import com.fq.modules.sys.role.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author 超chao
 * @Description 角色菜单授权业务实现类
 * @Date 2024/11/22/周五 16:15
 * @Version 1.0
 */
@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements SysRoleMenuService {

    @Autowired
    private SysRoleService roleService;

    @Autowired
    private SysRoleMenuMapper baseMapper;


    @Override
    public List<Long> findRoleMenus(String roleId) {
        //查询条件
        QueryWrapper<SysRoleMenu> wrapper = new QueryWrapper<>();
        if (roleId.equals(RoleType.ROLE_SUPER_ADMIN.getType())){
            wrapper.lambda();
        }else {
            wrapper.lambda().eq(SysRoleMenu::getRoleId, roleId);
        }
        List<SysRoleMenu> list = this.list(wrapper);

        List<Long> ids = new ArrayList<>();
        if(!CollectionUtils.isEmpty(list)){
            for (SysRoleMenu item: list){
                ids.add(item.getMenuId());
            }
        }
        return ids;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveRoleIds(String roleId, List<Long> ids) {
        roleService.checkIsSuperAdmin(roleId);

        if (roleId.equals(RoleType.ROLE_USER.getType())){
            throw new ServiceException(ApiError.ERROR_10030001);
        }

        // 先清理
        QueryWrapper<SysRoleMenu> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(SysRoleMenu::getRoleId, roleId);
        this.remove(wrapper);

        // 再保存
        if(!CollectionUtils.isEmpty(ids)){
            List<SysRoleMenu> list = new ArrayList<>();
            for (Long id: ids){
                SysRoleMenu item = new SysRoleMenu();
                item.setMenuId(id);
                item.setRoleId(roleId);
                list.add(item);
            }
            this.saveBatch(list);
        }
        // Spring Security 模式下，清除缓存的操作由 Redis 过期时间自动处理
        // 不再需要手动清除 Shiro 缓存


    }

    @Override
    public List<String> findRolePerms(String roleId) {
        return baseMapper.findRolePerms(roleId);
    }

    @Override
    public List<String> findRoleIdsByMenuId(Long menuId) {

        return baseMapper.findRoleIdsByMenuId(menuId);
    }

    @Override
    public void deleteByMenuId(Long menuId) {
        baseMapper.deleteByMenuId(menuId);
    }
}
