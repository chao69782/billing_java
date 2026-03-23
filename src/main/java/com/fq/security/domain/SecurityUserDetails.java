package com.fq.security.domain;

import com.fq.modules.sys.user.dto.SysUserLoginResDTO;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Spring Security 用户详情实现类
 */
@Getter
public class SecurityUserDetails implements UserDetails {

    private final Long userId;
    private final String userName;
    private final SysUserLoginResDTO userDTO;
    private final Collection<? extends GrantedAuthority> authorities;

    public SecurityUserDetails(SysUserLoginResDTO userDTO) {
        this.userId = userDTO.getId();
        this.userName = userDTO.getUserName();
        this.userDTO = userDTO;
        this.authorities = buildAuthorities(userDTO);
    }

    /**
     * 构建权限列表
     */
    private Collection<? extends GrantedAuthority> buildAuthorities(SysUserLoginResDTO userDTO) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        
        // 添加角色权限
        if (userDTO.getRole() != null) {
            authorities.add(new SimpleGrantedAuthority("ROLE:" + userDTO.getRole()));
        }
        
        // 添加具体权限
        if (userDTO.getPermissions() != null) {
            List<GrantedAuthority> permissionAuthorities = userDTO.getPermissions().stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
            authorities.addAll(permissionAuthorities);
        }
        
        return authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return null; // JWT 模式下不需要密码
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
