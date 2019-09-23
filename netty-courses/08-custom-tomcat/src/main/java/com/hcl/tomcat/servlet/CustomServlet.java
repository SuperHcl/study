package com.hcl.tomcat.servlet;

/**
 * @author: Hucl
 * @date: 2019/9/23 15:54
 * @description:
 */
public abstract class CustomServlet {

    public abstract void doGet(CustomHttpRequest request, CustomHttpResponse response) throws Exception;

    public abstract void doPost(CustomHttpRequest request, CustomHttpResponse response) throws Exception;
}
