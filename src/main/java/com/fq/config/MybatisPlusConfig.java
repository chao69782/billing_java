package com.fq.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.fq.ability.mybatis.AutoFillInterceptor;
import com.fq.ability.mybatis.DataPermissionInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author 超chao
 * @Description MyBatisPlus配置
 * @Date 2024/10/29/周二 16:50
 * @Version 1.0
 */
@Configuration
public class MybatisPlusConfig {
    /**
     * MyBatisPlus拦截器（用于分页）
     */
    @Bean
    public MybatisPlusInterceptor paginationInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        //添加MySQL的分页拦截器
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        // 添加数据权限拦截器
        interceptor.addInnerInterceptor(new DataPermissionInterceptor());
        return interceptor;
    }
    /**
     * 插入数据器
     */
    @Bean
    public AutoFillInterceptor autoFillInterceptor() {
        return new AutoFillInterceptor();
    }
}
