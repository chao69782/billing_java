package com.fq.modules.sys.role.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fq.ability.redis.constant.RedisConstant;
import com.fq.ability.redis.service.RedisService;
import com.fq.api.api.ApiError;
import com.fq.api.exception.ServiceException;
import com.fq.modules.sys.role.dto.SaveRolesDTO;
import com.fq.modules.sys.role.entity.SysRole;
import com.fq.modules.sys.role.entity.SysUserRole;
import com.fq.modules.sys.role.mapper.SysUserRoleMapper;
import com.fq.modules.sys.role.service.SysRoleService;
import com.fq.modules.sys.role.service.SysUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author 超chao
 * @Description 系统用户角色服务实现类
 * @Date 2024/10/14/周一 11:30
 * @Version 1.0
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private RedisService redisService;
    @Override
    public void saveUserRole(SaveRolesDTO dto) {
        this.checkRole(dto.getRoleId());

        // 删除全部角色
        QueryWrapper<SysUserRole> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(SysUserRole::getUserId, dto.getUserId());
        this.remove(wrapper);

        SysUserRole role = new SysUserRole();
        role.setRoleId(dto.getRoleId());
        role.setUserId(dto.getUserId());
        this.save(role);

        // 清除该用户的 Redis 缓存，确保权限变更立即生效
        String cacheKey = RedisConstant.USER_NAME_KEY + dto.getUserId();
        redisService.del(cacheKey);
    }

    @Override
    public List<String> findUserPermission(Long userId) {
        return baseMapper.findUserPermission(userId);
    }

    @Override
    public SysRole userRole(Long userId) {
        return baseMapper.userRole(userId);
    }
    /**
     * 校验角色
     * @param roleId
     */
    private void checkRole(String roleId){
        SysRole sysRole = sysRoleService.getById(roleId);
        if (sysRole == null){
            throw new ServiceException(ApiError.ERROR_10040002);
        }
    }
}
