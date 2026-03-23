package com.fq.utils;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

/**
 * @Author 超chao
 * @Description
 * @Date 2026/1/6 周二 13:04
 * @Version 1.0
 */
public class GeneratorUtil {
    public static void main(String[] args) {
        generate();
    }

    private static void generate() {
        String tableName = "category_user";
        // 自动获取项目根路径
        String projectPath = System.getProperty("user.dir");
        String outputDir = projectPath + "/src/main/java";

        // 修复：在 JDBC URL 中添加时区配置和字符编码
        String jdbcUrl = "jdbc:mysql://localhost:3306/billing?serverTimezone=Asia/Shanghai&characterEncoding=utf8&useUnicode=true&useSSL=false";

        FastAutoGenerator.create(jdbcUrl, "root", "123456")
                .globalConfig(builder -> {
                    builder.author("超chao")
                            .outputDir(outputDir);
                })
                .packageConfig(builder -> {
                    builder.parent("com.fq.modules")
                            .moduleName(tableName)
                            .pathInfo(Collections.singletonMap(OutputFile.xml,
                                    projectPath + "/src/main/resources/mapper/" + tableName));
                })
                .strategyConfig(builder -> {
                    builder.addInclude(tableName)
                            .entityBuilder()
                            .idType(IdType.ASSIGN_ID)
                            .superClass(com.baomidou.mybatisplus.extension.activerecord.Model.class)
                            .enableLombok()
                            .enableTableFieldAnnotation()
                            .controllerBuilder()
                            .superClass("com.fq.api.api.controller.BaseController")
                            .enableRestStyle()
                            .enableHyphenStyle();
                })
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }
}
