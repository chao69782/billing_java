package com.fq.security.filter;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fq.ability.jwt.JwtUtils;
import com.fq.ability.redis.constant.RedisConstant;
import com.fq.api.api.ApiError;
import com.fq.api.api.ApiRest;
import com.fq.config.properties.SecurityProperties;
import com.fq.security.service.impl.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * JWT 认证过滤器
 * 替代原有的 JwtFilter
 */
@Log4j2
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final UserDetailsServiceImpl userDetailsService;
    private final SecurityProperties securityProperties;

    public JwtAuthenticationFilter(UserDetailsServiceImpl userDetailsService,
                                   SecurityProperties securityProperties) {
        this.userDetailsService = userDetailsService;
        this.securityProperties = securityProperties;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, 
                                   FilterChain filterChain) throws ServletException, IOException {
        
        // OPTIONS 请求直接放行
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            return;
        }

        // 获取请求路径（去掉 context path）
        String path = request.getRequestURI().substring(request.getContextPath().length());
        
        // 白名单路径直接放行（不校验 Token）
        if (isAnonPath(path)) {
            filterChain.doFilter(request, response);
            return;
        }

        // 获取 Authorization 头
        String authorizationHeader = request.getHeader(RedisConstant.AUTHORIZATION);
        if (StrUtil.isBlank(authorizationHeader) || !authorizationHeader.startsWith(RedisConstant.BEARER)) {
            writeError(response, new Exception(ApiError.ERROR_10020001.msg));
            return;
        }

        String token = authorizationHeader.substring(RedisConstant.BEARER_SPLIT);
        
        try {
            // 从 Token 中获取用户 ID
            Long userId = getUserIdFromToken(token);
            if (userId == null) {
                writeError(response, new Exception(ApiError.ERROR_10020001.msg));
                return;
            }

            // 校验 Token 有效性
            if (!JwtUtils.verify(token, userId)) {
                writeError(response, new Exception(ApiError.ERROR_10020001.msg));
                return;
            }

            // 加载用户详情
            UserDetails userDetails = userDetailsService.loadUserByUserId(userId);
            if (userDetails == null) {
                writeError(response, new Exception(ApiError.ERROR_10020001.msg));
                return;
            }

            // 设置认证信息到 SecurityContext
            UsernamePasswordAuthenticationToken authentication = 
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            writeError(response, e);
        }
    }

    // +++ 关键重写1：异步分发时，本过滤器也需要执行 +++
    @Override
    protected boolean shouldNotFilterAsyncDispatch() {
        return false; // 改为false，表示异步调度时也要过滤
    }

    // +++ 关键重写2：错误分发时，本过滤器也需要执行 +++
    @Override
    protected boolean shouldNotFilterErrorDispatch() {
        return false; // 改为false，保持上下文一致
    }

    /**
     * 从 Token 中获取用户 ID
     */
    private Long getUserIdFromToken(String token) {
        try {
            DecodedJWT jwt = com.auth0.jwt.JWT.decode(token);
            return jwt.getClaim("username").asLong();
        } catch (Exception e) {
            log.error("解析 Token 失败", e);
            return null;
        }
    }

    /**
     * 判断是否为白名单路径
     */
    private boolean isAnonPath(String path) {
        if (securityProperties.getAnon() == null || securityProperties.getAnon().isEmpty()) {
            return false;
        }
        
        for (String pattern : securityProperties.getAnon()) {
            if (pathMatches(pattern, path)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 路径匹配（支持通配符 **）
     */
    private boolean pathMatches(String pattern, String path) {
        // 精确匹配
        if (pattern.equals(path)) {
            return true;
        }
        
        // 处理 ** 通配符
        if (pattern.endsWith("/**")) {
            String prefix = pattern.substring(0, pattern.length() - 3);
            return path.startsWith(prefix);
        }
        
        return false;
    }

    /**
     * 写入错误响应
     */
    private void writeError(HttpServletResponse response, Exception e) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_OK);

        String message = e.getMessage();
        if (message == null) {
            message = "Unknown error";
        }

        ApiRest<Void> apiRest;
        if (ApiError.ERROR_10020001.msg.equals(message)) {
            apiRest = new ApiRest<Void>().setMsg(ApiError.ERROR_10020001.msg).setCode(ApiError.ERROR_10020001.getCode());
        } else if (ApiError.ERROR_10020024.msg.equals(message)) {
            apiRest = new ApiRest<Void>().setMsg(ApiError.ERROR_10020024.msg).setCode(ApiError.ERROR_10020024.getCode());
        } else {
            apiRest = new ApiRest<Void>().setMsg(message).setCode(1);
        }
        response.getWriter().print(JSON.toJSONString(apiRest));
    }
}
