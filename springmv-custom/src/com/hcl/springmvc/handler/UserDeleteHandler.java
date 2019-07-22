package com.hcl.springmvc.handler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: Hucl
 * @date: 2019/7/22 16:31
 * @description:
 */
public class UserDeleteHandler {

    public void handleRequest1(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/plain;charset=UTF8");
        response.getWriter().write("删除成功！");
    }
}
