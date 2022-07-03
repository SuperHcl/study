package com.hcl.ssd.handler;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录失败的处理器
 *
 * @author Hu.ChangLiang
 * @date 2022/7/3 08:37
 */
public class MyAuthErrorHandler implements AuthenticationFailureHandler {

    String url;

    public MyAuthErrorHandler(String url) {
        this.url = url;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        // request.setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, exception);
        //		request.getRequestDispatcher(forwardUrl).forward(request, response);
        response.sendRedirect(url);
    }
}
