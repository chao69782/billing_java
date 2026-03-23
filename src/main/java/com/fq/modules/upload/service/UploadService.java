package com.fq.modules.upload.service;

import com.fq.api.api.ApiError;
import com.fq.api.api.enums.Digit;
import com.fq.api.exception.ServiceException;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @Author 超chao
 * @Description 文件上传
 * @Date 2025/10/28/周二 14:13
 * @Version 1.0
 */
@Log4j2
@Service
public class UploadService {

    @Value("${file.upload.path}")
    private String uploadPath;

    public String upload(MultipartFile file){

        // 生成新文件名
        String fileName = file.getOriginalFilename();

        String newFileName = RandomStringUtils.randomAlphanumeric(Digit.FOUR.getDigit()) + "-" + fileName;
        // 文件保存地址
        String objectName = uploadPath + newFileName;
        try {
            file.transferTo(new File(objectName));
        } catch (IOException e) {
            log.error("文件上传失败", e);
            throw new ServiceException(ApiError.ERROR_10010013);
        }
        return newFileName;
    }
}
