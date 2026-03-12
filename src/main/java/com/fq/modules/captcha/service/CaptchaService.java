package com.fq.modules.captcha.service;

/**
 * @Author 超chao
 * @Description 验证码业务类
 * @Date 2025/11/11 周二 11:16
 * @Version 1.0
 */
public interface CaptchaService {
    /**
     * 保存验证码信息到Redis
     *
     * @param key
     * @param value
     */
    void saveCaptcha(String key, String value);

    /**
     * 校验验证码内容是否正确
     *
     * @param key
     * @param input
     * @return
     */
    void checkCaptcha(String key, String input);
}
