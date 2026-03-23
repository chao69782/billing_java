package com.fq.utils.passwd;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.BCrypt;
import org.apache.commons.lang3.RandomStringUtils;

/**
 * @Author 超chao
 * @Description 密码处理工具类
 * @Date 2024/10/12/周六 14:29
 * @Version 1.0
 */
public class PassHandler {

    /**
     * checkPass:校验密码是否一致
     *
     * @param inputPass 用户传入的密码
     * @param hashPass      数据库保存的哈希密码
     * @return boolean
     */
    public static boolean checkPass(String inputPass,String hashPass) {
        if (StrUtil.isBlank(inputPass) || StrUtil.isBlank(hashPass)){
            return false;
        }
        return BCrypt.checkpw(inputPass, hashPass);
    }


    /**
     * buildPassword:用于用户注册时产生一个密码
     *
     * @param inputPass 输入的密码
     * @return PassInfo 返回一个密码对象，记得保存
     */
    public static PassInfo buildPassword(String inputPass) {
        // 产生一个16位的随机盐值
        String salt = BCrypt.gensalt();
        // 使用 bcrypt 加密密码
        String encryptPassword = BCrypt.hashpw(inputPass, salt);
        // 返回对象
        return new PassInfo(salt, encryptPassword);
    }
    /**
     * 生成随机密码
     */
    // 随机数字 0-9 范围内
    private static final int NUMBERS = 10;
    public static String generateRandomPassword(int length) {
        // 生成一个长度为length-1的随机字符串，包含字母和数字
        String randomString = RandomStringUtils.randomAlphanumeric(length - 1);

        // 确保至少有一个数字
        boolean hasDigit = false;
        for (char c : randomString.toCharArray()) {
            if (Character.isDigit(c)) {
                hasDigit = true;
                break;
            }
        }

        // 如果没有数字，则替换一个随机位置的字符为数字(0-9)
        if (!hasDigit && !randomString.isEmpty()) {
            int index = new java.util.Random().nextInt(randomString.length());
            char digit = (char) ('0' + new java.util.Random().nextInt(NUMBERS)); // 限制在0-9范围内
            randomString = randomString.substring(0, index) + digit + randomString.substring(index + 1);
        }

        // 生成一个大写字母作为第一个字符
        String firstChar = RandomStringUtils.random(1, 'A', (char) ('Z' + 1));

        // 拼接成最终的密码
        return firstChar + randomString;
    }

}
