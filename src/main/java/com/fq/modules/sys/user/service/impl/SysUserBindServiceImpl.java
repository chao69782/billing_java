package com.fq.modules.sys.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fq.modules.sys.user.entity.SysUserBind;
import com.fq.modules.sys.user.mapper.SysUserBindMapper;
import com.fq.modules.sys.user.service.SysUserBindService;
import org.springframework.stereotype.Service;

/**
 * @Author 超chao
 * @Description
 * @Date 2026/1/8 周四 11:33
 * @Version 1.0
 */
@Service
public class SysUserBindServiceImpl extends ServiceImpl<SysUserBindMapper, SysUserBind> implements SysUserBindService {
    @Override
    public Long findBind(String loginType, String openId) {
        //分页查询并转换
        QueryWrapper<SysUserBind> wrapper = new QueryWrapper<>();
        wrapper.lambda()
                .eq(SysUserBind::getLoginType, loginType)
                .eq(SysUserBind::getOpenId, openId);

        SysUserBind bind = this.getOne(wrapper, false);

        if(bind==null){
            return null;
        }
        return bind.getUserId();
    }
}
