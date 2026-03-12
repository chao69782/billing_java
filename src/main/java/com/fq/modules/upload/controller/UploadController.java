package com.fq.modules.upload.controller;

import com.fq.api.api.ApiRest;
import com.fq.api.api.controller.BaseController;
import com.fq.modules.upload.service.UploadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author 超chao
 * @Description 文件上传
 * @Date 2025/10/28/周二 14:21
 * @Version 1.0
 */
@Tag(name="文件上传")
@RestController
@RequestMapping("/api/common/file")
public class UploadController extends BaseController {
    @Autowired
    private UploadService baseService;

    @PostMapping("/upload")
    @Operation(summary = "操作--文件上传")
    public ApiRest<String> upload(@RequestParam(value = "file") MultipartFile file) {
        return super.success(baseService.upload( file));
    }
}
