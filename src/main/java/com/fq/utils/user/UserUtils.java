package com.fq.utils.user;

import cn.hutool.core.util.StrUtil;
import com.fq.api.api.ApiError;
import com.fq.api.exception.ServiceException;
import com.fq.modules.sys.user.dto.SysUserLoginResDTO;
import com.fq.security.domain.SecurityUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @Author 超chao
 * @Description TODO
 * @Date 2024/10/14/周一 9:31
 * @Version 1.0
 */
public class UserUtils {
    /**
     * 获取用户信息
     *
     * @return
     */
    public static SysUserLoginResDTO getUser() {
        try {
            SecurityUserDetails userDetails = (SecurityUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return userDetails.getUserDTO();
        } catch (Exception e) {
            throw new ServiceException(ApiError.ERROR_10020001);
        }
    }
    /**
     * 获取真实姓名
     */
    public static String getRealName() {
        SysUserLoginResDTO user = getUser();
        return user != null ? StrUtil.blankToDefault(user.getRealName(), "") : "";
    }
    /**
     * 获取身份证号
     */
    public static String getIdCard() {
        SysUserLoginResDTO user = getUser();
        return user != null ? StrUtil.blankToDefault(user.getIdCard(), "") : "";
    }
    /**
     * 获取用户账号
     * @return
     */
    public static String getUserName() {
        SysUserLoginResDTO user = getUser();
        return user != null ? StrUtil.blankToDefault(user.getUserName(), "") : "";
    }

    /**
     * 获取当前登录用户的ID
     *
     * @return
     */
    public static Long getUserId() {
        SysUserLoginResDTO user = getUser();
        return user != null && user.getId() != null ? user.getId() : 0L;
    }
    /**
     * 获取手机号
     */
    public static String getMobile() {
        SysUserLoginResDTO user = getUser();
        return user != null ? StrUtil.blankToDefault(user.getMobile(), "") : "";
    }
    /**
     * 获取token
     */
    public static String getToken() {
        SysUserLoginResDTO user = getUser();
        return user != null ? StrUtil.blankToDefault(user.getToken(), "") : "";
    }
}
