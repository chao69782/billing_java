package com.fq.modules.sys.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fq.modules.sys.user.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Author 超chao
 * @Description 系统用户mapper
 * @Date 2025/2/5/周三 9:19
 * @Version 1.0
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {
    /**
     * 根据用户名查找管理用户
     * @param userName
     * @return
     */
    SysUser getAccountUser(@Param("userName") String userName, @Param("accountType") Integer accountType);


    /**
     * 根据邀请码查找用户
     * @param inviteCode
     * @return
     */
    SysUser getUserByInviteCode(@Param("inviteCode") String inviteCode);

}
