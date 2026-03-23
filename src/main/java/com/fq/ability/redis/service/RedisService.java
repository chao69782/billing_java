package com.fq.ability.redis.service;

import com.alibaba.fastjson2.JSONObject;

import java.util.List;

/**
 * @Author 超chao
 * @Description Redis公共服务
 * @Date 2024/10/12/周六 15:31
 * @Version 1.0
 */
public interface RedisService {

    /**
     * 获得一个锁
     *
     * @param key
     * @param ms  锁入毫秒数
     * @return
     */
    boolean tryLock(String key, Long ms);

    /**
     * 删除缓存key
     *
     * @param keys
     */
    void del(String... keys);

    /**
     * 获取缓存内容
     *
     * @param key
     * @return
     */
    JSONObject getJson(String key);

    /**
     * 获取缓存内容
     *
     * @param key
     * @return
     */
    String getString(String key);


    /**
     * 设置缓存值
     *
     * @param key
     * @param data
     * @return
     */
    boolean set(String key, String data);

    /**
     * 设置缓存
     *
     * @param key
     * @param value
     * @param time
     * @return
     */
    boolean set(String key, String value, Long time);

    /**
     * 加入列表
     *
     * @param key
     * @param value
     * @return
     */
    boolean putList(String key, String value);

    /**
     * 移除列表
     *
     * @param key
     * @param value
     * @return
     */
    boolean removeList(String key, String value);

    /**
     * 查找列表
     *
     * @param key
     * @return
     */
    List<String> findList(String key);

    /**
     * 判断缓存中是否有对应的 value
     * @param key
     * @return
     */
    boolean exists(final String key);


    /**
     * 获取剩余时间
     * @param key
     * @return
     */
    Long getExpire(String key);

    /**
     * 设置一个当天凌晨0点过期的key
     * @param key
     * @param value
     */
    void setKeyWithMidnightExpiration(String key,String value);

}