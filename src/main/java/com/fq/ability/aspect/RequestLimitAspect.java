package com.fq.ability.aspect;

import cn.hutool.core.util.StrUtil;
import com.fq.ability.anno.RequestLimit;
import com.fq.ability.redis.service.RedisService;
import com.fq.api.api.ApiError;
import com.fq.api.exception.ServiceException;
import com.fq.utils.StringUtils;
import com.fq.utils.user.UserUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @Author 超chao
 * @Description RequestLimitAspect
 * @Date 2025/11/11 周二 12:51
 * @Version 1.0
 */
@Aspect
@Component
public class RequestLimitAspect {
    @Autowired
    private RedisService redisService;
    private static final String REQUEST_URL = "reqLimit:";

    /**
     * 切入点
     */
    @Pointcut("@annotation(requestLimit)")
    public void pt(RequestLimit requestLimit) {
    }

    @Before(value = "pt(requestLimit)", argNames = "joinPoint,requestLimit")
    public void before(JoinPoint joinPoint, RequestLimit requestLimit){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();
        // 用户
        String userId = String.valueOf(UserUtils.getUserId());
        String url =  StringUtils.appendKey(userId,request.getServletPath());
        String key = StringUtils.appendKey(url,REQUEST_URL);
        boolean exists = redisService.exists(key);
        if (exists) {
            if (StrUtil.isBlank(requestLimit.message())){
                throw new ServiceException(ApiError.ERROR_10010013);
            }else {
                String message = requestLimit.message();
                Integer code = ApiError.ERROR_10010013.getCode();
                throw new ServiceException(code,message);
            }
        }
        redisService.set(key,"1",requestLimit.time());
    }
}
