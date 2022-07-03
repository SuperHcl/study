package com.hcl.ssd.config;

import com.hcl.ssd.handler.MyAuthErrorHandler;
import com.hcl.ssd.handler.MyAuthSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * security配置
 *
 * @author Hu.ChangLiang
 * @date 2022/7/1 16:18
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 表单提交
        http.formLogin()
                // 当发现是login的时认为是登录，必须和表单提交的地址一样，去执行UserDetailsService
                .loginProcessingUrl("/login")
                // 设置登录页
                .loginPage("/login.html")
                // 登录成功后跳转页面，POST请求
//                .successForwardUrl("/toMain")
                .successHandler(new MyAuthSuccessHandler("http://www.baidu.com"))
                // 登录失败后跳转的页面
//                .failureForwardUrl("/toError")
                .failureHandler(new MyAuthErrorHandler("/error.html"))
        ;

        // 授权认证
        http.authorizeRequests()
                // login.html放行
                .antMatchers("/login.html", "/error.html").permitAll()
                // 所有请求都需要被认证。必须登录之后才能够访问
                .anyRequest().authenticated();

        // 关闭csrf防护
        http.csrf().disable();
    }

    // 密码加密器
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
