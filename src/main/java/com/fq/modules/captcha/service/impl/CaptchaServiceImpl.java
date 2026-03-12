package com.fq.modules.captcha.service.impl;

import cn.hutool.core.util.StrUtil;
import com.fq.ability.redis.constant.RedisConstant;
import com.fq.ability.redis.service.RedisService;
import com.fq.api.api.ApiError;
import com.fq.api.exception.ServiceException;
import com.fq.modules.captcha.service.CaptchaService;
import com.fq.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author 超chao
 * @Description 验证码业务类
 * @Date 2025/11/11 周二 11:17
 * @Version 1.0
 */
@Service
public class CaptchaServiceImpl implements CaptchaService {
    @Autowired
    private RedisService redisService;
    @Override
    public void saveCaptcha(String key, String value) {
        redisService.set(StringUtils.appendKey(RedisConstant.CAPTCHA_PREFIX,key), value, RedisConstant.CAPTCHA_EXPIRE);

    }

    @Override
    public
    void checkCaptcha(String key, String input) {
        String value = redisService.getString(StringUtils.appendKey(RedisConstant.CAPTCHA_PREFIX,key));
        boolean res = StrUtil.isNotBlank(value) && value.equalsIgnoreCase(input);
        if (!res) {
            throw new ServiceException(ApiError.ERROR_10020026);
        }
    }
}
