package com.example.springbootmybatis.config;

import com.example.springbootmybatis.interceptor.DecryptionInterceptor;
import com.example.springbootmybatis.interceptor.MybatisInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * mybatis配置
 *
 * @author Hu.ChangLiang
 * @date 2023/5/30 12:11
 */
@Configuration
public class MybatisConfiguration {

//    @Bean
    public MybatisInterceptor mybatisInterceptor()  {
        return new MybatisInterceptor();
    }

    @Bean
    public DecryptionInterceptor decryptInterceptor () {
        return new DecryptionInterceptor();
    }


}
