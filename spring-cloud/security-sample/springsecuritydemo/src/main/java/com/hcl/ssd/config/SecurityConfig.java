package com.hcl.ssd.config;

import com.hcl.ssd.handler.MyAccessDeniedHandler;
import com.hcl.ssd.handler.MyAuthErrorHandler;
import com.hcl.ssd.handler.MyAuthSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
                .successForwardUrl("/toMain")
                // 自定义登录成功处理器
//                .successHandler(new MyAuthSuccessHandler("http://www.baidu.com"))
                // 登录失败后跳转的页面
                .failureForwardUrl("/toError")
                // 自定义登录失败处理器
//                .failureHandler(new MyAuthErrorHandler("/error.html"))
        ;

        // 授权认证
        http.authorizeRequests()
                // login.html放行
                .antMatchers("/login.html", "/error.html").permitAll()
                // 放行静态资源
                .antMatchers("/**/*.jpeg", "/**/*.jpg").permitAll()
                // 方法拦截，正则表达式的方式
//                .regexMatchers(HttpMethod.GET, "/demo").permitAll()
                // .hasAuthority() 拥有某种权限才能访问资源
//                .antMatchers("/image.html").hasAuthority("admin")
                // hasAnyAuthority("xx","@@") 多权限判断，拥有任何一个都行
                .antMatchers("/image.html").hasAnyAuthority("Admin", "admin")
                // 角色判断，ROLE_ 是在定义用户UserDetailsServiceImpl的时候开始的，abc是真正的权限，
                // hasRole()不能带ROLE_, 启动会报错
//                .antMatchers("/image.html").hasRole("abcd")
//                .antMatchers("/image.html").hasAnyRole("abc", "abcd")
                // .access()可以实现权限判断，要是看源码，hasAnyRole底层调用的也是access方法
//                .antMatchers("/image.html").access("hasRole('abc')")
                // 所有请求都需要被认证。必须登录之后才能够访问
                .anyRequest().authenticated()
//                .anyRequest().access("@myAuthServiceImpl.hasPermission(request, authentication)")
        ;

        // 关闭csrf防护
        http.csrf().disable();

        // 异常处理
        http.exceptionHandling()
                .accessDeniedHandler(new MyAccessDeniedHandler());
    }

    // 密码加密器
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
