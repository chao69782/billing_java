package com.fq.modules.third.wechat.service;

import com.alibaba.fastjson2.JSON;
import com.fq.api.api.ApiError;
import com.fq.api.exception.ServiceException;
import com.fq.modules.third.wechat.dto.WeChatTokenDTO;
import com.fq.utils.http.HttpClientUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Author 超chao
 * @Description
 * @Date 2026/1/8 周四 13:05
 * @Version 1.0
 */
@Log4j2
@Component
public class WechatService {
    @Value("${wechat.wmp.appId}")
    private String appId;
    @Value("${wechat.wmp.appSecret}")
    private String appSecret;
    /**
     * 获取微信AccessToken
     */
    private static final String SESSION_URL = "https://api.weixin.qq.com/sns/jscode2session?appid={appId}&secret={appSecret}&js_code={code}&grant_type=authorization_code";

    /**
     * 通过微信code获得用户信息
     *
     * @param code 微信code
     * @return 微信用户信息
     */
    public WeChatTokenDTO requestLogin(String code) {

        try {
            // 构建完整请求URL
            String requestUrl = SESSION_URL.replace("{appId}", appId)
                    .replace("{appSecret}", appSecret)
                    .replace("{code}", code);

            // 获得返回JSON
            String json = HttpClientUtil.getJson(requestUrl, null, null);

            // 转换为登录结果
            WeChatTokenDTO rest = JSON.parseObject(json, WeChatTokenDTO.class);

            return rest;
        } catch (Exception e) {
            log.error("获取微信sessionKey失败，code {}", code);
            throw new ServiceException(ApiError.ERROR_10020027);
        }
    }
}
