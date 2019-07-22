package com.hcl.springmvc.handler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: Hucl
 * @date: 2019/7/22 16:35
 * @description:
 */
public class UserUpdateHandler {

    public void handleRequest2(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/plain;charset=UTF8");
        response.getWriter().write("更新成功！");
    }
}
