package com.hcl.springmvc.handlerAdapter.iface;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: Hucl
 * @date: 2019/7/23 11:20
 * @description:
 */
public interface HandlerAdapter {

    /**
     * 该适配器是否和该处理器适配
     * @param handler 适配器
     * @return true or false
     */
    boolean supports(Object handler);


    /**
     * 使用适配器去执行处理器（一个电脑只能找到指定的电源适配器才能充电）
     * @param request req
     * @param response resp
     * @throws Exception
     */
    void handleRequest(Object handler, HttpServletRequest request, HttpServletResponse response) throws Exception;
}
