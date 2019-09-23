package com.hcl.tomcat.service;

import com.hcl.tomcat.servlet.CustomHttpRequest;
import com.hcl.tomcat.servlet.CustomHttpResponse;
import com.hcl.tomcat.servlet.CustomServlet;

/**
 * @author: Hucl
 * @date: 2019/9/23 16:17
 * @description:
 */
public class TestService extends CustomServlet{
    @Override
    public void doGet(CustomHttpRequest request, CustomHttpResponse response) throws Exception {
        String uri = request.getUri();
        String path = request.getPath();
        String param = request.getParameter("name");
        String method = request.getMethod();
        String content = "method = " + method + "\n" +
                "uri = " + uri + "\n" +
                "path = " + path + "\n" +
                "param = " + param;

        response.write(content);
    }

    @Override
    public void doPost(CustomHttpRequest request, CustomHttpResponse response) throws Exception {
        doGet(request, response);
    }
}
