package com.fq.modules.sys.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fq.modules.sys.user.dto.SysUserLoginDTO;
import com.fq.modules.sys.user.dto.SysUserLoginResDTO;
import com.fq.modules.sys.user.entity.SysUser;

/**
 * @Author 超chao
 * @Description 系统用户接口
 * @Date 2025/2/5/周三 9:18
 * @Version 1.0
 */
public interface SysUserService extends IService<SysUser> {
    /**
     * 用户登录
     * @param encryptData 请求参数
     * @param accountType 登录用户类型
     * @return 登录信息
     */
    SysUserLoginResDTO login(String encryptData, Integer accountType);

    /**
     * 用户登录
     * @param reqDTO 请求参数
     * @param accountType 登录用户类型
     * @return 登录信息
     */
    SysUserLoginResDTO login(SysUserLoginDTO reqDTO, Integer accountType);

    /**
     * 根据用户名查找用户
     * @param userName 用户名
     * @return 用户实体
     */
    SysUser getUserByUserName(String userName,Integer accountType);

    /**
     * 三方登录
     *
     * @param loginType 登录方式
     * @param openId openid
     * @param nickName 昵称
     * @param avatar 头像
     * @return 登录信息
     */
    SysUserLoginResDTO loginByThird(String loginType, String openId, String nickName, String avatar);

    /**
     * 校验密码登录
     * @param user 用户信息
     * @param password 密码
     * @param requirePassword 是否需要密码
     * @return 登录信息
     */
    SysUserLoginResDTO checkAndLogin(SysUser user, String password, Boolean requirePassword);

    /**
     * 保存会话记录
     * @param sysUser 用户信息
     * @param isResetToken 是否重新生成token
     * @return 登录信息
     */
    SysUserLoginResDTO setToken(SysUser sysUser, Boolean isResetToken);

    /**
     * 保存并登录
     * @param userId 用户id
     * @param userName 用户名
     * @param nickName 昵称
     * @param role 角色
     * @param mobile 手机号
     * @param avatar 头像
     * @param password 密码
     * @return 登录信息
     */
    SysUserLoginResDTO saveAndLogin(Long userId, String userName, String nickName, String role, String mobile, String avatar, String password);

    /**
     * 校验邀请码
     * @param inviteCode
     * @return
     */
    SysUser checkInviteCode(String inviteCode);
}
