package com.fq.modules.sys.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fq.modules.sys.user.entity.SysUserBind;

/**
 * @Author 超chao
 * @Description 登录绑定业务接口类
 * @Date 2026/1/8 周四 11:33
 * @Version 1.0
 */
public interface SysUserBindService extends IService<SysUserBind> {

    /**
     * 根据绑定信息查找用户ID
     * @param loginType 登录类型
     * @param openId openid
     * @return 用户ID
     */
    Long findBind(String loginType, String openId);
}
