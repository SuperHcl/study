package com.hcl.ssd.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义登录成功后的处理器
 *
 * @author Hu.ChangLiang
 * @date 2022/7/2 16:55
 */
public class MyAuthSuccessHandler implements AuthenticationSuccessHandler {
    String url;

    public MyAuthSuccessHandler(String url) {
        this.url = url;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        User user = (User) authentication.getPrincipal();
        System.out.println(user.toString());
        response.sendRedirect(url);
    }
}
