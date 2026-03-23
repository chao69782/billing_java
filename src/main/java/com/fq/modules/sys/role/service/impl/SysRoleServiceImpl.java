package com.fq.modules.sys.role.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fq.api.api.ApiError;
import com.fq.api.api.dto.BaseChangeStateDTO;
import com.fq.api.exception.ServiceException;
import com.fq.modules.sys.role.dto.QueryRoleDTO;
import com.fq.modules.sys.role.dto.SysRoleDTO;
import com.fq.modules.sys.role.entity.SysRole;
import com.fq.modules.sys.role.enums.RoleState;
import com.fq.modules.sys.role.enums.RoleType;
import com.fq.modules.sys.role.mapper.SysRoleMapper;
import com.fq.modules.sys.role.service.SysRoleService;
import com.fq.utils.BeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @Author 超chao
 * @Description 角色业务类
 * @Date 2024/10/14/ 11:11
 * @Version 1.0
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {
    /**
     * 系统默认角色
     */
    private static final Set<String> SYSTEM_ROLE = new HashSet<>(Arrays.asList("super_admin","ordinary_user"));
    @Autowired
    private SysRoleMapper baseMapper;

    @Override
    public IPage<SysRoleDTO> paging(QueryRoleDTO reqDTO) {
        return baseMapper.paging(reqDTO.toPage(), reqDTO);
     }

    @Override
    public void save(SysRoleDTO reqDTO) {
        SysRole entity = new SysRole();
        BeanMapper.copy(reqDTO, entity);
        this.saveOrUpdate(entity);
    }
    @Override
    public void deleteById(String id) {

        boolean check = this.checkRoleUserUse(id);

        if (SYSTEM_ROLE.contains(id)) {
            throw new ServiceException(ApiError.ERROR_10040006);
        }
        if (check){
            throw new ServiceException(ApiError.ERROR_10040007);
        }
        baseMapper.delete(id);
    }

    @Override
    public boolean checkRoleUserUse(String roleId) {
        return baseMapper.checkRoleUserUse(roleId);
    }

    @Override
    public void updateRoleState(BaseChangeStateDTO<String> reqDTO) {
        String roleId = reqDTO.getId();
        this.checkIsSuperAdmin(roleId);
        this.checkIsOrdinaryUser(roleId);
        UpdateWrapper<SysRole> wrapper = new UpdateWrapper<>();
        wrapper.set("role_state",reqDTO.getState()).eq("id",roleId);
        //禁用
        if (reqDTO.getState().equals(RoleState.DISABLE.getValue()) || reqDTO.getState().equals(RoleState.NORMAL.getValue())){
            this.update(wrapper);
            return;
        }
        throw new ServiceException(ApiError.ERROR_10010007);
    }

    @Override
    public void checkIsSuperAdmin(String roleId) {
        if(roleId.equals(RoleType.ROLE_SUPER_ADMIN.getType())){
            throw new ServiceException(ApiError.ERROR_10040004);
        }
    }
    @Override
    public void checkIsOrdinaryUser(String roleId) {
        if(roleId.equals(RoleType.ROLE_USER.getType())){
            throw new ServiceException(ApiError.ERROR_10040005);
        }
    }
}