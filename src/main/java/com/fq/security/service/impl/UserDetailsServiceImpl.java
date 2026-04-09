package com.fq.security.service.impl;

import cn.hutool.json.JSONUtil;
import com.fq.ability.redis.constant.RedisConstant;
import com.fq.ability.redis.service.RedisService;
import com.fq.api.api.ApiError;
import com.fq.api.exception.ServiceException;
import com.fq.modules.sys.user.dto.SysUserLoginResDTO;
import com.fq.security.domain.SecurityUserDetails;
import com.fq.security.service.UserDetailsService;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 * 用户详情服务实现类
 */
@Log4j2
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final RedisService redisService;

    public UserDetailsServiceImpl(RedisService redisService) {
        this.redisService = redisService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws ServiceException {
        // 这个方法在 JWT 模式下不会被使用
        // 仅用于兼容 Spring Security 接口
        throw new UnsupportedOperationException("JWT 模式下不支持通过用户名加载用户");
    }

    /**
     * 根据用户 ID 加载用户详情
     * @param userId 用户 ID
     * @return 用户详情
     */
    @Override
    public UserDetails loadUserByUserId(Long userId) {
        String key = RedisConstant.USER_NAME_KEY + userId;
        String json = redisService.getString(key);
        
        if (json == null) {
            log.warn("用户缓存不存在，userId: {}", userId);
            throw new ServiceException(ApiError.ERROR_10020001);
        }
        
        try {
            SysUserLoginResDTO userDTO = JSONUtil.toBean(json, SysUserLoginResDTO.class);
            return new SecurityUserDetails(userDTO);
        } catch (Exception e) {
            log.error("解析用户信息失败，userId: {}", userId, e);
            throw new ServiceException(ApiError.ERROR_10020001);
        }
    }
}
