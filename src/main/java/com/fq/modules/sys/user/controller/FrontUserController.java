package com.fq.modules.sys.user.controller;

import com.fq.api.api.ApiRest;
import com.fq.api.api.controller.BaseController;
import com.fq.modules.sys.user.dto.SysUserLoginResDTO;
import com.fq.modules.sys.user.enums.LoginType;
import com.fq.modules.sys.user.service.SysUserService;
import com.fq.modules.third.wechat.dto.WeChatTokenDTO;
import com.fq.modules.third.wechat.service.WechatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author 超chao
 * @Description
 * @Date 2026/1/8 周四 13:02
 * @Version 1.0
 */
@Tag(name = "用户授权登录、信息")
@RestController
@RequestMapping("/api/front/user")
public class FrontUserController extends BaseController {
    @Autowired
    private SysUserService baseService;
    @Autowired
    private WechatService wechatService;
    @PostMapping("/login")
    @Operation(summary = "操作--微信小程序code登录")
    public ApiRest<SysUserLoginResDTO> login(@RequestParam(value = "code") String code) {
        WeChatTokenDTO weChatTokenDTO = wechatService.requestLogin(code);
        SysUserLoginResDTO respDTO = baseService.loginByThird(LoginType.WECHAT.getType(), weChatTokenDTO.getOpenid(), null, null);
        return success(respDTO);
    }
}
