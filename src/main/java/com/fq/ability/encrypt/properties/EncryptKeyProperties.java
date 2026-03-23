package com.fq.ability.encrypt.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author 超chao
 * @Description 加密解密key配置
 * @Date 2024/12/19/周四 17:00
 * @Version 1.0
 */
@Data
@Component
@ConfigurationProperties(prefix = "encrypt")
public class EncryptKeyProperties {
    private String publicKey;
    private String privateKey;
}
