package com.hcl.ss02.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * jwt token配置类
 * 就不需要自己定义token了，需要把{@link TokenConfig}注释掉
 *
 * @author Hu.ChangLiang
 * @date 2022/7/20 14:14
 */
@Configuration
public class JwtTokenStoreConfig {

    @Bean
    public TokenStore jwtTokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        // 配置JWT使用的秘钥
        converter.setSigningKey("test_key");
        return converter;
    }
}
