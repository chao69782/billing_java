package com.fq.ability.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.fq.ability.jwt.properties.JwtTokenProperties;
import com.fq.utils.MD5Util;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Author 超chao
 * @Description jwt工具类
 * @Date 2024/10/12/周六 14:52
 * @Version 1.0
 */
@Component
public class JwtUtils {

    /**
     * 有效期 (秒)
     */
    public static Integer EXPIRE_TIME;

    /**
     * 使用固定的解密秘钥
     */
    private static String SECRET;

    /**
     * 是否T下线
     */
    public static Boolean KICK_OUT;

    /**
     * 从配置文件注入过期时间
     * @param properties
     */
    @Autowired
    public void setExpireTime(JwtTokenProperties properties) {
        JwtUtils.EXPIRE_TIME = properties.getExpireTime();
    }

    /**
     * 从配置文件注入token秘钥
     * @param properties
     */
    @Autowired
    public void setSecretKey(JwtTokenProperties properties) {
        JwtUtils.SECRET = properties.getSecret();
    }


    @Autowired
    public void setKickOut(JwtTokenProperties properties) {
        JwtUtils.KICK_OUT = properties.getKickOut();
    }

    /**
     * 校验是否正确
     *
     * @param token
     * @param userId
     * @return
     */
    public static boolean verify(String token, Long userId) {
        try {
            // 根据密码生成JWT效验器
            Algorithm algorithm = Algorithm.HMAC256(encryptSecret(userId));
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim("username", userId)
                    .build();
            // 效验TOKEN
            verifier.verify(token);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    /**
     * 生成 JWT Token 字符串
     *
     * @param userId
     * @return
     */
    public static String sign(Long userId) {
        Algorithm algorithm = Algorithm.HMAC256(encryptSecret(userId));
        JWTCreator.Builder jwtBuilder = JWT.create().withClaim("username", userId);
            
        // 如果过期时间为 -1，表示永不过期，设置为一个很远的未来时间（例如 100 年后）
        if (EXPIRE_TIME == -1) {
            // 设置为 100 年后的时间，近似于永不过期
            jwtBuilder.withExpiresAt(DateUtils.addYears(new Date(), 100));
        } else {
            jwtBuilder.withExpiresAt(DateUtils.addSeconds(new Date(), EXPIRE_TIME));
        }
            
        return jwtBuilder.sign(algorithm);
    }

    /**
     * 根据用户名和秘钥，生成一个新的秘钥，用于JWT加强一些安全性
     *
     * @param userId
     * @return
     */
    private static String encryptSecret(Long userId) {
        return MD5Util.md5(userId + "&" + SECRET);
    }

}
