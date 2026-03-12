package com.fq.ability.jwt.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author 超chao
 * @Description jwt配置文件
 * @Date 2024/10/12/周六 14:53
 * @Version 1.0
 */
@Data
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtTokenProperties {

    /**
     * 过期时间
     */
    private Integer expireTime;

    /**
     * 密钥
     */
    private String secret;

    /**
     * 重置token比率
     */
    private Float renewPercent;

    /**
     * 是否T下线
     */
    private Boolean kickOut;
}
