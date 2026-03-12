package com.fq.modules.sys.user.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fq.ability.encrypt.EncryptService;
import com.fq.ability.redis.constant.RedisConstant;
import com.fq.ability.redis.service.RedisService;
import com.fq.ability.jwt.JwtUtils;
import com.fq.api.api.ApiError;
import com.fq.api.api.enums.Digit;
import com.fq.api.exception.ServiceException;
import com.fq.modules.captcha.service.CaptchaService;
import com.fq.modules.sys.role.dto.SaveRolesDTO;
import com.fq.modules.sys.role.entity.SysRole;
import com.fq.modules.sys.role.enums.RoleState;
import com.fq.modules.sys.role.enums.RoleType;
import com.fq.modules.sys.role.service.SysUserRoleService;
import com.fq.modules.sys.user.dto.SysUserLoginDTO;
import com.fq.modules.sys.user.dto.SysUserLoginResDTO;
import com.fq.modules.sys.user.entity.SysUser;
import com.fq.modules.sys.user.entity.SysUserBind;
import com.fq.modules.sys.user.enums.UserState;
import com.fq.modules.sys.user.mapper.SysUserMapper;
import com.fq.modules.sys.user.service.SysUserBindService;
import com.fq.modules.sys.user.service.SysUserService;
import com.fq.utils.passwd.PassHandler;
import com.fq.utils.passwd.PassInfo;
import com.fq.utils.user.UserUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @Author 超chao
 * @Description 用户业务实现类
 * @Date 2025/2/5/周三 9:19
 * @Version 1.0
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
    @Autowired
    private SysUserMapper baseMapper;
    @Autowired
    private SysUserRoleService userRoleService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private EncryptService encryptService;
    @Autowired
    private CaptchaService captchaService;
    @Autowired
    private SysUserBindService sysUserBindService;
    @Override
    public SysUserLoginResDTO login(String encryptData, Integer accountType) {
        JSONObject jsonObject = encryptService.decryptToObject(encryptData);
        String userName = jsonObject.getString("username");
        String password = jsonObject.getString("password");
        String captchaKey = jsonObject.getString("captchaKey");
        String captchaValue = jsonObject.getString("captchaValue");
        if (StrUtil.isBlank(captchaKey) || StrUtil.isBlank(captchaValue)){
            throw new ServiceException(ApiError.ERROR_10020025);
        }

        if (StrUtil.isBlank(userName) || StrUtil.isBlank(password)){
            throw new ServiceException(ApiError.ERROR_10020020);
        }

        // 校验图形验证码
        captchaService.checkCaptcha(captchaKey, captchaValue);

        SysUser user = this.getUserByUserName(userName, accountType);
        return this.checkAndLogin(user, password, true);
    }

    @Override
    public SysUserLoginResDTO login(SysUserLoginDTO reqDTO, Integer accountType) {
        // 校验图形验证码
        captchaService.checkCaptcha(reqDTO.getCaptchaKey(), reqDTO.getCaptcha());

        SysUser user = this.getUserByUserName(reqDTO.getUserName(), accountType);
        return this.checkAndLogin(user, reqDTO.getPassword(), true);
    }

    @Override
    public SysUser getUserByUserName(String userName,Integer accountType) {
        return baseMapper.getAccountUser(userName,accountType);
    }

    @Override
    public SysUserLoginResDTO loginByThird(String loginType, String openId, String nickName, String avatar) {
        Long userId = sysUserBindService.findBind(loginType, openId);
        // 不存在，创建新的用户
        if (userId == null) {
            // 随机产生数据
            SysUserLoginResDTO dto = this.saveAndLogin(
                    null,
                    null,
                    nickName,
                    null,
                    null,
                    avatar,
                    null);
            // 绑定
            SysUserBind bind = new SysUserBind();
            bind.setLoginType(loginType);
            bind.setUserId(dto.getId());
            bind.setOpenId(openId);
            sysUserBindService.save(bind);
            return dto;
        }
        // 校验用户状态&密码并获取会话
        SysUser user = this.getById(userId);
        return this.checkAndLogin(user, null, false);
    }

    @Override
    public SysUserLoginResDTO checkAndLogin(SysUser user, String password, Boolean requirePassword) {
        if (user == null) {
            // 用户不存在
            throw new ServiceException(ApiError.ERROR_10020004);
        }

        //查看是否被禁用
        if (!user.getState().equals(UserState.NORMAL.getState())) {
            // 账号被禁用
            throw new ServiceException(ApiError.ERROR_10020010);
        }

        // 是否需要密码（PC 或者 mobile）
        if (requirePassword) {
            if (StrUtil.isBlank(user.getPassword()) || StrUtil.isBlank(password) || !PassHandler.checkPass(password, user.getPassword())) {
                throw new ServiceException(ApiError.ERROR_10020003);
            }

        }

        return this.setToken(user,true);
    }

    @Override
    public SysUserLoginResDTO setToken(SysUser sysUser, Boolean isResetToken) {

        String key = RedisConstant.USER_NAME_KEY + sysUser.getId().toString();

        SysUserLoginResDTO respDTO = new SysUserLoginResDTO();
        BeanUtils.copyProperties(sysUser,respDTO);
        respDTO.setIsHavePassword(!StrUtil.isBlank(sysUser.getPassword()));
        if (isResetToken){
            // 根据用户生成Token
            String token = JwtUtils.sign(sysUser.getId());
            respDTO.setToken(token);
        } else {
            // 从缓存获取token
            respDTO.setToken(UserUtils.getToken());
        }
        // 角色
        SysRole sysRole = userRoleService.userRole(respDTO.getId());
        respDTO.setRole(sysRole.getId());
        respDTO.setRoleType(sysRole.getRoleType());

        // 检查角色状态
        if (!sysRole.getRoleState().equals(RoleState.NORMAL.getValue())){
            throw new ServiceException(ApiError.ERROR_10040003);
        }
        // 超级管理员
        if (sysRole.getId().equals(RoleType.ROLE_SUPER_ADMIN.getType())){
            respDTO.setPermissions(List.of("*"));

        } else {
            //权限表，用于前端控制按钮
            List<String> permissions = userRoleService.findUserPermission(sysUser.getId());
            respDTO.setPermissions(permissions);
        }
        // 保存到 redis
        redisService.set(key, JSONUtil.toJsonStr(respDTO), (long) JwtUtils.EXPIRE_TIME);
        return respDTO;
    }

    @Override
    public SysUserLoginResDTO saveAndLogin(Long userId, String userName, String nickName, String role, String mobile, String avatar, String password) {
        // 保存用户
        SysUser user = new SysUser();
        String prefix = RandomStringUtils.randomAlphabetic(Digit.SIX.getDigit());
        // 指定用户ID的
        user.setId(Objects.requireNonNullElseGet(userId, IdWorker::getId));
        user.setNickName(StrUtil.isBlank(nickName) ? "用户" + prefix : nickName);
        user.setUserName(StrUtil.isBlank(userName) ? RandomStringUtils.randomAlphabetic(Digit.ELEVEN.getDigit()) : userName);
        user.setInviteCode(prefix);
        user.setMobile(mobile);
        user.setAvatar(avatar);
        user.setState(UserState.NORMAL.getState());

        if (!StrUtil.isBlank(password)){
            PassInfo passInfo = PassHandler.buildPassword(password);
            user.setPassword(passInfo.getPassword());
        }
        SaveRolesDTO saveRoles = new SaveRolesDTO();

        // 保存角色
        if (!StrUtil.isBlank(role)) {
            saveRoles.setRoleId(role);
        } else {
            // 默认普通用户
            saveRoles.setRoleId(RoleType.ROLE_USER.getType());
        }

        saveRoles.setUserId(user.getId());

        // 保存用户
        this.save(user);

        // 保存角色
        userRoleService.saveUserRole(saveRoles);

        return this.setToken(user,true);
    }

    @Override
    public SysUser checkInviteCode(String inviteCode) {
        SysUser user = baseMapper.getUserByInviteCode(inviteCode);
        if (user == null) {
            throw new ServiceException(ApiError.ERROR_10070001);
        }
        return user;
    }

}
