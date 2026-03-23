package com.fq.modules.sys.user.controller;

import com.fq.api.api.ApiRest;
import com.fq.api.api.controller.BaseController;
import com.fq.modules.sys.user.dto.SysUserLoginDTO;
import com.fq.modules.sys.user.dto.SysUserLoginResDTO;
import com.fq.modules.sys.user.enums.AccountType;
import com.fq.modules.sys.user.service.SysUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author 超chao
 * @Description 系统用户
 * @Date 2025/2/5/周三 9:20
 * @Version 1.0
 */
@Tag(name = "系统用户")
@RestController
@RequestMapping("/api/admin/sys/user")
public class SysUserController extends BaseController {
    @Autowired
    private SysUserService baseService;
    /**
     * 登录
     */
    @PostMapping(value = "/encryptLogin")
    @Operation(summary = "操作--账号（手机号）密码加密登录",description = """
            加密格式：{
              "password": "123456",
              "username": "123456",
              "validTime": "1734601025459",
              "captchaKey": "uuid",
              "captchaValue": "adtg"
            }""")
    public ApiRest<SysUserLoginResDTO> encryptLogin(@RequestParam(value = "encryptData") String encryptData){
        return super.success(baseService.login(encryptData, AccountType.ADMIN.getType()));
    }

    /**
     * 登录
     */
    @PostMapping(value = "/login")
    @Operation(summary = "操作--账号（手机号）密码登录")
    public ApiRest<SysUserLoginResDTO> login(@Validated @RequestBody SysUserLoginDTO reqDTO){
        return super.success(baseService.login(reqDTO, AccountType.ADMIN.getType()));
    }
}
