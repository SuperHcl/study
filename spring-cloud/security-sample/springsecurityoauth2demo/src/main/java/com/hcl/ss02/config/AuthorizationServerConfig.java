package com.hcl.ss02.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

import javax.annotation.Resource;

/**
 * 授权服务器配置
 *
 * @author Hu.ChangLiang
 * @date 2022/7/8 16:44
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                // 配置客户端id
                .withClient("admin")
                // 配置client-secret
                .secret(passwordEncoder.encode("112233"))
                // 配置访问token的有限期3600s
                .accessTokenValiditySeconds(3600)
                // 标识授权成功后跳转的地址
                .redirectUris("www.baidu.com")
                // 配置申请的权限范围
                .scopes("all")
                // 配置授权模式 授权码模式
                .authorizedGrantTypes("authorization_code")

        ;
    }
}
