package com.fq.modules.sys.role.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fq.modules.sys.role.entity.SysRole;
import com.fq.modules.sys.role.entity.SysUserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author 超chao
 * @Description 系统用户角色mapper
 * @Date 2024/10/14/周一 11:31
 * @Version 1.0
 */
@Mapper
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {
    /**
     * 查找用户的角色列表
     * @param userId
     * @return
     */
    SysRole userRole(@Param("userId") Long userId);
    /**
     * 查找用户的权限标签
     * @param userId
     * @return
     */
    List<String> findUserPermission(@Param("userId") Long userId);
    /**
     * 查找所有权限标识
     * @return
     */
    List<String> findAllPermission();
}
