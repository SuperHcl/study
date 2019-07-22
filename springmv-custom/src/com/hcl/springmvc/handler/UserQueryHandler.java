package com.hcl.springmvc.handler;

import com.hcl.springmvc.handler.iface.HttpRequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: Hucl
 * @date: 2019/7/22 16:32
 * @description:
 */
public class UserQueryHandler implements HttpRequestHandler {
    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain;charset=UTF8");
        response.getWriter().write("使用handler删除成功！");
    }
}
