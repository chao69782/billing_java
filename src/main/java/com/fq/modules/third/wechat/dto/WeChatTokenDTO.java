package com.fq.modules.third.wechat.dto;

import lombok.Data;

/**
 * @Author 超chao
 * @Description 微信token数据传输类
 * @Date 2026/1/8 周四 13:08
 * @Version 1.0
 */
@Data
public class WeChatTokenDTO{
    private String session_key;
    private String openid;
}
