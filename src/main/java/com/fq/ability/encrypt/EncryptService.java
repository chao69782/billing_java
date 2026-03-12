package com.fq.ability.encrypt;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.fq.ability.encrypt.properties.EncryptKeyProperties;
import com.fq.api.api.ApiError;
import com.fq.api.exception.ServiceException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * @Author 超chao
 * @Description 加密服务
 * @Date 2025/10/23/周四 11:30
 * @Version 1.0
 */
@Service
@Log4j2
public class EncryptService {
    @Autowired
    private EncryptKeyProperties encryptKeyProperties;

    private static final String RSA_ALGORITHM = "RSA";
    private static final String TRANSFORMATION = "RSA/ECB/PKCS1Padding";

    private static final int KEY_PAIR_SIZE = 2048;


    /**
     * 生成RSA密钥对
     */
    public static KeyPair generateKeyPair() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(RSA_ALGORITHM);
        keyPairGenerator.initialize(KEY_PAIR_SIZE); // 密钥长度
        return keyPairGenerator.generateKeyPair();
    }

    /**
     * 获取Base64编码的公钥
     */
    public static String getPublicKey(KeyPair keyPair) {
        return Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());
    }

    /**
     * 获取Base64编码的私钥
     */
    public static String getPrivateKey(KeyPair keyPair) {
        return Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded());
    }

    /**
     * 从Base64字符串还原公钥
     */
    public static PublicKey restorePublicKey(String publicKeyStr) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(publicKeyStr);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        return keyFactory.generatePublic(keySpec);
    }

    /**
     * 从Base64字符串还原私钥
     */
    public static PrivateKey restorePrivateKey(String privateKeyStr) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(privateKeyStr);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        return keyFactory.generatePrivate(keySpec);
    }

    /**
     * 使用公钥加密数据
     */
    public String encrypt(String data){
        try {
            PublicKey publicKey = restorePublicKey(encryptKeyProperties.getPublicKey());
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] encryptedData = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encryptedData);
        } catch (Exception e) {
            log.error("RSA加密数据失败！", e);
            throw new ServiceException(ApiError.ERROR_10010012);
        }
    }

    /**
     * 使用私钥解密数据
     */
    public String decrypt(String encryptedData){
        try {
            PrivateKey privateKey = restorePrivateKey(encryptKeyProperties.getPrivateKey());
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.error("RSA解密数据失败！", e);
            throw new ServiceException(ApiError.ERROR_10010010);
        }
    }

    public JSONObject decryptToObject(String encryptedData) {
        try {
            String decrypt = decrypt(encryptedData);
            JSONObject jsonObject = JSON.parseObject(decrypt);
            Long validTime = jsonObject.getLong("validTime");
            if (validTime != null){
                if (System.currentTimeMillis() > validTime){
                    throw new ServiceException(ApiError.ERROR_10010011);
                }
            } else {
                throw new ServiceException(ApiError.ERROR_10010011);
            }
            return jsonObject;
        } catch (ServiceException e){
            throw e;
        } catch (Exception e){
            log.error("解密数据失败！",e);
            throw new ServiceException(ApiError.ERROR_10010010);
        }
    }

}
