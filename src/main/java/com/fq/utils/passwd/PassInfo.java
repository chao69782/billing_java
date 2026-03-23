package com.fq.utils.passwd;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author 超chao
 * @Description 密码信息
 * @Date 2024/10/12/周六 14:29
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PassInfo {

    //密码随机串码
    private String salt;

    //MD5后的密码
    private String password;

}