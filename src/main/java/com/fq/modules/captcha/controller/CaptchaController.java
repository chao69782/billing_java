package com.fq.modules.captcha.controller;

import com.fq.api.api.controller.BaseController;
import com.fq.modules.captcha.service.CaptchaService;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author 超chao
 * @Description 图形验证码
 * @Date 2025/11/11 周二 11:20
 * @Version 1.0
 */
@Tag(name = "图形验证码")
@RestController
@RequestMapping("/api/common/captcha")
public class CaptchaController extends BaseController {
    @Autowired
    private CaptchaService captchaService;

    /**
     * 验证码尺寸
     */
    private static final Integer CAPTCHA_WIDTH = 130;
    private static final Integer CAPTCHA_HEIGHT = 48;
    private static final Integer CAPTCHA_LENGTH = 4;

    /**
     * 操作--生成图形验证码
     */
    @RequestMapping(value = "/gen", method = RequestMethod.GET)
    @Operation(summary = "操作--生成图形验证码")
    public void captcha(HttpServletResponse response, @RequestParam("key") String key) throws Exception {

        // 设置请求头为输出图片类型
        response.setContentType("image/gif");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        // 算术类型
        SpecCaptcha captcha = new SpecCaptcha(CAPTCHA_WIDTH, CAPTCHA_HEIGHT,CAPTCHA_LENGTH);
        // 设置类型，纯数字、纯字母、字母数字混合
        captcha.setCharType(Captcha.TYPE_DEFAULT);

        // 存入REDIS
        captchaService.saveCaptcha(key, captcha.text().toLowerCase());

        ServletOutputStream os = response.getOutputStream();

        // 输出图片流
        captcha.out(os);
    }
}
