package com.fq.ability.redis.constant;

/**
 * @Author 超chao
 * @Description redis常量
 * @Date 2025/1/25/周六 8:46
 * @Version 1.0
 */
public class RedisConstant {
    /**
     * 用户名前缀
     */
    public static final String USER_NAME_KEY = "fq:name:";
    /**
     * 验证码缓存前缀
     */
    public static final String CAPTCHA_PREFIX = "sys:captcha:";



    /**
     * 会话
     */
    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER = "Bearer ";
    public static final int BEARER_SPLIT = 7;

    /**
     * 验证码过期时间
     */
    public static final Long CAPTCHA_EXPIRE = 300L;


    /**
     * AI 对话记忆前缀
     */
    public static final String CHAT_MEMORY_PREFIX = "chat:memory:";
    /**
     * AI 对话记忆过期时间 (7天 = 604800秒)
     */
    public static final Long CHAT_MEMORY_EXPIRE = 604800L;
}
