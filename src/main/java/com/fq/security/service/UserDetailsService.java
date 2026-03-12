package com.fq.security.service;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * 用户详情服务接口
 */
public interface UserDetailsService extends org.springframework.security.core.userdetails.UserDetailsService {
    
    /**
     * 根据用户 ID 加载用户详情
     * @param userId 用户 ID
     * @return 用户详情
     */
    UserDetails loadUserByUserId(Long userId);
}
